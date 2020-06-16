package in.techware.lataxicustomer.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.listeners.DriverRatingListener;
import in.techware.lataxicustomer.listeners.PolyPointsListener;
import in.techware.lataxicustomer.listeners.SuccessListener;
import in.techware.lataxicustomer.model.DriverRatingBean;
import in.techware.lataxicustomer.model.PolyPointsBean;
import in.techware.lataxicustomer.model.SuccessBean;
import in.techware.lataxicustomer.net.DataManager;
import in.techware.lataxicustomer.util.AppConstants;
import in.techware.lataxicustomer.util.TimeUtil;
import in.techware.lataxicustomer.viewModels.TripFeedbackViewModel;

public class TripFeedbackActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "";

    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true)
            .liteMode(true);

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private View.OnClickListener snackBarRefreshOnClickListener;

    private TripFeedbackViewModel viewModel;

    private SuccessBean successBean;
    private String tripID;
    private TextView txtToolbarDate;
    private TextView txtDriverName;
    private TextView txtFare;
    private Button btnRatingSubmit;
    private ImageView ivDriverPhoto;
    private ImageView ivBadFeedbackDriverPhoto;
    private ImageView ivGoodFeedbackDriverPhoto;
    private RatingBar ratingDriver;
    private ViewFlipper viewFlipper;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private TextView txtBadRemarksService;
    private TextView txtBadRemarksCleanliness;
    private TextView txtBadRemarksDriving;
    private TextView txtBadRemarksComfort;
    private TextView txtBadRemarksCarQuality;
    private TextView txtBadRemarksOther;
    private Button btnBadRemarksSubmit;
    private RatingBar ratingBadRemark;
    private TextView txtGoodRemarksService;
    private TextView txtGoodRemarksCleanliness;
    private TextView txtGoodRemarksDriving;
    private TextView txtGoodRemarksComfort;
    private TextView txtGoodRemarksCarQuality;
    private TextView txtGoodRemarksOther;
    private Button btnGoodRemarksSubmit;
    private RatingBar ratingGoodRemark;
    private EditText etxtFeedback;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_feedback);

        tripID = getIntent().getStringExtra("id");

        viewModel = (TripFeedbackViewModel) ViewModelProviders.of(this).get(TripFeedbackViewModel.class);

        initViews();
        initMap();


        Log.i(TAG, "onCreate: TripId" + tripID);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, SplashActivity.class)
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

        if (successBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }

    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchTripCompletionDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    public void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };

        View lytToolbar = getLayoutInflater().inflate(R.layout.layout_toolbar_trip_feedback, toolbar);
        txtToolbarDate = lytToolbar.findViewById(R.id.txt_toolbar_trip_feedback_date);

        viewFlipper = findViewById(R.id.viewFlipper_trip_feedback);

        ivDriverPhoto = (ImageView) findViewById(R.id.iv_trip_feedback_rating_driver_photo);
        ivBadFeedbackDriverPhoto = (ImageView) findViewById(R.id.iv_trip_feedback_bad_remarks_driver_photo);
        ivGoodFeedbackDriverPhoto = (ImageView) findViewById(R.id.iv_trip_feedback_good_remarks_driver_photo);

        txtDriverName = (TextView) findViewById(R.id.txt_trip_feedback_rating_driver_name);
        txtFare = (TextView) findViewById(R.id.txt_trip_feedback_rating_fare);

        txtBadRemarksService = (TextView) findViewById(R.id.txt_trip_feedback_bad_remarks_service);
        txtBadRemarksCleanliness = (TextView) findViewById(R.id.txt_trip_feedback_bad_remarks_cleanliness);
        txtBadRemarksDriving = (TextView) findViewById(R.id.txt_trip_feedback_bad_remarks_driving);
        txtBadRemarksComfort = (TextView) findViewById(R.id.txt_trip_feedback_bad_remarks_comfort);
        txtBadRemarksCarQuality = (TextView) findViewById(R.id.txt_trip_feedback_bad_remarks_car_quality);
        txtBadRemarksOther = (TextView) findViewById(R.id.txt_trip_feedback_bad_remarks_other);

        txtGoodRemarksService = (TextView) findViewById(R.id.txt_trip_feedback_good_remarks_service);
        txtGoodRemarksCleanliness = (TextView) findViewById(R.id.txt_trip_feedback_good_remarks_cleanliness);
        txtGoodRemarksDriving = (TextView) findViewById(R.id.txt_trip_feedback_good_remarks_driving);
        txtGoodRemarksComfort = (TextView) findViewById(R.id.txt_trip_feedback_good_remarks_comfort);
        txtGoodRemarksCarQuality = (TextView) findViewById(R.id.txt_trip_feedback_good_remarks_car_quality);
        txtGoodRemarksOther = (TextView) findViewById(R.id.txt_trip_feedback_good_remarks_other);

        etxtFeedback = (EditText) findViewById(R.id.etxt_trip_feedback_feedback);

        btnRatingSubmit = (Button) findViewById(R.id.btn_trip_feedback_rating_next);
        btnBadRemarksSubmit = (Button) findViewById(R.id.btn_trip_feedback_bad_remarks_submit);
        btnGoodRemarksSubmit = (Button) findViewById(R.id.btn_driver_good_remarks_submit);
        btnSubmit = (Button) findViewById(R.id.btn_trip_feedback_submit);

        ratingDriver = (RatingBar) findViewById(R.id.rating_trip_feedback_driver);
        ratingBadRemark = (RatingBar) findViewById(R.id.rating_trip_feedback_bad_remark);
        ratingGoodRemark = (RatingBar) findViewById(R.id.rating_trip_feedback_good_remark);

        ratingBadRemark.setEnabled(false);
        ratingGoodRemark.setEnabled(false);

        setBadFeedbackClicks(txtBadRemarksService);
        setBadFeedbackClicks(txtBadRemarksCleanliness);
        setBadFeedbackClicks(txtBadRemarksDriving);
        setBadFeedbackClicks(txtBadRemarksComfort);
        setBadFeedbackClicks(txtBadRemarksCarQuality);
        setBadFeedbackClicks(txtBadRemarksOther);

        setGoodFeedbackClicks(txtGoodRemarksService);
        setGoodFeedbackClicks(txtGoodRemarksCleanliness);
        setGoodFeedbackClicks(txtGoodRemarksDriving);
        setGoodFeedbackClicks(txtGoodRemarksComfort);
        setGoodFeedbackClicks(txtGoodRemarksCarQuality);
        setGoodFeedbackClicks(txtGoodRemarksOther);

        ratingDriver.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                viewModel.setRating(rating);
            }
        });

        btnRatingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNextClick(v);
            }
        });
        btnBadRemarksSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNextClick(v);
            }
        });
        btnGoodRemarksSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNextClick(v);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNextClick(v);
            }
        });


    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map_lite);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0, (int) ((20 * px) /*+ mActionBarHeight + getStatusBarHeight()*/), 0, (int) (20 * px));

                if (successBean != null) {
                    onPlotLatLng(successBean.getDSourceLatitude(), successBean.getDSourceLongitude(),
                            successBean.getDDestinationLatitude(), successBean.getDDestinationLongitude());
                    mapAutoZoom();
                }

            }
        });
    }


    public void populateTripDetails() {

        Log.i(TAG, "populateTipDetails: SourceLatitude:" + viewModel.getSourceLatitude());
        Log.i(TAG, "populateTipDetails: SourceLongitude:" + viewModel.getSourceLongitude());


        txtToolbarDate.setText(TimeUtil.getDateFromUnix(TimeUtil.DATE_FORMAT_4,
                true, false, viewModel.getTime() * 1000, false));
        txtDriverName.setText(viewModel.getDriverName());
        txtFare.setText(viewModel.getFare());

        Glide.with(getApplicationContext())
                .load(viewModel.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .circleCrop())
                .into(ivDriverPhoto);

        Glide.with(getApplicationContext())
                .load(viewModel.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .circleCrop())
                .into(ivBadFeedbackDriverPhoto);

        Glide.with(getApplicationContext())
                .load(viewModel.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .circleCrop())
                .into(ivGoodFeedbackDriverPhoto);


        if (mMap != null) {
            onPlotLatLng(successBean.getDSourceLatitude(), successBean.getDSourceLongitude(),
                    successBean.getDDestinationLatitude(), successBean.getDDestinationLongitude());
            mapAutoZoom();
        }
        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    private void setBadFeedbackClicks(TextView view) {
        String feedback = (String) view.getTag();
        if (!viewModel.getBadFeedbackList().contains(feedback)) {
            viewModel.getBadFeedbackList().add(feedback);
            view.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
            view.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.white)));
        } else {
            viewModel.getBadFeedbackList().remove(feedback);
            view.setBackgroundResource(R.drawable.btn_click_gray_rectangle_with_semicircle_edge);
            view.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.text_feedback)));
        }
    }

    private void setGoodFeedbackClicks(TextView view) {
        String feedback = (String) view.getTag();
        if (!viewModel.getGoodFeedbackList().contains(feedback)) {
            viewModel.getGoodFeedbackList().add(feedback);
            view.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
            view.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.white)));
        } else {
            viewModel.getGoodFeedbackList().remove(feedback);
            view.setBackgroundResource(R.drawable.btn_click_gray_rectangle_with_semicircle_edge);
            view.setTextColor((ContextCompat.getColor(getApplicationContext(), R.color.text_feedback)));
        }
    }

    private void performNextClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (viewFlipper.getDisplayedChild() == 0) {
            viewModel.setRating(viewModel.getRating() != 0 ? viewModel.getRating() : .5f);
            ratingBadRemark.setRating(viewModel.getRating());
            ratingGoodRemark.setRating(viewModel.getRating());
            viewFlipper.setDisplayedChild(1);
        } else if (viewFlipper.getDisplayedChild() == 1) {
            viewFlipper.setDisplayedChild(2);
        } else if (viewFlipper.getDisplayedChild() == 2) {
            viewFlipper.setDisplayedChild(3);
        } else if (viewFlipper.getDisplayedChild() == 3) {
            if (!etxtFeedback.getText().toString().equals("")) {
                viewModel.setFeedback(etxtFeedback.getText().toString());
                performDriverRating();
            } else {
                Snackbar.make(coordinatorLayout, R.string.message_enter_a_feedback_for_the_trip, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }


    }

    public void fetchTripCompletionDetails() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("id", tripID);

        DataManager.fetchTripCompletionDetails(urlParams, new SuccessListener() {

            @Override
            public void onLoadCompleted(SuccessBean successBeanWS) {
//                populateUserInfo(successBean);

                successBean = successBeanWS;
                viewModel.setTime(successBean.getTime());
                viewModel.setDriverName(successBean.getDriverName());
                viewModel.setDriverPhoto(successBean.getDriverPhoto());
                viewModel.setFare(successBean.getFare());
                viewModel.setSource(successBean.getSource());
                viewModel.setSourceLatitude(successBean.getSourceLatitude());
                viewModel.setSourceLongitude(successBean.getSourceLongitude());
                viewModel.setDestination(successBean.getDestination());
                viewModel.setDestinationLatitude(successBean.getDestinationLatitude());
                viewModel.setDestinationLongitude(successBean.getDestinationLongitude());

                if (!isFinishing())
                    populateTripDetails();
            }

            @Override
            public void onLoadFailed(String errorMsg) {


            }
        });
    }

    public void performDriverRating() {

        JSONObject postData = getFeedbackJSObj();

        DataManager.performDriverRating(postData, new DriverRatingListener() {

            @Override
            public void onLoadCompleted(DriverRatingBean driverRatingBean) {

                swipeView.setRefreshing(false);
                startActivity(new Intent(TripFeedbackActivity.this, SplashActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getFeedbackJSObj() {
        JSONObject postData = new JSONObject();
        JSONArray badFeedbackArray = new JSONArray();
        JSONArray goodFeedbackArray = new JSONArray();

        try {
            postData.put("id", tripID);
            postData.put("rating", viewModel.getRating());

            for (String str1 : viewModel.getBadFeedbackList()) {
                badFeedbackArray.put(str1);
            }
            postData.put("bad_feedback", badFeedbackArray);

            for (String str2 : viewModel.getGoodFeedbackList()) {
                goodFeedbackArray.put(str2);
            }
            postData.put("good_feedback", goodFeedbackArray);
            postData.put("driver_feedback", viewModel.getFeedback());

            Log.i(TAG, "getFeedbackJSObj: Rating" + viewModel.getRating());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        fetchPolyPoint();

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_marker)));

            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker)));

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    public void mapAutoZoom() {

        LatLng newLatLng1 = successBean.getSourceLatLng();
        LatLng newLatLng2 = successBean.getDestinationLatLng();


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 0));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        Log.i(TAG, "mapAutoZoom: " + bounds.getCenter());

    }

    public void fetchPolyPoint() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", viewModel.getSourceLatitude() + "," + viewModel.getSourceLongitude());
        urlParams.put("destination", viewModel.getDestinationLatitude() + "," + viewModel.getDestinationLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", "AIzaSyBXZv9SRxxLKwEacQiYAe_YtvOju1ef8og");

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
//                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;
                populatePath();

            }

            @Override
            public void onLoadFailed(String error) {
//                swipeView.setRefreshing(false);
                /*Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Dismiss", snackBarDismissOnClickListener).show();*/
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
            polyLineOptions.color((ContextCompat.getColor(getApplicationContext(), R.color.map_path)));

        }

        polyLine = mMap.addPolyline(polyLineOptions);
    }
}

