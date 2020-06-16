
package in.techware.lataxicustomer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.listeners.PolyPointsListener;
import in.techware.lataxicustomer.listeners.TripDetailsListener;
import in.techware.lataxicustomer.model.PolyPointsBean;
import in.techware.lataxicustomer.model.TripBean;
import in.techware.lataxicustomer.model.TripDetailsBean;
import in.techware.lataxicustomer.net.DataManager;

public class TripDetailsActivity extends BaseAppCompatNoDrawerActivity {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleMapOptions googleMapOptions;
    private Toolbar toolbarTrips;
    private TextView txtDateTime;
    private TripBean tripBean;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtDriverName;
    private TextView txtBaseFare;
    private TextView txtKilometerFare;
    private TextView txtMinuteFare;
    private TextView txtSubTotalFare;
    private TextView txtPromotionFare;
    private TextView txtTotalFare;
    private TextView txtKilometer;
    private TextView txtMinutes;
    private RatingBar ratingTrip;
    private ImageView ivDriverPhoto;
    private LinearLayout llFare;
    private FrameLayout flTrips;
    private LinearLayout llDriverInfo;
    private TripDetailsBean tripDetailsBean;
    private double sourceLatitude;
    private double sourceLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private Polyline line;
    private String Id;
    private GoogleMap googleMap;
    private LatLng newLatLng1;
    private LatLng newLatLng2;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private String TAG;
    private LinearLayout llBottomSheet;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private ViewGroup.LayoutParams param;
    private TextView txtRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        tripBean = (TripBean) getIntent().getSerializableExtra("bean");

