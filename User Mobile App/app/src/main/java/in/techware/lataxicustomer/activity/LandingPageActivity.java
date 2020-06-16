package in.techware.lataxicustomer.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.adapter.CarTypeRecyclerAdapter;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.dialogs.PopupMessage;
import in.techware.lataxicustomer.listeners.BasicListener;
import in.techware.lataxicustomer.listeners.CarInfoListener;
import in.techware.lataxicustomer.listeners.LandingPageListener;
import in.techware.lataxicustomer.listeners.LocationUpdateListener;
import in.techware.lataxicustomer.listeners.PermissionListener;
import in.techware.lataxicustomer.listeners.PolyPointsListener;
import in.techware.lataxicustomer.listeners.TotalFareListener;
import in.techware.lataxicustomer.model.BasicBean;
import in.techware.lataxicustomer.model.CarBean;
import in.techware.lataxicustomer.model.DriverBean;
import in.techware.lataxicustomer.model.FareBean;
import in.techware.lataxicustomer.model.LandingPageBean;
import in.techware.lataxicustomer.model.PlaceBean;
import in.techware.lataxicustomer.model.PolyPointsBean;
import in.techware.lataxicustomer.net.DataManager;
import in.techware.lataxicustomer.net.WSAsyncTasks.FCMRegistrationTask;
import in.techware.lataxicustomer.net.WSAsyncTasks.LocationNameTask;
import in.techware.lataxicustomer.net.WSAsyncTasks.LocationTask;
import in.techware.lataxicustomer.util.AppConstants;


public class LandingPageActivity extends BaseAppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int UPDATE_INTERVAL = 10000;
    private static final int FASTEST_INTERVAL = 5000;
    private static final int DISPLACEMENT = 10;
    private static final String TAG = "LandingPA";

    private static final int REQ_SEARCH_SOURCE_SELECT = 0;
    private static final int REQ_SEARCH_DESTINATION_SELECT = 1;
    private static final int REQ_SEARCH_DESTINATION_ESTIMATE_SELECT = 2;
    private static final int REQ_REQUEST_RIDE = 3;
    private static final int REQ_ESTIMATED_DESTINATION = 4;
    private static final int LOCATION_SOURCE = 0;
    private static final int LOCATION_DESTINATION = 1;

    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);

    //    private GoogleApiClient mGoogleApiClient;
    private Location LastLocation;
    private GoogleMap mMap;
    private Toolbar toolbarHome;
    private TextView txtActionSearch;
    private FrameLayout framePickup;
    private ImageView ivMarker;
    private ImageView ivBottomMarker;
    private LinearLayout llLandingBottomBar;
    private ImageView ivLocationButton;
    private SupportMapFragment mapFragment;
    //    private View lytBottom;
    private TextView txtTime;
    private TextView txtMaxSize;
    private TextView txtFare;
    private String carType = String.valueOf(-1);
    //    private int searchPlaceType = AppConstants.SEARCH_SOURCE;
    private TextView txtSource;
    private LinearLayout llConfirmation;
    private boolean isConfirmationPage = false;
    private boolean isCameraMoved;
    private CardView cvConfirmationPage;
    private TextView txtDestination;
    private TextView txtTotalFare;
    private RelativeLayout rlFare;
    private View viewDottedLine;
    private TextView txtCarOne;
    private TextView txtCarTwo;
    private TextView txtCarThree;
    private TextView carFour;
    private TextView txtFareEstimate;
    private TextView txtTo;
    private LinearLayout llDestinationEstimated;
    private TextView txtEstimatedDestination;
    private Button btnRequest;
    private View.OnClickListener snackBarRefreshOnClickListener;

    private int searchType;
    private FareBean fareBean;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private LatLngBounds bounds;
    private LatLng newLatLng1;
    private LatLng newLatLng2;
    private ImageView carOneImage;
    private ImageView carTwoImage;
    private ImageView carThreeImage;
    private ImageView carFourImage;
    private TextView txtCarAvailability;
    private String time;
    private String distance;
    private boolean isDestinationEstimateSelect = false;
    private LinearLayout llFare;
    private TextView txtCarArrivalEstimatedTime;
    private CarBean carBean;
    private LandingPageBean landingPageBean;
    private PlaceBean destinationBean;
    private PlaceBean sourceBean;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private ViewGroup.LayoutParams param;
    private FrameLayout flLandingPage;
    private ViewGroup.LayoutParams param1;
    private TextView txtEstimatedFare;
    private boolean isMapInit = true;
    private TextView txtFareLabel;
    private LinearLayout llProgressBar;
    private LinearLayout llEstimation;
    private LinearLayout llConfirmationProgress;
    private boolean isInit = true;
    private CarTypeRecyclerAdapter adapterCarTypes;
    private RecyclerView rvCarTypes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing_page);

        isGetLocationEnabled = false;


       /* if (!checkForLocationPermissions()) {
            getLocationPermissions();
        } else {
            checkLocationSettingsStatus();
        }

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        }else{
            isGetLocationEnabled=true;
        }*/

        initViews();
        initMap();

        setProgressScreenVisibility(true, true);
