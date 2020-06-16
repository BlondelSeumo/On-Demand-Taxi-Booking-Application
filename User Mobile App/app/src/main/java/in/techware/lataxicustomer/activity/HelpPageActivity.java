package in.techware.lataxicustomer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
import in.techware.lataxicustomer.listeners.PolyPointsListener;
import in.techware.lataxicustomer.listeners.TripFeedbackListener;
import in.techware.lataxicustomer.model.PolyPointsBean;
import in.techware.lataxicustomer.model.TripDetailsBean;
import in.techware.lataxicustomer.model.TripFeedbackBean;
import in.techware.lataxicustomer.net.DataManager;
import in.techware.lataxicustomer.util.AppConstants;

public class HelpPageActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "HelpA";
    private Toolbar toolbarTrips;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtDriverName;
    private TextView txtTotalFare;
    private RatingBar ratingTrip;
    private ImageView ivDriverPhoto;
    private TripDetailsBean bean;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FragmentActivity activity;
    private String MyLat;
    private String MyLong;
    private TextView txtSource;
    private TextView txtDestination;
    private LatLng newLatLng1;
    private LatLng newLatLng2;
    private LinearLayout llFeedBackOne;
    private LinearLayout llFeedBackTwo;
    private LinearLayout llFeedBackThree;
    private LinearLayout llFeedBackFour;
    private LinearLayout llFeedBackFive;
    private LinearLayout llFeedBackSix;
    private ImageView ivFeedback1Select;
    private ImageView ivFeedback2Select;
    private ImageView ivFeedback3Select;
    private ImageView ivFeedback4Select;
    private ImageView ivFeedback5Select;
    private ImageView ivFeedback6Select;
    private String tripFeedbackType;
    private double sourceLatitude;
    private double sourceLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private PolyPointsBean polyPointsBean;
    private Polyline polyLine;
    private TextView txtReceipt;
    private TextView txtCancelled;
    private TextView txtCash;
    private TextView txtAmount;
    private TextView txtRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        bean = (TripDetailsBean) getIntent().getSerializableExtra("bean");

        initViews();

        swipeView.setRefreshing(true);
        setProgressScreenVisibility(true, true);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

//        swipeView.setRefreshing(true);
    }


    public void initViews() {

        coordinatorLayout.removeView(toolbar);

        toolbarTrips = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_trips, toolbar);
        coordinatorLayout.addView(toolbarTrips, 0);
        setSupportActionBar(toolbarTrips);

        txtDate = (TextView) toolbarTrips.findViewById(R.id.txt_date_trip_details);
        txtTime = (TextView) toolbarTrips.findViewById(R.id.txt_time_trip_details);
        txtDriverName = (TextView) findViewById(R.id.txt_help_page_activity_driver_name);
        txtTotalFare = (TextView) findViewById(R.id.txt_help_page_activity_total_fare);
        txtSource = (TextView) findViewById(R.id.txt_help_page_activity_source);
        txtDestination = (TextView) findViewById(R.id.txt_help_page_activity_destination);
        txtReceipt = (TextView) findViewById(R.id.txt_receipt);
        txtCancelled = (TextView) findViewById(R.id.txt_cancelled_help_page);
        txtCash = (TextView) findViewById(R.id.txt_cash_help_page);

        llFeedBackOne = (LinearLayout) findViewById(R.id.ll_feedback1);
        llFeedBackTwo = (LinearLayout) findViewById(R.id.ll_feedback2);
        llFeedBackThree = (LinearLayout) findViewById(R.id.ll_feedback3);
        llFeedBackFour = (LinearLayout) findViewById(R.id.ll_feedback4);
        llFeedBackFive = (LinearLayout) findViewById(R.id.ll_feedback5);
        llFeedBackSix = (LinearLayout) findViewById(R.id.ll_feedback6);

        ivFeedback1Select = (ImageView) findViewById(R.id.iv_feedback1_select);
        ivFeedback2Select = (ImageView) findViewById(R.id.iv_feedback2_select);
        ivFeedback3Select = (ImageView) findViewById(R.id.iv_feedback3_select);
        ivFeedback4Select = (ImageView) findViewById(R.id.iv_feedback4_select);
        ivFeedback5Select = (ImageView) findViewById(R.id.iv_feedback5_select);
        ivFeedback6Select = (ImageView) findViewById(R.id.iv_feedback6_select);

        ratingTrip = (RatingBar) findViewById(R.id.rb_help_page_activity_rating);

        txtRating = (TextView) findViewById(R.id.txt_rating_help_page);

        ivDriverPhoto = (ImageView) findViewById(R.id.iv_help_page_activity_driver_photo);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_help_page_map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0, 0, 0, (int) (50 * px));

                if (ActivityCompat.checkSelfPermission
                        (HelpPageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (HelpPageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                }
                populateDetails();

            }
        });

    }

    private void ratingCheck() {

        if (bean.getRating() == 0) {
            txtRating.setVisibility(View.VISIBLE);
            ratingTrip.setVisibility(View.GONE);
        } else {
            txtRating.setVisibility(View.GONE);
            ratingTrip.setVisibility(View.VISIBLE);
        }
    }

    public void populateDetails() {

        ratingCheck();

        Log.i(TAG, "populateDetails: " + bean);

        if (bean.getTripStatus().equalsIgnoreCase("3")) {
            txtCancelled.setVisibility(View.INVISIBLE);
            txtReceipt.setVisibility(View.VISIBLE);
            txtCash.setVisibility(View.VISIBLE);
            txtTotalFare.setVisibility(View.VISIBLE);
//            txtRating.setVisibility(View.GONE);
        } else {
            txtCancelled.setVisibility(View.VISIBLE);
            txtReceipt.setVisibility(View.INVISIBLE);
            txtCash.setVisibility(View.INVISIBLE);
            txtTotalFare.setVisibility(View.INVISIBLE);
//            txtRating.setVisibility(View.INVISIBLE);
        }


        onPlotLatLng(Double.parseDouble(bean.getSourceLatitude()), Double.parseDouble(bean.getSourceLongitude()), Double.parseDouble(bean.getDestinationLatitude()), Double.parseDouble(bean.getDestinationLongitude()));

        txtDriverName.setText(bean.getDriverName());
        txtDate.setText(App.getUserDateFromUnix(String.valueOf(bean.getTime())));
        txtTime.setText(App.getUserTimeFromUnix(String.valueOf(bean.getTime())));
        txtTotalFare.setText(bean.getTotalFare());

        txtSource.setText(bean.getSourceName());
        txtDestination.setText(bean.getDestinationName());

        ratingTrip.setRating(bean.getRating());

        Glide.with(getApplicationContext())
                .load(bean.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_dummy_photo)
                        .fallback(R.drawable.ic_dummy_photo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .circleCrop())
                .into(ivDriverPhoto);

        mapAutoZoom();

        setProgressScreenVisibility(false, false);
        swipeView.setRefreshing(false);
    }

    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        fetchPolyPoints();

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));

