package in.techware.lataxidriverapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.PolyPointListener;
import in.techware.lataxidriverapp.listeners.TripDetailsListener;
import in.techware.lataxidriverapp.model.MapBean;
import in.techware.lataxidriverapp.model.PolyPointBean;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

//import in.techware.lataxidriver.model.PlaceBean;

public class TripDetailsActivity extends BaseAppCompatNoDrawerActivity {


    private static final String TAG = "HomeFrag";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private GoogleApiClient mGoogleApiClient;
    private static final LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);

    private GoogleMap mMap;
    private static SupportMapFragment myMapFragment = SupportMapFragment.newInstance(options);
    private FragmentManager myFragmentManager;


    private LatLng current;
    private double dLatitude;
    private double dLongitude;
    private LatLng center;
    private LatLng newLatLng;
    private MapBean mapBean;
    private boolean isInit = true;
    private boolean isMarkerClicked;
    private LatLng latLngClickedMarker;
    private int selectedPosition = -1;
    //    private ArrayList<PlaceBean> placesList;
    private ArrayList<String> plotList;
    private HashMap<String, Integer> markerMap;
    private Bitmap mapPin;
    private Bitmap frame;
    private String pathIn;

    private LinearLayout llBottomSheet;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private ImageView ivProfilePhoto;
    private ViewGroup.LayoutParams param;
    private TripBean tripBean;
    private Toolbar toolbarTrips;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtCustomerName;
    private TextView txtPayout;
    private TextView txtDuration;
    private TextView txtDistance;
    private TextView txtFare;
    private TextView txtLaTaxiFee;
    private TextView txtTaxReturn;
    private TextView txtEstimatedPayout;
    private TextView txtSourceLocation;
    private TextView txtDestinationLocation;
    private RatingBar ratingTrip;
    private TextView txtRating;
    private LinearLayout llDetails;
    private PolyPointBean polyPointBean;
    private Polyline polyLine;
    private String tripID;
    private View.OnClickListener snackBarRefreshOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        if (getIntent().hasExtra("bean")) {
            tripBean = (TripBean) getIntent().getSerializableExtra("bean");
            tripID = tripBean.getId();
        } else if (getIntent().hasExtra("trip_id")) {
            tripID = getIntent().getStringExtra("trip_id");
        }

        initViews();
        initMap();

        if (tripBean != null)
            populateTrip();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                toolbar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //                mVibrator.vibrate(25);
                startActivity(new Intent(this, HomeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //            onBackPressed();
            startActivity(new Intent(this, HomeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (tripBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(App.getInstance().getApplicationContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(result)) {
                googleApiAvailability.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchTripDetails();
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


        coordinatorLayout.removeView(toolbar);
        toolbarTrips = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_trip_details, toolbar);
        coordinatorLayout.addView(toolbarTrips, 0);
        setSupportActionBar(toolbarTrips);

        txtDate = (TextView) toolbarTrips.findViewById(R.id.txt_toolbar_trip_details_date);
        txtTime = (TextView) toolbarTrips.findViewById(R.id.txt_toolbar_trip_details_time);

        txtCustomerName = (TextView) findViewById(R.id.txt_trip_details_customer_name);
        txtPayout = (TextView) findViewById(R.id.txt_trip_details_payout);
        txtDuration = (TextView) findViewById(R.id.txt_trip_details_duration);
        txtDistance = (TextView) findViewById(R.id.txt_trip_details_distance);
        txtFare = (TextView) findViewById(R.id.txt_trip_details_fare);
        txtLaTaxiFee = (TextView) findViewById(R.id.txt_trip_details_lataxi_fee);
        txtTaxReturn = (TextView) findViewById(R.id.txt_trip_details_tax_return);
        txtEstimatedPayout = (TextView) findViewById(R.id.txt_trip_details_estimated_payout);
        txtSourceLocation = (TextView) findViewById(R.id.txt_trip_details_source_location);
        txtDestinationLocation = (TextView) findViewById(R.id.txt_trip_details_destination_location);

        txtRating = (TextView) findViewById(R.id.txt_trip_details_rating);

        ratingTrip = (RatingBar) findViewById(R.id.rating_trip_details);

        ivProfilePhoto = (ImageView) findViewById(R.id.iv_trip_details_profile_photo);

        llBottomSheet = (LinearLayout) findViewById(R.id.ll_bottom_sheet_trip_details);
        llDetails = (LinearLayout) findViewById(R.id.ll_trip_details_detailed);

        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                /*if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING
                        || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                }*/
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(TAG, "onSlide: offset : " + slideOffset);
//                mapFragmentView.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
                param = myMapFragment.getView().getLayoutParams();
                param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - (80 * px * (1 - slideOffset)) - bottomSheet.getHeight() * (slideOffset));
                Log.i(TAG, "onSlide: PAram Height : " + param.height);
                myMapFragment.getView().setLayoutParams(param);
            }
        });

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

                if (tripBean != null) {
                    populateMap();
                }
            }
        });

    }

    private void initMapOnLoad() {

        current = new LatLng(0.0, 0.0);

        if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
            dLatitude = Double.parseDouble(Config.getInstance().getCurrentLatitude());
            dLongitude = Double.parseDouble(Config.getInstance().getCurrentLongitude());
            current = new LatLng(dLatitude, dLongitude);
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
        mMap.setMyLocationEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
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

    private void fetchTripDetails() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("trip_id", tripID);
        /*if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }
*/
        DataManager.fetchTripDetails(urlParams, new TripDetailsListener() {
            @Override
            public void onLoadCompleted(TripBean tripBeanWS) {

                tripBean = tripBeanWS;
                populateTrip();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);

            }
        });
    }

    private void populateTrip() {

        if (tripBean != null) {

            if (mMap != null) {
                populateMap();
            }

            if (tripBean.getTripStatus().equalsIgnoreCase("0")) {
                llDetails.setVisibility(View.GONE);
            } else {
                llDetails.setVisibility(View.VISIBLE);
            }

            txtDate.setText(App.getDateFromUnix(App.DATE_FORMAT_1, true, false, tripBean.getStartTime()));
            txtTime.setText("at " + App.getTimeFromUnix(App.TIME_FORMAT_0, true, false, tripBean.getStartTime()));
            txtCustomerName.setText(tripBean.getCustomerName());
            txtPayout.setText(tripBean.getEstimatedPayout());
            txtDistance.setText(tripBean.getDistance());
            txtDuration.setText(tripBean.getDuration());
            txtFare.setText(tripBean.getFare());
            txtLaTaxiFee.setText(tripBean.getFee());
            txtTaxReturn.setText(tripBean.getTax());
            txtEstimatedPayout.setText(tripBean.getEstimatedPayout());
            txtSourceLocation.setText(tripBean.getSourceLocation());
            txtDestinationLocation.setText(tripBean.getDestinationLocation());

            ratingTrip.setRating(tripBean.getRating());

            if (tripBean.getRating() == 0) {
                txtRating.setVisibility(View.VISIBLE);
                ratingTrip.setVisibility(View.GONE);
            } else {
                txtRating.setVisibility(View.GONE);
                ratingTrip.setVisibility(View.VISIBLE);
            }

            Glide.with(getApplicationContext())
                    .load(tripBean.getCustomerPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_profile_photo_default)
                            .fallback(R.drawable.ic_profile_photo_default)
                            .centerCrop()
                            .circleCrop())
                    .into(ivProfilePhoto);

            swipeView.setRefreshing(false);
            setProgressScreenVisibility(false, false);

        } else {
            Toast.makeText(getApplicationContext(), R.string.message_something_went_wrong, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void populateMap() {
        mMap.clear();
        onPlotLatLng(tripBean.getDSourceLatitude(), tripBean.getDSourceLongitude(),
                tripBean.getDDestinationLatitude(), tripBean.getDDestinationLongitude());
        mapAutoZoom();
    }


    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        fetchPolyPoint();

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));

//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_source)));


            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_destination)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void mapAutoZoom() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(tripBean.getSourceLatLng());
        builder.include(tripBean.getDestinationLatLng());
        LatLngBounds bounds = builder.build();
        if (mMap != null && myMapFragment.getView() != null && myMapFragment.getView().getHeight() > 0) {
            if (myMapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }

    }

    public void fetchPolyPoint() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", tripBean.getSourceLatitude() + "," + tripBean.getSourceLongitude());
        urlParams.put("destination", tripBean.getDestinationLatitude() + "," + tripBean.getDestinationLongitude());
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

    private void moveToCurrentLocation() {
        try {
            if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                    && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
                dLatitude = Double.parseDouble(Config.getInstance().getCurrentLatitude());
                dLongitude = Double.parseDouble(Config.getInstance().getCurrentLongitude());
                current = new LatLng(dLatitude, dLongitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 18));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTripDetailsDetailsClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

    }

    public void onTripDetailsHelpClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivity(new Intent(this, TripHelpActivity.class)
                .putExtra("bean", tripBean));

    }
}