//        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        initFCM();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isConfirmationPage) {
                onBackClick();
            } else {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    onBackPressed();
                }
            }
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConfirmationPage && sourceBean == null) {
            if (checkPlayServices()) {
                getCurrentLocation();
//            buildGoogleApiClient();
//            createLocationRequest();
            }
        }
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(result)) {
                googleApiAvailability.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    private void getData() {
        if (App.isNetworkAvailable()) {
            fetchLandingPageDetails();
        } else {
            setProgressScreenVisibility(true, false);
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    public void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //  mVibrator.vibrate(25);
                getData();
            }
        };

        btnRequest = (Button) findViewById(R.id.btn_request);

        rlFare = (RelativeLayout) findViewById(R.id.rl_fare);

        coordinatorLayout.removeView(toolbar);
//      toolbar.setVisibility(View.GONE);
        toolbarHome = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_landing_page, toolbar);
        coordinatorLayout.addView(toolbarHome, 0);
        setSupportActionBar(toolbarHome);


        rvCarTypes = (RecyclerView) findViewById(R.id.rv_bottom_sheet_landing_car_types);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCarTypes.setLayoutManager(layoutManager);

        ivBottomMarker = (ImageView) findViewById(R.id.iv_bottom_marker);

        llConfirmationProgress = (LinearLayout) findViewById(R.id.ll_confirmation_progress);

        txtFareEstimate = (TextView) findViewById(R.id.txt_fare_estimate);
        txtTo = (TextView) findViewById(R.id.txt_to);
        llDestinationEstimated = (LinearLayout) findViewById(R.id.ll_destination_estimated);

        carOneImage = (ImageView) findViewById(R.id.iv_car_one);
        carTwoImage = (ImageView) findViewById(R.id.iv_car_two);
        carThreeImage = (ImageView) findViewById(R.id.iv_car_three);
        carFourImage = (ImageView) findViewById(R.id.iv_car_four);

        txtCarOne = (TextView) findViewById(R.id.txt_la_go);
        txtCarTwo = (TextView) findViewById(R.id.txt_la_x);
        txtCarThree = (TextView) findViewById(R.id.txt_la_xl);
        carFour = (TextView) findViewById(R.id.txt_la_xll);

        txtCarArrivalEstimatedTime = (TextView) findViewById(R.id.txt_min_time);

//        ivActionSearch = (ImageView) toolbarHome.findViewById(R.id.ic_action_search);

        txtCarAvailability = (TextView) findViewById(R.id.txt_cars_available);
        txtSource = (TextView) findViewById(R.id.txt_source);
        txtDestination = (TextView) findViewById(R.id.txt_destination);

        txtTime = (TextView) findViewById(R.id.txt_time);
        txtMaxSize = (TextView) findViewById(R.id.txt_max_size);
        txtFare = (TextView) findViewById(R.id.txt_fare);
        txtEstimatedDestination = (TextView) findViewById(R.id.txt_estimated_destination);

        cvConfirmationPage = (CardView) findViewById(R.id.cv_confirmation_page);

        txtEstimatedFare = (TextView) findViewById(R.id.txt_estimated_fare);

        llProgressBar = (LinearLayout) findViewById(R.id.ll_landing_progress_bar);
        llEstimation = (LinearLayout) findViewById(R.id.ll_estimation);
        llFare = (LinearLayout) findViewById(R.id.ll_fare);

        flLandingPage = (FrameLayout) findViewById(R.id.fl_landing_page);

        framePickup = (FrameLayout) findViewById(R.id.frame_pickup_landing_page);
        ivMarker = (ImageView) findViewById(R.id.iv_marker);

        llLandingBottomBar = (LinearLayout) findViewById(R.id.ll_landing_estimation_bottom_sheet);
        ivLocationButton = (FloatingActionButton) findViewById(R.id.fab_location_button);

        txtActionSearch = (TextView) toolbarHome.findViewById(R.id.txt_action_search);

        txtTotalFare = (TextView) findViewById(R.id.txt_total_fare);

        viewDottedLine = (View) findViewById(R.id.view_dotted_line);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            txtActionSearch.setCompoundDrawablesRelative(null,null,null,null);
        }
