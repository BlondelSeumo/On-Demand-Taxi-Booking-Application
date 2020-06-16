package in.techware.lataxicustomer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.listeners.AppStatusListener;
import in.techware.lataxicustomer.listeners.PolyPointsListener;
import in.techware.lataxicustomer.listeners.TripCancellationListener;
import in.techware.lataxicustomer.model.DriverBean;
import in.techware.lataxicustomer.model.PolyPointsBean;
import in.techware.lataxicustomer.model.TripCancellationBean;
import in.techware.lataxicustomer.net.DataManager;
import in.techware.lataxicustomer.util.AppConstants;

public class OnTripActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "OnTripA";

    private static final int LOCATION_SOURCE = 0;
    private static final int LOCATION_DESTINATION = 1;

    private DriverBean driverBean;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private LinearLayout llDriverInfo;
    private TextView txtETA;
    private ImageView ivETAMarker;
    private TextView txtDriverName;
    private TextView txtCarNumber;
    private RatingBar ratingDriver;
    private ImageView ivDriverPhoto;
    private ImageView ivCarPhoto;
    private Marker markerCar;
    private String tripID;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private LatLngBounds bounds;
    private boolean isInit = true;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_trip);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        if (getIntent().hasExtra("bean")) {
            driverBean = (DriverBean) getIntent().getSerializableExtra("bean");
        } else {
            Toast.makeText(this, R.string.message_something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }


        initViews();
        initMap();
//        populateDriverDetails();

        setProgressScreenVisibility(true, true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(appStatusTask, 5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(appStatusTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(appStatusTask);
    }

    private void initViews() {

        llDriverInfo = (LinearLayout) findViewById(R.id.ll_on_trip_details);

        txtETA = (TextView) findViewById(R.id.txt_on_trip_estimated_time_of_arrival);
        ivETAMarker = (ImageView) findViewById(R.id.iv_on_trip_marker);

        txtDriverName = (TextView) findViewById(R.id.txt_on_trip_driver_name);
        txtCarNumber = (TextView) findViewById(R.id.txt_on_trip_car_number);

        ratingDriver = (RatingBar) findViewById(R.id.rating_on_trip_driver_rating);

        ivDriverPhoto = (ImageView) findViewById(R.id.iv_on_trip_driver_photo);
        ivCarPhoto = (ImageView) findViewById(R.id.iv_on_trip_car_photo);


    }

    private void initMap() {


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_home_map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0, (int) ((100 * px) + mActionBarHeight + getStatusBarHeight()), 0, (int) (230 * px));

//                populateDriverDetails();
//                mHandler.postDelayed(appStatusTask, 5000);

            }
        });

        /*try {
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            params.height = (int) (height - (230 * px) *//*- getNavBarHeight()*//* - mActionBarHeight - getStatusBarHeight()*//*- (230 * px)*//*);

            Log.i(TAG, "onActivityResult: MapHeight " + params.height);
            mapFragment.getView().setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    public void onCancelButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (App.isNetworkAvailable()) {
            performTripCancellation();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    public void onContactButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (checkForCallPermissions()) {
            performCall(driverBean.getDriverNumber());
        } else {
            getCallPermissions();
        }
    }

    public void populateDriverDetails() {

        if (mMap != null) {
            onPlotLocation(true, LOCATION_SOURCE,
                    driverBean.getDSourceLatitude(), driverBean.getDSourceLongitude());
            onPlotLocation(true, LOCATION_DESTINATION,
                    driverBean.getDDestinationLatitude(), driverBean.getDDestinationLongitude());
            onPlotDriverCar();
        }

        txtETA.setText(driverBean.getTime() != null && !driverBean.getTime().equalsIgnoreCase("null")
                ? driverBean.getTime() : getString(R.string.label_not_available));
        txtDriverName.setText(driverBean.getDriverName());
        txtCarNumber.setText(driverBean.getCarNumber());

        ratingDriver.setRating(driverBean.getRating() == -1f ? 0 : driverBean.getRating());

        Glide.with(getApplicationContext())
                .load(driverBean.getCarPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_car_la_landing_page)
                        .fallback(R.drawable.ic_car_la_landing_page))
                .into(ivCarPhoto);

        Log.i(TAG, "populateDriverDetails: DriverPhoto " + driverBean.getDriverPhoto());

        Glide.with(getApplicationContext())
                .load(driverBean.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_dummy_photo)
                        .fallback(R.drawable.ic_dummy_photo)
                        .centerCrop()
                        .circleCrop())
                .into(ivDriverPhoto);

        Log.i(TAG, "populateDriverDetails: CarPhoto " + driverBean.getCarPhoto());

        setProgressScreenVisibility(false, false);
//        fetchAppStatus();
    }

    private void onPlotDriverCar() {

        try {
//           mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_car_landing_page)));

            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_driver_details_car);
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);

            if (mMap != null) {
                if (markerCar == null) {
                    markerCar = mMap.addMarker(new MarkerOptions()
                            .position(driverBean.getCarLatLng())
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .flat(true));
                } else {
                    markerCar.setPosition(driverBean.getCarLatLng());
                }
                mapAutoZoom();
                if (isInit)
                    fetchPolyPoints(true);
            }


        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    Runnable appStatusTask = new Runnable() {
        @Override
        public void run() {
            if (App.isNetworkAvailable()) {
                fetchAppStatus();
            }
            mHandler.postDelayed(appStatusTask, 5000);
        }
    };

    private void fetchAppStatus() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchAppStatus(urlParams, new AppStatusListener() {
            @Override
            public void onLoadCompleted(DriverBean driverBeanWS) {

                if (!isFinishing()) {
                    if (driverBeanWS.getAppStatus() != 0) {
                        driverBean = driverBeanWS;
                        populateDriverDetails();
                    } else {
                        Toast.makeText(OnTripActivity.this, R.string.message_trip_ended, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(OnTripActivity.this, SplashActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                }
            }

            @Override
            public void onLoadFailed(String error) {
                Log.i(TAG, "onLoadFailed: Error " + error);
            }
        });

    }

    public void performTripCancellation() {

        swipeView.setRefreshing(true);
        JSONObject postData = geTripCancellationJSObj();

        DataManager.performTripCancellation(postData, new TripCancellationListener() {

            @Override
            public void onLoadCompleted(TripCancellationBean tripCancellationBean) {
                swipeView.setRefreshing(false);
                Toast.makeText(OnTripActivity.this, R.string.message_trip_cancelled, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(OnTripActivity.this, SplashActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }

            @Override
            public void onLoadFailed(String error) {

                swipeView.setRefreshing(false);

//                finish();
            }
        });
    }

    private JSONObject geTripCancellationJSObj() {

        tripID = driverBean.getTripID();

        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_id", tripID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


    public void onPlotLocation(boolean isMarkerNeeded, int type, double latitude, double longitude) {

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(latitude, longitude);
            if (isMarkerNeeded) {
                switch (type) {
                    case LOCATION_SOURCE:
                        mMap.addMarker(new MarkerOptions()
                                .position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_marker)));
                        break;
                    case LOCATION_DESTINATION:
                        mMap.addMarker(new MarkerOptions()
                                .position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker)));
                        break;
                    default:
                        mMap.addMarker(new MarkerOptions()
                                .position(newLatLng).icon(BitmapDescriptorFactory.defaultMarker()));
                        break;
                }
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));
            Log.i(TAG, "onPlotLocation: Position" + newLatLng);

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    public void fetchPolyPoints(final boolean isPolyLineNeeded) {

        HashMap<String, String> urlParams = new HashMap<>();

//        if (sourceBean != null && destinationBean != null) {
        urlParams.put("origin", driverBean.getSourceLatitude() + "," + driverBean.getSourceLongitude());
        urlParams.put("destination", driverBean.getDestinationLatitude() + "," + driverBean.getDDestinationLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));
//        }

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;

/*                time = String.valueOf(polyPointsBean.getTime());
                distance = String.valueOf(polyPointsBean.getDistance());*/

                Log.i(TAG, "onLoadCompleted: Time Taken" + polyPointsBean.getTimeText());
                Log.i(TAG, "onLoadCompleted: Distance" + polyPointsBean.getDistanceText());

                if (isPolyLineNeeded) {
                    populatePath();
                }
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

        List<List<HashMap<String, String>>> routes = polyPointsBean.getRoutes();

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
        isInit = false;
    }

    public void mapAutoZoom() {

       /* if (sourceBean != null && destinationBean != null) {
            newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
            newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
        }*/

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(driverBean.getSourceLatLng());
        builder.include(driverBean.getDestinationLatLng());
        if (driverBean != null) {
            builder.include(driverBean.getCarLatLng());
        }

        Log.i(TAG, "mapAutoZoom: SOURCE : " + driverBean.getSourceLatLng());
        Log.i(TAG, "mapAutoZoom: DESTINATION : " + driverBean.getDestinationLatLng());
        Log.i(TAG, "mapAutoZoom: CAR : " + driverBean.getCarLatLng());
        bounds = builder.build();

//        mMap.setPadding(0, (int) (height - getStatusBarHeight() - mActionBarHeight - (px * 160)), 0, (int) (height - getStatusBarHeight() - mActionBarHeight - (px * 120)));

//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (40 * px)));
        if (mMap != null && mapFragment.getView() != null && mapFragment.getView().getHeight() > 0) {
            if (mapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (40 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }

    }


    public void onOnTripDriverDetailsClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

    }
}
