package in.techware.lataxidriverapp.activity;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.LocationUpdateListener;
import in.techware.lataxidriverapp.listeners.PermissionListener;
import in.techware.lataxidriverapp.listeners.PolyPointListener;
import in.techware.lataxidriverapp.listeners.RequestDetailsListener;
import in.techware.lataxidriverapp.listeners.TripDetailsListener;
import in.techware.lataxidriverapp.model.PolyPointBean;
import in.techware.lataxidriverapp.model.RequestDetailsBean;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class RequestConfirmationActivity extends BaseAppCompatNoDrawerActivity implements
        GoogleMap.OnMyLocationButtonClickListener {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;

    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);

    private static final String TAG = "RequestConfA";
    private String requestID;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private RequestDetailsBean requestDetailsBean;
    private TextView txtETA;
    private TextView txtDistance;
    private TextView txtCarType;
    private Button btnConfirm;
    private ImageView ivCarType;
    private HashMap markerMap;
    private ArrayList<Object> plotList;
    private Bitmap mapPin;
    private FragmentManager myFragmentManager;
    private SupportMapFragment myMapFragment;
    private ViewGroup.LayoutParams param;
    private GoogleMap mMap;
    private LatLng current;
    private LatLng center;
    private Polyline polyLine;
    private PolyPointBean polyPointBean;
    private boolean isInit;
    private AudioManager audioManager;
    private int volume;
    private int streamMaxVolume;
    private Ringtone ringtone;
    private Thread thr;
    private long pattern[] = {0, 2000, 1000};
    private boolean isPlaySound = true;
    private Handler mHandler;
    private boolean isMapInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        getWindow().setType(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().setType(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//        getWindow().addFlags(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//        getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);

        KeyguardManager.KeyguardLock keyLock;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            KeyguardManager keyMgr = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            keyLock = keyMgr.newKeyguardLock("noti");
            keyLock.disableKeyguard();
        }/* else {
            KeyguardManager keyMgr = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            keyLock = keyMgr.newKeyguardLock("noti");
            keyLock.disableKeyguard();
        }*/

        setContentView(R.layout.activity_request_confirmation);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        if (getIntent().hasExtra("request_id")) {
            requestID = getIntent().getStringExtra("request_id");
        }

        initViews();
        initMap();

        initAlarm();

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    /*public void onAttachedToWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopPlay();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (requestDetailsBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }

    private void getData(boolean isSwipeRefreshing) {

//        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchRequestDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };

        mHandler = new Handler();

        txtETA = (TextView) findViewById(R.id.txt_request_confirmation_eta);
        txtDistance = (TextView) findViewById(R.id.txt_request_confirmation_distance);
        txtCarType = (TextView) findViewById(R.id.txt_request_confirmation_car_type);

        ivCarType = (ImageView) findViewById(R.id.iv_request_confirmation_car_type);

        btnConfirm = (Button) findViewById(R.id.btn_request_confirmation_confirm);

        btnConfirm.setTypeface(typeface);


        LocationUpdateListener locationUpdateListener = new LocationUpdateListener() {
            @Override
            public void onLocationUpdated(Location location) {
                Config.getInstance().setCurrentLatitude("" + location.getLatitude());
                Config.getInstance().setCurrentLongitude("" + location.getLongitude());

                Log.i(TAG, "onLocationChanged: LATITUDE : " + location.getLatitude());
                Log.i(TAG, "onLocationChanged: LONGITUDE : " + location.getLongitude());

                if (requestDetailsBean != null
                        && !requestDetailsBean.getCustomerLatitude().equalsIgnoreCase("")
                        && !requestDetailsBean.getCustomerLongitude().equalsIgnoreCase("")
                        && isMapInit) {
                    populateMap();
                }
            }
        };
        addLocationUpdateListener(locationUpdateListener);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionCheckCompleted(int requestCode, boolean isPermissionGranted) {
                if (requestCode == REQUEST_PERMISSIONS_LOCATION) {
                    if (isPermissionGranted) {
                        if (checkLocationSettingsStatus()) {
                            getCurrentLocation();
                        }
                    }
                }
            }
        };
        addPermissionListener(permissionListener);


    }

    private void initMap() {

        plotList = new ArrayList<>();
        markerMap = new HashMap();
/*
        frame = BitmapFactory.decodeResource(getResources(), R.drawable.bg_map_pin);
        mapPin = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hunt_green);
        mapPinRed = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hunt_red);
*/

        mapPin = BitmapFactory.decodeResource(getResources(), R.drawable.circle_app);

        myFragmentManager = getSupportFragmentManager();
        myMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.fragment_map);
        //	mMap = myMapFragment.getMap();

        param = myMapFragment.getView().getLayoutParams();
        param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - (80 * px));
        Log.i(TAG, "onSlide: Param Height : " + param.height);
        myMapFragment.getView().setLayoutParams(param);

        myMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mapTemp) {

                Log.i(TAG, "onMapReady: MAP IS LOADED");

                mMap = mapTemp;
                mMap.setPadding(0, 0/*(int) ((100 * px) + mActionBarHeight + getStatusBarHeight())*/, 0, (int) (60 * px));
                initMapOnLoad();
                if (Config.getInstance().getCurrentLatitude() != null
                        && !Config.getInstance().getCurrentLatitude().equals("")
                        && Config.getInstance().getCurrentLongitude() != null
                        && !Config.getInstance().getCurrentLongitude().equals("")) {
                    //	fetchMap();
                }
            }
        });

    }

    private void initMapOnLoad() {

        current = new LatLng(0.0, 0.0);

        if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
            current = new LatLng(Config.getInstance().getDCurrentLatitude(), Config.getInstance().getDCurrentLongitude());
        }

        //	mMap=mapView.getMap();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions()) {
                getLocationPermissions();
            }
            checkLocationSettingsStatus();
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