*/

        llConfirmation = (LinearLayout) findViewById(R.id.ll_confirmation);
        btnRequest = (Button) findViewById(R.id.btn_request);

        txtFareLabel = (TextView) findViewById(R.id.txt_fare_lable);

        setBottomSheetBehavior();

        param1 = flLandingPage.getLayoutParams();
        param1.height = (int) (height - getStatusBarHeight() - mActionBarHeight);
        Log.i(TAG, "onSlide: PAram Height : " + param1.height);
        flLandingPage.setLayoutParams(param1);

        LocationUpdateListener locationUpdateListener = new LocationUpdateListener() {
            @Override
            public void onLocationUpdated(Location location) {
                Config.getInstance().setCurrentLatitude("" + location.getLatitude());
                Config.getInstance().setCurrentLongitude("" + location.getLongitude());

                Log.i(TAG, "onLocationChanged: LATITUDE : " + location.getLatitude());
                Log.i(TAG, "onLocationChanged: LONGITUDE : " + location.getLongitude());

                if (isInit) {
                    getData();
                    isInit = false;
                }
                if (sourceBean == null && mMap != null) {
                    LastLocation = location;
                    displayLocation();
                }

            }
        };
        addLocationUpdateListener(locationUpdateListener);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionCheckCompleted(int requestCode, boolean isPermissionGranted) {

                if (requestCode == REQUEST_PERMISSIONS_LOCATION & isPermissionGranted) {

                    Log.i(TAG, "onPermissionCheckCompleted: PERMISSION GRANTED !!!!");
                    if (checkLocationSettingsStatus() && checkPlayServices()) {
                        getCurrentLocation();
                    }
                }

            }
        };

        addPermissionListener(permissionListener);

    }

    public void setBottomSheetBehavior() {

        bottomSheetBehavior = BottomSheetBehavior.from(llLandingBottomBar);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                /*if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING

                bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING){
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
                }/*/
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(TAG, "onSlide: offset : " + slideOffset);
// mapFragmentView.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();

                try {
                    param = mapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight/* - (80 * px * (1 - slideOffset))*/ - bottomSheet.getHeight() * (slideOffset));
//                Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    mapFragment.getView().setLayoutParams(param);

                    param1 = flLandingPage.getLayoutParams();
                    param1.height = (int) (height - getStatusBarHeight() - mActionBarHeight /*- (80 * px * (1 - slideOffset))*/ - bottomSheet.getHeight() * (slideOffset));
                    Log.i(TAG, "onSlide: PAram Height : " + param1.height);
                    flLandingPage.setLayoutParams(param1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_home_map);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0, (int) ((100 * px) + mActionBarHeight + getStatusBarHeight()), 0, (int) (100 * px));

                initMapLoad();

            }
        });
    }

    private void initMapLoad() {

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getPeekHeight() == 100 * px) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {

                /*if (sourceBean != null & destinationBean != null) {
                    fetchTotalfare();
                    txtFare.setText(fareBean.getTotalFare());
                }*/
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getPeekHeight() == 100 * px) {
                    bottomSheetBehavior.setPeekHeight(0);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                if (!isConfirmationPage) {
                    mMap.getUiSettings().setScrollGesturesEnabled(true);
                    mMap.setMaxZoomPreference(18f);
                    framePickup.setVisibility(View.INVISIBLE);
                    ivBottomMarker.setVisibility(View.INVISIBLE);
                    ivMarker.setVisibility(View.VISIBLE);
                    ivLocationButton.setVisibility(View.INVISIBLE);

                    isCameraMoved = true;
                }
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                if (sourceBean != null & destinationBean != null) {
                    if (!isConfirmationPage) {
                        fetchPolyPoints(false);
                    }
                    if (fareBean != null) {
                        txtFare.setText(fareBean.getTotalFare());
                    }
                }

                if (!isConfirmationPage) {

                    CameraPosition postion = mMap.getCameraPosition();
                    LatLng center = postion.target;

                    framePickup.setVisibility(View.VISIBLE);
                    ivBottomMarker.setVisibility(View.VISIBLE);
                    ivMarker.setVisibility(View.INVISIBLE);
                    ivLocationButton.setVisibility(View.VISIBLE);

                    if (bottomSheetBehavior.getPeekHeight() == 0) {
                        bottomSheetBehavior.setPeekHeight((int) (100 * px));
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        llLandingBottomBar.animate().translationY(00*px).setDuration(1000).start();
                    }

                    Log.i(TAG, "onCameraIdle: GetLocationName Called : " + center);
                    if (isCameraMoved) {

                        getLocationName(String.valueOf(center.latitude), String.valueOf(center.longitude));
//                        getLocationName(center.latitude, center.longitude);

                        if (sourceBean == null)
                            sourceBean = new PlaceBean();
                        sourceBean.setLatitude(String.valueOf(center.latitude));
                        sourceBean.setLongitude(String.valueOf(center.longitude));

                        if (App.isNetworkAvailable()) {
                            fetchLandingPageDetails();
//                            fetchCarDetails();
                        } else {
                            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                        }

                        if (destinationBean != null) {
//                            getEstimatedFare();
                        }
                    }
                    isCameraMoved = false;
                }
            }
        });
    }


    private void initFCM() {

        FCMRegistrationTask fcmRegistrationTask = new FCMRegistrationTask();
        fcmRegistrationTask.setFCMRegistrationTaskListener(new FCMRegistrationTask.FCMRegistrationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(String fcmToken) {

                Log.i(TAG, "dataDownloadedSuccessfully: FCM TOKEN : " + fcmToken);

                JSONObject postData = getUpdateFCMTokenJSObj(fcmToken);

                DataManager.performUpdateFCMToken(postData, new BasicListener() {
                    @Override
                    public void onLoadCompleted(BasicBean basicBean) {

                    }

                    @Override
                    public void onLoadFailed(String error) {

                    }
                });

            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        fcmRegistrationTask.execute();

    }


    private JSONObject getUpdateFCMTokenJSObj(String fcmToken) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("fcm_token", fcmToken);
//            postData.put("user_id", userBean.getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SEARCH_DESTINATION_SELECT && resultCode == RESULT_OK) {

            destinationBean = (PlaceBean) data.getSerializableExtra("bean");

            if (sourceBean != null && destinationBean != null) {
                if (sourceBean.getName().equalsIgnoreCase(destinationBean.getName())) {

                    mMap.clear();
                    destinationBean = null;
                    txtDestination.setText("");
                    rlFare.setVisibility(View.GONE);

                    onSourceSelect();

                    Snackbar.make(coordinatorLayout, R.string.message_source_and_destination_are_same, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                }
            }

            Log.i(TAG, "onActivityResult: ON DESTINATION SELECT ");

            fetchCarDetails();

            if (destinationBean != null) {
                llFare.setVisibility(View.GONE);
                llConfirmationProgress.setVisibility(View.VISIBLE);
            }

            Log.i(TAG, "onActivityResult: SourceLatitude : " + sourceBean.getDLatitude());
            Log.i(TAG, "onActivityResult: SourceLongitude : " + sourceBean.getDLongitude());

//            Log.i(TAG, "onActivityResult: DestinationLatitude : " + destinationBean.getDLatitude());
//            Log.i(TAG, "onActivityResult: DestinationLongitude : " + destinationBean.getDLongitude());

            if (sourceBean != null && destinationBean != null) {
                onDestinationSelect();
            }
        }

        if (requestCode == REQ_SEARCH_SOURCE_SELECT && resultCode == RESULT_OK) {

            sourceBean = (PlaceBean) data.getSerializableExtra("bean");

            if (sourceBean != null && destinationBean != null) {
                if (sourceBean.getName().equalsIgnoreCase(destinationBean.getName())) {

                    mMap.clear();
                    destinationBean = null;
                    txtDestination.setText("");
                    rlFare.setVisibility(View.GONE);

                    Snackbar.make(coordinatorLayout, R.string.message_source_and_destination_are_same, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }

            Log.i(TAG, "onActivityResult: SourceName" + sourceBean.getName());
//            Log.i(TAG, "onActivityResult: DestinationName" + destinationBean.getName());
            Log.i(TAG, "onActivityResult: SourceLatitude : " + sourceBean.getDLatitude());
            Log.i(TAG, "onActivityResult: SourceLongitude : " + sourceBean.getDLongitude());

            fetchCarDetails();
            if (sourceBean != null) {
                onSourceSelect();
            }
        }

        if (requestCode == REQ_REQUEST_RIDE && resultCode == RESULT_OK) {

            DriverBean driverBean = (DriverBean) data.getSerializableExtra("bean");
            startActivity(new Intent(this, OnTripActivity.class)
                    .putExtra("bean", driverBean)
                    .putExtra("source", sourceBean)
                    .putExtra("destination", destinationBean)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }

        if (requestCode == REQ_ESTIMATED_DESTINATION && resultCode == RESULT_OK) {

            destinationBean = (PlaceBean) data.getSerializableExtra("bean");
            llProgressBar.setVisibility(View.VISIBLE);
            llEstimation.setVisibility(View.GONE);

            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }


            if (sourceBean == null && mMap != null) {
                sourceBean = new PlaceBean();
                LatLng center = mMap.getCameraPosition().target;
                sourceBean.setLatitude(String.valueOf(center.latitude));
                sourceBean.setLongitude(String.valueOf(center.longitude));
            } else if (sourceBean == null) {
                Snackbar.make(coordinatorLayout, AppConstants.WEB_ERROR_MSG, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                return;
            }
            fetchPolyPoints(false);
            showFareEstimation(destinationBean.getName());
        }
    }


    private void onSourceSelect() {

        mMap.clear();
        txtSource.setText(sourceBean.getName());
        onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
        try {
            if (destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
                onPlotLocation(true, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (sourceBean.getDLatitude() != 0 && sourceBean.getDLongitude() != 0 && destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {
                rlFare.setVisibility(View.VISIBLE);
                viewDottedLine.setVisibility(View.VISIBLE);
                mapAutoZoom();
                fetchPolyPoints(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onDestinationSelect() {
        mMap.clear();
        onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
        onPlotLocation(true, LOCATION_DESTINATION, destinationBean.getDLatitude(), destinationBean.getDLongitude());
        txtDestination.setText(destinationBean.getName());
        if (sourceBean.getDLatitude() != 0 && sourceBean.getDLongitude() != 0
                && destinationBean.getDLatitude() != 0 && destinationBean.getDLongitude() != 0) {

            rlFare.setVisibility(View.VISIBLE);
            viewDottedLine.setVisibility(View.VISIBLE);
            mapAutoZoom();
            fetchPolyPoints(true);
        }

    }

    private void showFareEstimation(String location) {
        if (destinationBean != null) {
//            fetchTotalfare();
            txtFareLabel.setText(R.string.label_estd_fare);
        }
        txtFareEstimate.setVisibility(View.GONE);
        txtTo.setVisibility(View.VISIBLE);
        llDestinationEstimated.setVisibility(View.VISIBLE);
        txtEstimatedDestination.setText(location);
    }


    public void onLocationButtonClick(View view) {

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        Log.i(TAG, "onLocationButtonClick: Clicked");

//        displayLocation();

        sourceBean = null;
        if (!checkForLocationPermissions()) {
            getLocationPermissions();
        } else {
            if (checkLocationSettingsStatus()) {
                getCurrentLocation();
            }
        }
    }

    public void onFareEstimateClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (mMap != null) {
            LatLng center = mMap.getCameraPosition().target;
            if (sourceBean == null)
                sourceBean = new PlaceBean();
            sourceBean.setLatitude(String.valueOf(center.latitude));
            sourceBean.setLongitude(String.valueOf(center.longitude));
        }

        searchType = AppConstants.SEARCH_ESTIMATED_DESTINATION;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_ESTIMATED_DESTINATION);
    }

    public void onPickUpLocationClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

/*        CarBean bean1 = landingPageBean.getCars().get(0);
        CarBean bean2 = landingPageBean.getCars().get(1);
        CarBean bean3 = landingPageBean.getCars().get(2);
        CarBean bean4 = landingPageBean.getCars().get(3);*/

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setPeekHeight(0);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }

       /* if (carType.equalsIgnoreCase("1")) {
            btnRequest.setText("Request " + bean1.getCarName());
        }

        if (carType.equalsIgnoreCase("2")) {
            btnRequest.setText("Request " + bean2.getCarName());
        }

        if (carType.equalsIgnoreCase("3")) {
            btnRequest.setText("Request " + bean3.getCarName());
        }

        if (carType.equalsIgnoreCase("4")) {
            btnRequest.setText("Request " + bean4.getCarName());
        }*/

        if (landingPageBean == null) {
            Snackbar.make(coordinatorLayout, R.string.message_service_not_available, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return;
        }

        CarBean bean = landingPageBean.getCar(carType);
        btnRequest.setText(getString(R.string.label_request) + " " + (bean != null
                ? bean.getCarName() : getString(R.string.app_name)));


        llFare.setVisibility(View.VISIBLE);
        rlFare.setVisibility(View.GONE);

        LatLng center = mMap.getCameraPosition().target;
//        center = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();

        if (sourceBean == null) {
            sourceBean = new PlaceBean();
        }

        sourceBean.setLatitude(String.valueOf(center.latitude));
        sourceBean.setLongitude(String.valueOf(center.longitude));

        onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());

        if (txtDestination.length() > 0) {
            rlFare.setVisibility(View.VISIBLE);
            viewDottedLine.setVisibility(View.VISIBLE);

        }

        if (!isConfirmationPage) {

            layoutConfirmationPage();

            txtActionSearch.setText(R.string.label_confirmation);

            if (!llConfirmation.isShown()) {

                cvConfirmationPage.setVisibility(View.VISIBLE);
                llConfirmation.setVisibility(View.VISIBLE);

            }

            if (!btnRequest.isShown()) {
                btnRequest.setVisibility(View.VISIBLE);
            }
            isConfirmationPage = true;

        }

        if (destinationBean != null) {
            mMap.clear();
            llFare.setVisibility(View.GONE);
            llConfirmationProgress.setVisibility(View.VISIBLE);
            txtDestination.setText(destinationBean.getName());

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onDestinationSelect();
                }
            }, 2000);
            llEstimation.setVisibility(View.GONE);
        }
    }

    public void layoutConfirmationPage() {

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        if (!isConfirmationPage) {
            bottomSheetBehavior.setPeekHeight(0);

            ivBottomMarker.setVisibility(View.GONE);

            ivMarker.setVisibility(View.GONE);

            ivLocationButton.setVisibility(View.GONE);

            framePickup.setVisibility(View.GONE);

        }
    }

    public void onBackClick() {

        mMap.clear();

        fetchLandingPageDetails();

        try {
            ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
            params.height = height;
            mapFragment.getView().setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        llConfirmationProgress.setVisibility(View.GONE);

        getCurrentLocation();
//        txtSource.setText("");
        txtDestination.setText("");

        txtFare.setVisibility(View.VISIBLE);

        rlFare.setVisibility(View.GONE);
        llFare.setVisibility(View.GONE);

        viewDottedLine.setVisibility(View.GONE);

        Log.i(TAG, "onBackClick: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        CameraPosition postion = mMap.getCameraPosition();
        LatLng center = postion.target;

        txtActionSearch.setText(Config.getInstance().getCurrentLocation());

        cvConfirmationPage.setVisibility(View.GONE);
//        rvCarList.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setPeekHeight((int) (100 * px));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

//        llEstimation.setVisibility(View.VISIBLE);
        framePickup.setVisibility(View.VISIBLE);
        ivBottomMarker.setVisibility(View.VISIBLE);
        ivMarker.setVisibility(View.GONE);
        ivLocationButton.setVisibility(View.VISIBLE);
        btnRequest.setVisibility(View.GONE);
        llConfirmation.setVisibility(View.GONE);

        mMap.clear();

        sourceBean = null;
        destinationBean = null;

        txtTo.setVisibility(View.GONE);
        llDestinationEstimated.setVisibility(View.GONE);
        txtFareEstimate.setVisibility(View.VISIBLE);

        isConfirmationPage = false;
    }


    public void onLaGoCarClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(0).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        txtCarOne.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
        txtCarTwo.setBackgroundResource(R.color.transparent);
        txtCarThree.setBackgroundResource(R.color.transparent);
        carFour.setBackgroundResource(R.color.transparent);
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }

    public void onLaXCarClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(1).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        txtCarTwo.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
        txtCarOne.setBackgroundResource(R.color.transparent);
        txtCarThree.setBackgroundResource(R.color.transparent);
        carFour.setBackgroundResource(R.color.transparent);
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }

    public void onCarXlClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(2).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        txtCarThree.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
        txtCarOne.setBackgroundResource(R.color.transparent);
        txtCarTwo.setBackgroundResource(R.color.transparent);
        carFour.setBackgroundResource(R.color.transparent);
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }

    public void onCarXxlClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        carType = landingPageBean.getCars().get(3).getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        llProgressBar.setVisibility(View.VISIBLE);
        llEstimation.setVisibility(View.GONE);

        carFour.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
        txtCarOne.setBackgroundResource(R.color.transparent);
        txtCarTwo.setBackgroundResource(R.color.transparent);
        txtCarThree.setBackgroundResource(R.color.transparent);
        carFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txtCarThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtCarOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        if (sourceBean != null & destinationBean != null) {
            fetchPolyPoints(false);
            if (fareBean != null) {
                txtFare.setText(fareBean.getTotalFare());
            }
            txtFareLabel.setText(R.string.label_estd_fare);
        }
    }


    public void onCarTypeSelected(int position, CarBean bean) {

        carType = bean.getCarID();

        if (App.isNetworkAvailable()) {
            fetchCarDetails();
            llProgressBar.setVisibility(View.VISIBLE);
            llEstimation.setVisibility(View.GONE);

            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            if (sourceBean != null & destinationBean != null) {
                fetchPolyPoints(false);
                if (fareBean != null) {
                    txtFare.setText(fareBean.getTotalFare());
                }
                txtFareLabel.setText(R.string.label_estd_fare);
            }
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    public void onSourceClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        searchType = AppConstants.SEARCH_SOURCE;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_SEARCH_SOURCE_SELECT);

    }

    public void onDestinationClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        searchType = AppConstants.SEARCH_DESTINATION;

        Intent intent = new Intent(LandingPageActivity.this, SearchPageActivity.class);
        intent.putExtra("search_type", searchType);
        startActivityForResult(intent, REQ_SEARCH_DESTINATION_SELECT);
    }


    private void displayLocation() {

        Log.i(TAG, "displayLocation: OnPlotLocation Called .........>>>>>>>>>>>>>>>>>>>>>>>>..");

        if (LastLocation != null && !isConfirmationPage) {

            onPlotLocation(false, LOCATION_SOURCE, LastLocation.getLatitude(), LastLocation.getLongitude());
            getLocationName(String.valueOf(LastLocation.getLatitude()), String.valueOf(LastLocation.getLongitude()));
//            getLocationName(LastLocation.getLatitude(), LastLocation.getLongitude());
        }
    }

    private void getLocationName(double currentLatitude, double currentLongitude) {

        LocationTask locationTask = new LocationTask(currentLatitude, currentLongitude);
        locationTask.setLocationTaskListener(new LocationTask.LocationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(PlaceBean placeBean) {

                sourceBean = placeBean;

                if (placeBean != null) {
                    txtActionSearch.setText(placeBean.getName());
                    txtSource.setText(placeBean.getName());
                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationTask.execute();


    }

    protected void getLocationName(final String latitude, final String longitude) {

//        swipeView.setRefreshing(true);

        /*String currentLatitude = Config.getInstance().getCurrentLatitude();
        String currentLongitude = Config.getInstance().getCurrentLongitude();

        System.out.println("Current Location : " + currentLatitude + "," + currentLongitude);*/

        HashMap<String, String> urlParams = new HashMap<>();
        //	postData.put("uid", id);
        urlParams.put("latlng", latitude + "," + longitude);
        urlParams.put("sensor", "true");
        urlParams.put("key", getString(R.string.browser_api_key));

        LocationNameTask locationNameTask = new LocationNameTask(urlParams);
        locationNameTask.setLocationNameTaskListener(new LocationNameTask.LocationNameTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(String address) {
                //	System.out.println(landingBean.getStatus());
                if (null != address) {
                    System.out.println("Location Name Retrieved : " + address);
                    Config.getInstance().setCurrentLocation(address);

                    txtActionSearch.setText(address);
                    txtSource.setText(address);
                    if (sourceBean == null)
                        sourceBean = new PlaceBean();
                    sourceBean.setAddress(address);
                    sourceBean.setName(address);
                    sourceBean.setLatitude(latitude);
                    sourceBean.setLongitude(longitude);
                    /*					txtLocation.setText(address);
                    Toast.makeText(CreateActivity.this,"Location Name Retrieved : "+address, Toast.LENGTH_SHORT).show();
					 */
                }
            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        locationNameTask.execute();
    }

    private void fetchCarDetails() {

        if (destinationBean == null) {
            llEstimation.setVisibility(View.GONE);
            llProgressBar.setVisibility(View.VISIBLE);
        }

//        swipeView.setRefreshing(true)
//        center = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();

        if (sourceBean == null) {
            if (mMap != null) {
                LatLng center = mMap.getCameraPosition().target;
                sourceBean = new PlaceBean();
                sourceBean.setLatitude(String.valueOf(center.latitude));
                sourceBean.setLongitude(String.valueOf(center.longitude));
            } else {
                Log.i(TAG, "fetchCarDetails: CAR DETAILS ERROR ");
                Snackbar.make(coordinatorLayout, AppConstants.WEB_ERROR_MSG, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                return;
            }
        }


        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("car_type", carType);
        urlParams.put("latitude", sourceBean.getLatitude());
        urlParams.put("longitude", sourceBean.getLongitude());

        DataManager.fetchCarAvailability(urlParams, new CarInfoListener() {
            @Override
            public void onLoadCompleted(CarBean carBeanWS) {
                swipeView.setRefreshing(false);
                carBean = carBeanWS;
                populateCarDetails(carBeanWS);

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                txtCarAvailability.setText(R.string.label_no_cars_available);
                txtCarArrivalEstimatedTime.setVisibility(View.GONE);
                Toast.makeText(LandingPageActivity.this, error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateCarDetails(CarBean carBean) {

        if (carBean.getCarsAvailable().equalsIgnoreCase(getString(R.string.label_no_cars_available))) {
            txtCarAvailability.setText(R.string.label_no_cars_available);
            txtCarArrivalEstimatedTime.setVisibility(View.GONE);
        } else {
            txtCarArrivalEstimatedTime.setVisibility(View.VISIBLE);
            txtCarAvailability.setText(R.string.btn_set_pickup_location);
        }

        txtCarArrivalEstimatedTime.setText(carBean.getMinTime());
        txtTime.setText(carBean.getMinTime());
        txtMaxSize.setText(carBean.getMaxSize());
        if (destinationBean == null) {
            txtFare.setText(carBean.getMinFare());
        }

        if (destinationBean == null) {
            llEstimation.setVisibility(View.VISIBLE);
            llProgressBar.setVisibility(View.GONE);
        }
    }

    public void fetchTotalfare() {

        HashMap<String, String> urlParams = null;
        try {
            urlParams = new HashMap<>();
            urlParams.put("car_type", String.valueOf(carType));
            if (sourceBean.getName() != null && !sourceBean.getName().equals("")) {
                urlParams.put("source", sourceBean.getName());
            }
            if (destinationBean.getName() != null && destinationBean.getName().equals("")) {
                urlParams.put("destination", destinationBean.getName());
            }
            urlParams.put("source_latitude", sourceBean.getLatitude());
            urlParams.put("source_longitude", sourceBean.getLongitude());
            urlParams.put("destination_latitude", destinationBean.getLatitude());
            urlParams.put("destination_longitude", destinationBean.getLongitude());
            urlParams.put("distance", String.valueOf(distance));
            urlParams.put("time", String.valueOf(time));

            Log.i(TAG, "fetchTotalfare: Time " + time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DataManager.fetchTotalFare(urlParams, new TotalFareListener() {

            @Override
            public void onLoadCompleted(FareBean fareBeanWS) {

                if (isConfirmationPage) {
                    llFare.setVisibility(View.VISIBLE);
                    llConfirmationProgress.setVisibility(View.GONE);

                }
                swipeView.setRefreshing(false);
                fareBean = fareBeanWS;
                populateFareDetails(fareBeanWS);

                txtFare.setVisibility(View.VISIBLE);

                llProgressBar.setVisibility(View.GONE);
                llEstimation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

                if (isConfirmationPage) {

                    txtFareEstimate.setVisibility(View.VISIBLE);
                    llProgressBar.setVisibility(View.GONE);
                }

                if (isConfirmationPage) {
                    llFare.setVisibility(View.VISIBLE);
                    rlFare.setVisibility(View.GONE);
                    llConfirmationProgress.setVisibility(View.GONE);
                }

                txtFare.setVisibility(View.GONE);

                txtEstimatedFare.setVisibility(View.GONE);

                llProgressBar.setVisibility(View.GONE);
                llEstimation.setVisibility(View.VISIBLE);

                if (!isFinishing()) {
                    PopupMessage popupMessage = new PopupMessage(LandingPageActivity.this);
                    popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
                        @Override
                        public void actionCompletedSuccessfully(boolean result) {

                            if (!isConfirmationPage) {
                                destinationBean = null;
                                txtTo.setVisibility(View.GONE);
                                txtFareLabel.setText(R.string.label_min_fare);
                                txtFare.setVisibility(View.VISIBLE);
                                txtFare.setText(carBean.getMinFare());
                                llDestinationEstimated.setVisibility(View.GONE);
                                txtFareEstimate.setVisibility(View.VISIBLE);
                            } else {
                                mMap.clear();
                                onPlotLocation(true, LOCATION_SOURCE, sourceBean.getDLatitude(), sourceBean.getDLongitude());
                                destinationBean = null;
                                txtDestination.setText("");
                                rlFare.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void actionFailed() {

                        }
                    });
                    popupMessage.show(error, 0);
                }

            }
        });
    }

    private void populateFareDetails(FareBean fareBean) {

        if (fareBean.getTotalFare() != null) {
            txtTotalFare.setText(fareBean.getTotalFare());
            txtFare.setText(fareBean.getTotalFare());
        }
    }

    public void getEstimatedFare() {

        String source = txtActionSearch.getText().toString();
        String destination = txtEstimatedDestination.getText().toString();

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("source", source);
        urlParams.put("destination", destination);

        urlParams.put("car_type", String.valueOf(carType));

        urlParams.put("source_latitude", sourceBean.getLatitude());
        urlParams.put("source_longitude", sourceBean.getLongitude());
        urlParams.put("destination_latitude", destinationBean.getLatitude());
        urlParams.put("destination_longitude", destinationBean.getLongitude());

        urlParams.put("distance", String.valueOf(distance));
        urlParams.put("time", String.valueOf(time));

        Log.i(TAG, "getEstimatedFare: Time " + time);

        DataManager.fetchTotalFare(urlParams, new TotalFareListener() {

            @Override
            public void onLoadCompleted(FareBean fareBean) {
                swipeView.setRefreshing(false);
                populateEstimatedFare(fareBean);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    public void populateEstimatedFare(FareBean fareBean) {

        txtFare.setText(fareBean.getTotalFare());
    }

    public void onEstimatedDestinationClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        onFareEstimateClick(view);
    }

    public void onRequestRideClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (carBean != null) {
            if (carBean.getCarsAvailable().equalsIgnoreCase(getString(R.string.label_no_cars_available)) && txtDestination.getText().length() != 0) {

                Snackbar.make(coordinatorLayout, R.string.message_no_cars_available_for_the_location, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            } else {

                if (txtDestination.getText().length() == 0) {
                    Snackbar.make(coordinatorLayout, R.string.message_destination_required, Snackbar.LENGTH_SHORT)
                            .setAction(R.string.btn_refresh, snackBarRefreshOnClickListener).show();
                } else {
                    if (fareBean != null && sourceBean != null && destinationBean != null) {
                        Intent intent = new Intent(LandingPageActivity.this, RequestingPageActivity.class);
                        intent.putExtra("fare_bean", fareBean);
                        intent.putExtra("car_type", String.valueOf(carType));
                        intent.putExtra("source_bean", sourceBean);
                        intent.putExtra("destination_bean", destinationBean);

    /*                    intent.putExtra("source_latitude", sourceBean.getLatitude());
                        intent.putExtra("source_longitude", sourceBean.getLongitude());
                        intent.putExtra("destination_latitude", destinationBean.getLatitude());
                        intent.putExtra("destination_longitude", destinationBean.getLongitude());*/

                        startActivityForResult(intent, REQ_REQUEST_RIDE);
                    } else {
                        Snackbar.make(coordinatorLayout, R.string.message_something_went_wrong, Snackbar.LENGTH_LONG)
                                .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                    }
                }
            }
        }
    }

    public void fetchLandingPageDetails() {

        Log.i(TAG, "fetchLandingPageDetails: AuthToken" + Config.getInstance().getAuthToken());

        HashMap<String, String> urlParams = new HashMap<>();

        if (mMap != null) {
            LatLng center = mMap.getCameraPosition().target;
            urlParams.put("latitude", String.valueOf(center.latitude));
            urlParams.put("longitude", String.valueOf(center.longitude));
        } else {
            urlParams.put("latitude", Config.getInstance().getCurrentLatitude());
            urlParams.put("longitude", Config.getInstance().getCurrentLongitude());
        }
        DataManager.fetchLandingPageDetails(urlParams, new LandingPageListener() {

            @Override
            public void onLoadCompleted(LandingPageBean landingPageBeanWS) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(false, false);
                landingPageBean = landingPageBeanWS;
                populateLandingPageDetails(landingPageBeanWS);

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
            }
        });
    }

    private void populateLandingPageDetails(LandingPageBean landingPageBean) {


        Collections.sort(landingPageBean.getCars());

        /*CarBean bean1 = landingPageBean.getCars().get(0);
        CarBean bean2 = landingPageBean.getCars().get(1);
        CarBean bean3 = landingPageBean.getCars().get(2);
        CarBean bean4 = landingPageBean.getCars().get(3);

        txtCarOne.setText(bean1.getCarName());
        txtCarTwo.setText(bean2.getCarName());
        txtCarThree.setText(bean3.getCarName());
        carFour.setText(bean4.getCarName());

        Glide.with(getApplicationContext())
                .load(bean1.getCarImage())
                .into(carOneImage);

        Glide.with(getApplicationContext())
                .load(bean2.getCarImage())
                .into(carTwoImage);

        Glide.with(getApplicationContext())
                .load(bean3.getCarImage())
                .into(carThreeImage);

        Glide.with(getApplicationContext())
                .load(bean4.getCarImage())
                .into(carFourImage);*/


        if (adapterCarTypes == null) {

            adapterCarTypes = new CarTypeRecyclerAdapter(this, landingPageBean);
            adapterCarTypes.setCarTypeRecyclerAdapterListener(new CarTypeRecyclerAdapter.CarTypeRecyclerAdapterListener() {
                @Override
                public void onRefresh() {

                }

                @Override
                public void onSelectedCar(int position, CarBean carBean) {
                    carType = carBean.getCarID();
                    onCarTypeSelected(position, carBean);
                }
            });
            rvCarTypes.setAdapter(adapterCarTypes);
        } else {
            if (landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()) {
                adapterCarTypes.setLandingPageBean(landingPageBean);
                adapterCarTypes.notifyDataSetChanged();
            } else {
                txtCarAvailability.setText(R.string.label_no_cars_available);
                txtCarArrivalEstimatedTime.setVisibility(View.GONE);
            }
        }

        if (carType.equalsIgnoreCase("") || landingPageBean.getCar(carType) == null) {
            carType = landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()
                    ? landingPageBean.getCars().get(0).getCarID() : "-1";
        }

        fetchCarDetails();
    }

    public void onPlotLocation(boolean isMarkerNeeded, int type, double latitude, double longitude) {

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(latitude, longitude);
            if (isMarkerNeeded) {
                switch (type) {
                    case LOCATION_SOURCE:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_marker)));
                        break;
                    case LOCATION_DESTINATION:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker)));
                        break;
                    default:
                        mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.defaultMarker()));
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
        urlParams.put("origin", sourceBean.getLatitude() + "," + sourceBean.getLongitude());
        urlParams.put("destination", destinationBean.getLatitude() + "," + destinationBean.getLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));
//        }

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;
                time = String.valueOf(polyPointsBean.getTime());
                distance = String.valueOf(polyPointsBean.getDistance());

                Log.i(TAG, "onLoadCompleted: Time Taken" + polyPointsBean.getTimeText());
                Log.i(TAG, "onLoadCompleted: Distance" + polyPointsBean.getDistanceText());

                fetchTotalfare();

                if (isPolyLineNeeded) {
                    if (!isDestinationEstimateSelect)
                        populatePath();
                    isDestinationEstimateSelect = false;
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Log.i(TAG, "onLoadFailed: POLYPOINTS : ");
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
    }

    public void mapAutoZoom() {


        if (sourceBean != null && destinationBean != null) {
            newLatLng1 = new LatLng(sourceBean.getDLatitude(), sourceBean.getDLongitude());
            newLatLng2 = new LatLng(destinationBean.getDLatitude(), destinationBean.getDLongitude());
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
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

    public void onLayoutClickLandingPage(View view) {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public int getNavBarHeight() {
        Context context = App.getInstance().getApplicationContext();
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        if (!checkForLocationPermissions()) {
            getLocationPermissions();
        } else {
            if (checkLocationSettingsStatus()) {
                getCurrentLocation();
            }
        }
        return false;
    }

}