        initViews();

//        getSupportActionBar().setTitle(str);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

//        swipeView.setRefreshing(true);
        setProgressScreenVisibility(true, true);

    }

    public void initViews() {

        fetchTripDetails();

        coordinatorLayout.removeView(toolbar);

        toolbarTrips = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_trips, toolbar);
        coordinatorLayout.addView(toolbarTrips, 0);
        setSupportActionBar(toolbarTrips);

        llFare = (LinearLayout) findViewById(R.id.ll_trip_details_fare);
        flTrips = (FrameLayout) findViewById(R.id.fl_trips);
        llDriverInfo = (LinearLayout) findViewById(R.id.ll_trip_details_driver_info);

        txtDate = (TextView) findViewById(R.id.txt_date_trip_details);
        txtTime = (TextView) findViewById(R.id.txt_time_trip_details);
        txtDriverName = (TextView) findViewById(R.id.txt_driver_name_trip_details);
        ratingTrip = (RatingBar) findViewById(R.id.rating_bottom_sheet_trip_details);
        txtBaseFare = (TextView) findViewById(R.id.txt_base_rate_trip_details);
        txtKilometerFare = (TextView) findViewById(R.id.txt_kilometer_rate_trip_details);
        txtMinuteFare = (TextView) findViewById(R.id.txt_minute_rate_trip_details);
        txtSubTotalFare = (TextView) findViewById(R.id.txt_subtotal_rate_trip_details);
        txtPromotionFare = (TextView) findViewById(R.id.txt_promotion_rate_trip_details);
        txtTotalFare = (TextView) findViewById(R.id.txt_total_rate_trip_details);
        txtKilometer = (TextView) findViewById(R.id.txt_kilometer);
        txtMinutes = (TextView) findViewById(R.id.txt_minute);
        txtRating = (TextView) findViewById(R.id.txt_rating);

        ivDriverPhoto = (ImageView) findViewById(R.id.iv_driver_photo_trip_details);

        llBottomSheet = (LinearLayout) findViewById(R.id.ll_trip_details_bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
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
                param = mapFragment.getView().getLayoutParams();
                param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - (80 * px * (1 - slideOffset)) - bottomSheet.getHeight() * (slideOffset));
                Log.i(TAG, "onSlide: PAram Height : " + param.height);
                mapFragment.getView().setLayoutParams(param);
            }
        });


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_trip_details_map);
        param = mapFragment.getView().getLayoutParams();
        param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - (80 * px));
        Log.i(TAG, "onSlide: Param Height : " + param.height);
        mapFragment.getView().setLayoutParams(param);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0, (int) (50 * px), 0, (int) (60 * px));
                mMap.getUiSettings().setMapToolbarEnabled(false);
                if (ActivityCompat.checkSelfPermission
                        (TripDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (TripDetailsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                }
            }
        });
    }


    public void fetchTripDetails() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("id", tripBean.getId());

        DataManager.fetchTripDetails(urlParams, new TripDetailsListener() {
            @Override
            public void onLoadCompleted(TripDetailsBean tripDetailsBeanWS) {
                System.out.println("Successfull : TripDetailsBean : " + tripDetailsBeanWS);
                tripDetailsBean = tripDetailsBeanWS;
                populateTripDetails(tripDetailsBeanWS);

            }

            @Override
            public void onLoadFailed(String errorMsg) {
                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }

    private void ratingCheck() {

        if (tripDetailsBean.getRating() == 0) {
            txtRating.setVisibility(View.VISIBLE);
            ratingTrip.setVisibility(View.GONE);
        } else {
            txtRating.setVisibility(View.GONE);
            ratingTrip.setVisibility(View.VISIBLE);
        }
    }

    private void populateTripDetails(TripDetailsBean tripDetailsBean) {

        ratingCheck();

        if (!tripDetailsBean.getTripStatus().equalsIgnoreCase("3")) {
            llFare.setVisibility(View.GONE);
        } else {
            llFare.setVisibility(View.VISIBLE);
        }

        populateMap();

        if (tripDetailsBean != null) {
            txtDate.setText(App.getUserDateFromUnix(String.valueOf(tripDetailsBean.getTime())));
            txtTime.setText(App.getUserTimeFromUnix(String.valueOf(tripDetailsBean.getTime())));
//            txtKilometer.setText(tripDetailsBean.getKilometer());
//            txtMinutes.setText(tripDetailsBean.getMinute());
            txtBaseFare.setText(tripDetailsBean.getBaseFare());
            txtKilometerFare.setText(tripDetailsBean.getKilometerFare());
            txtMinuteFare.setText(tripDetailsBean.getMinutesFare());
            txtDriverName.setText(tripDetailsBean.getDriverName());
            txtSubTotalFare.setText(tripDetailsBean.getSubTotalFare());
            txtPromotionFare.setText(tripDetailsBean.getPromotionFare());
            txtTotalFare.setText(tripDetailsBean.getTotalFare());

            ratingTrip.setRating(tripDetailsBean.getRating());

            Glide.with(getApplicationContext())
                    .load(tripDetailsBean.getDriverPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_dummy_photo)
                            .fallback(R.drawable.ic_dummy_photo)
                            .centerCrop()
                            .circleCrop())
                    .into(ivDriverPhoto);
        }


        setProgressScreenVisibility(false, false);
        swipeView.setRefreshing(false);

    }

    private void populateMap() {
        mMap.clear();
        onPlotLatLng(tripDetailsBean.getDSourceLatitude(), tripDetailsBean.getDSourceLongitude(),
                tripDetailsBean.getDDestinationLatitude(), tripDetailsBean.getDDestinationLongitude());
        mapAutoZoom();
    }

    public void onLayoutClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        fetchPolyPoint();

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));

//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_marker)));


            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void onHelpButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent intent = new Intent(TripDetailsActivity.this, HelpPageActivity.class);
        intent.putExtra("bean", tripDetailsBean);
        startActivity(intent);

    }

    public void mapAutoZoom() {


        newLatLng1 = tripDetailsBean.getSourceLatLng();
        newLatLng2 = tripDetailsBean.getDestinationLatLng();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
        LatLngBounds bounds = builder.build();

        if (mMap != null && mapFragment.getView() != null && mapFragment.getView().getHeight() > 0) {
            if (mapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }

    }

    public void fetchPolyPoint() {

        sourceLatitude = tripDetailsBean.getDSourceLatitude();
        sourceLongitude = tripDetailsBean.getDSourceLongitude();
        destinationLatitude = tripDetailsBean.getDDestinationLatitude();
        destinationLongitude = tripDetailsBean.getDDestinationLongitude();

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", sourceLatitude + "," + sourceLongitude);
        urlParams.put("destination", destinationLatitude + "," + destinationLongitude);
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));

        DataManager.fetchPolyPoints(urlParams, new PolyPointsListener() {

            @Override
            public void onLoadCompleted(PolyPointsBean polyPointsBeanWS) {
                swipeView.setRefreshing(false);

                polyPointsBean = polyPointsBeanWS;
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
}