/*        if (placesBean != null) {
            dLatitude = Double.parseDouble(placesBean.getLatitude());
            dLongitude = Double.parseDouble(placesBean.getLongitude());

            newLatLng = new LatLng(dLatitude, dLongitude);

            mMap.addMarker(new MarkerOptions()
                    .position(newLatLng)
                    .title(placesBean.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 12));
        }*/

        center = mMap.getCameraPosition().target;

       /* mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                if (llMapDetails.isShown()) {
                    llMapDetails.setVisibility(View.GONE);
                }

            }
        });*/
/*
        try {
            populatePlotList();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    private void initAlarm() {

//        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.push_sound);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, streamMaxVolume,
                AudioManager.FLAG_ALLOW_RINGER_MODES | AudioManager.FLAG_PLAY_SOUND);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), defaultSoundUri);
        mVibrator.vibrate(pattern, 1);

        thr = new Thread(null, mTask, "Ringtone Player");
        thr.start();
    }

    Runnable mTask = new Runnable() {
        public void run() {
            try {
                ringtone.play();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    public void stopPlay() {
        try {
            ringtone.stop();
            mHandler.removeCallbacks(mTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        audioManager.setStreamVolume(AudioManager.STREAM_RING, volume,
                AudioManager.FLAG_ALLOW_RINGER_MODES | AudioManager.FLAG_PLAY_SOUND);
        mVibrator.cancel();
    }

    public void onRequestConfirmationConfirmClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performConfirmTrip(view);
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    public void fetchRequestDetails() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("request_id", requestID);

        DataManager.fetchRequestDetails(urlParams, new RequestDetailsListener() {

            @Override
            public void onLoadCompleted(RequestDetailsBean requestDetailsBeanWS) {

                requestDetailsBean = requestDetailsBeanWS;

                Log.i(TAG, "onLoadCompleted: REQUESTDETAILSBEAN : " + new Gson().toJson(requestDetailsBeanWS));
                populateRequestDetails();
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                setProgressScreenVisibility(true, false);
                swipeView.setRefreshing(false);

            }
        });
    }

    private void populateRequestDetails() {

        txtETA.setText(requestDetailsBean.getEta());
        txtCarType.setText(requestDetailsBean.getCarType());
        txtDistance.setText(requestDetailsBean.getDistance());

        Glide.with(getApplicationContext())
                .load(requestDetailsBean.getCarTypeImage())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_car_la_landing_page)
                        .fallback(R.drawable.ic_car_la_landing_page))
                .into(ivCarType);

        if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")
                && isMapInit) {
            populateMap();
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    private void performConfirmTrip(final View view) {
        swipeView.setRefreshing(true);
        view.setEnabled(false);

        JSONObject postData = getTripAcceptJSObj();

        DataManager.performTripAccept(postData, new TripDetailsListener() {

            @Override
            public void onLoadCompleted(TripBean tripBean) {
                swipeView.setRefreshing(false);
                view.setEnabled(true);
                Toast.makeText(RequestConfirmationActivity.this, R.string.message_trip_confirmed, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(RequestConfirmationActivity.this, OnTripActivity.class)
                        .putExtra("bean", tripBean));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                view.setEnabled(true);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });


    }

    private JSONObject getTripAcceptJSObj() {

        JSONObject postData = new JSONObject();

        try {
            postData.put("request_id", requestID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void populateMap() {
        mMap.clear();
        onPlotLatLng(Double.parseDouble(Config.getInstance().getCurrentLatitude()),
                Double.parseDouble(Config.getInstance().getCurrentLongitude()),
                requestDetailsBean.getDDestinationLatitude(), requestDetailsBean.getDDestinationLongitude());
        mapAutoZoom();
        isMapInit = false;
    }


    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        fetchPolyPoint();

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));

//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_location)));


            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin_customer)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void mapAutoZoom() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(Double.parseDouble(Config.getInstance().getCurrentLatitude()),
                Double.parseDouble(Config.getInstance().getCurrentLongitude())));
        builder.include(requestDetailsBean.getDestinationLatLng());
        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
        if (mMap != null && myMapFragment.getView() != null && myMapFragment.getView().getHeight() > 0) {
            if (myMapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }
    }

    public void fetchPolyPoint() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", Config.getInstance().getCurrentLatitude() + "," + Config.getInstance().getCurrentLongitude());
        urlParams.put("destination", requestDetailsBean.getCustomerLatitude() + "," + requestDetailsBean.getCustomerLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));

        DataManager.fetchPolyPoints(urlParams, new PolyPointListener() {

            @Override
            public void onLoadCompleted(PolyPointBean polyPointBeanWS) {
                swipeView.setRefreshing(false);

                polyPointBean = polyPointBeanWS;
                populatePath();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });
    }

    private void populatePath() {

        txtDistance.setText(polyPointBean.getDistanceText());
        txtETA.setText(polyPointBean.getTimeText());

        ArrayList<List<HashMap<String, String>>> routes = polyPointBean.getRoutes();

        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap point = (HashMap) path.get(j);

                double lat = Double.parseDouble((String) point.get("lat"));
                double lng = Double.parseDouble((String) point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(8);
            polyLineOptions.color(ContextCompat.getColor(getApplicationContext(), R.color.map_path));

        }

        polyLine = mMap.addPolyline(polyLineOptions);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

}