//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_marker)));

            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker)));

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    public void mapAutoZoom() {

        newLatLng1 = bean.getSourceLatLng();
        newLatLng2 = bean.getDestinationLatLng();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (20 * px)));
        if (mMap != null && mapFragment.getView() != null && mapFragment.getView().getHeight() > 0) {
            if (mapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (20 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }

    }

    public void onReceiptClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent intent = new Intent(HelpPageActivity.this, ReceiptPageActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);

    }


    public void onFeedBackClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);


        changeFeedbackToDefault();
        String type = (String) view.getTag();

        switch (type) {
            case "0":
                tripFeedbackType = AppConstants.FEEDBACK1;
                llFeedBackOne.setBackgroundResource(R.color.bg_feedback);
                ivFeedback1Select.setVisibility(View.VISIBLE);
                break;
            case "1":
                tripFeedbackType = AppConstants.FEEDBACK2;
                llFeedBackTwo.setBackgroundResource(R.color.bg_feedback);
                ivFeedback2Select.setVisibility(View.VISIBLE);
                break;
            case "2":
                tripFeedbackType = AppConstants.FEEDBACK3;
                llFeedBackThree.setBackgroundResource(R.color.bg_feedback);
                ivFeedback3Select.setVisibility(View.VISIBLE);
                break;
            case "3":
                tripFeedbackType = AppConstants.FEEDBACK4;
                llFeedBackFour.setBackgroundResource(R.color.bg_feedback);
                ivFeedback4Select.setVisibility(View.VISIBLE);
                break;
            case "4":
                tripFeedbackType = AppConstants.FEEDBACK5;
                llFeedBackFive.setBackgroundResource(R.color.bg_feedback);
                ivFeedback5Select.setVisibility(View.VISIBLE);
                break;
            case "5":
                tripFeedbackType = AppConstants.FEEDBACK6;
                llFeedBackSix.setBackgroundResource(R.color.bg_feedback);
                ivFeedback6Select.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }


    private void changeFeedbackToDefault() {

        llFeedBackSix.setBackgroundResource(R.color.transparent);
        llFeedBackOne.setBackgroundResource(R.color.transparent);
        llFeedBackTwo.setBackgroundResource(R.color.transparent);
        llFeedBackThree.setBackgroundResource(R.color.transparent);
        llFeedBackFour.setBackgroundResource(R.color.transparent);
        llFeedBackFive.setBackgroundResource(R.color.transparent);

        ivFeedback6Select.setVisibility(View.GONE);
        ivFeedback1Select.setVisibility(View.GONE);
        ivFeedback2Select.setVisibility(View.GONE);
        ivFeedback3Select.setVisibility(View.GONE);
        ivFeedback4Select.setVisibility(View.GONE);
        ivFeedback5Select.setVisibility(View.GONE);
    }

    public void performTripFeedback() {

        swipeView.setRefreshing(true);
        JSONObject postData = getTripFeedbackJSObj();

        DataManager.performTripFeedback(postData, new TripFeedbackListener() {

            @Override
            public void onLoadCompleted(TripFeedbackBean tripFeedbackBean) {

                Toast.makeText(getApplicationContext(), R.string.message_feedback_is_successfully_sent,
                        Toast.LENGTH_LONG).show();
                swipeView.setRefreshing(false);

                finish();
            }

            @Override
            public void onLoadFailed(String error) {

                Toast.makeText(getApplicationContext(), R.string.message_feedback_is_not_sent,
                        Toast.LENGTH_LONG).show();
                swipeView.setRefreshing(false);

                finish();

            }
        });
    }

    private JSONObject getTripFeedbackJSObj() {

        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_feedback_type", tripFeedbackType);
            postData.put("trip_id", bean.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onHelpPageFeedbackSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (tripFeedbackType != null) {
            performTripFeedback();
        } else {
            Snackbar.make(coordinatorLayout, R.string.message_select_a_feedback, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    public void fetchPolyPoints() {

        sourceLatitude = Double.parseDouble(bean.getSourceLatitude());
        sourceLongitude = Double.parseDouble(bean.getSourceLongitude());
        destinationLatitude = Double.parseDouble(bean.getDestinationLatitude());
        destinationLongitude = Double.parseDouble(bean.getDestinationLongitude());

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