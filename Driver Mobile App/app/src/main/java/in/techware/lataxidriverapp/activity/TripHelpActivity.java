package in.techware.lataxidriverapp.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.PolyPointListener;
import in.techware.lataxidriverapp.listeners.TripFeedbackListener;
import in.techware.lataxidriverapp.model.PolyPointBean;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.model.TripFeedbackBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class TripHelpActivity extends BaseAppCompatNoDrawerActivity {

    private TripBean tripBean;
    private FragmentManager myFragmentManager;
    private SupportMapFragment myMapFragment;
    private ViewGroup.LayoutParams param;
    private GoogleMap mMap;
    private String TAG = "";
    private ImageView ivUserPhoto;
    private RatingBar customerRating;
    private TextView txtCustomerName;
    private TextView txtAmount;
    private TextView txtSource;
    private TextView txtDestination;
    private Button btnSubmit;
    private TextView txtRating;
    private TextView txtDate;
    private TextView txtTime;
    private Toolbar toolbarHelp;
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
    private PolyPointBean polyPointBean;
    private Polyline polyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_help);

        if (getIntent().hasExtra("bean")) {
            tripBean = (TripBean) getIntent().getSerializableExtra("bean");
        }

        initViews();
        initMap();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initMap() {

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
                if (Config.getInstance().getCurrentLatitude() != null
                        && !Config.getInstance().getCurrentLatitude().equals("")
                        && Config.getInstance().getCurrentLongitude() != null
                        && !Config.getInstance().getCurrentLongitude().equals("")) {
                    //	fetchMap();
                }
            }
        });
    }

    private void initViews() {

        coordinatorLayout.removeView(toolbar);
        toolbarHelp = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_trip_details, toolbar);
        coordinatorLayout.addView(toolbarHelp, 0);
        setSupportActionBar(toolbarHelp);

        txtDate = (TextView) toolbarHelp.findViewById(R.id.txt_toolbar_trip_details_date);
        txtTime = (TextView) toolbarHelp.findViewById(R.id.txt_toolbar_trip_details_time);

        ivUserPhoto = (ImageView) findViewById(R.id.iv_trip_help_profile_photo);

        customerRating = (RatingBar) findViewById(R.id.rating_trip_help);

        txtRating = (TextView) findViewById(R.id.txt_trip_help_rating);

        txtCustomerName = (TextView) findViewById(R.id.txt_trip_help_customer_name);
        txtAmount = (TextView) findViewById(R.id.txt_trip_help_estimated_payout);
        txtSource = (TextView) findViewById(R.id.txt_trip_help_source_location);
        txtDestination = (TextView) findViewById(R.id.txt_trip_help_destination_location);

        llFeedBackOne = (LinearLayout) findViewById(R.id.ll_trip_help_feedback_1);
        llFeedBackTwo = (LinearLayout) findViewById(R.id.ll_trip_help_feedback_2);
        llFeedBackThree = (LinearLayout) findViewById(R.id.ll_trip_help_feedback_3);
        llFeedBackFour = (LinearLayout) findViewById(R.id.ll_trip_help_feedback_4);
        llFeedBackFive = (LinearLayout) findViewById(R.id.ll_trip_help_feedback_5);
        llFeedBackSix = (LinearLayout) findViewById(R.id.ll_trip_help_feedback_6);

        ivFeedback1Select = (ImageView) findViewById(R.id.iv_trip_help_feedback_1_select);
        ivFeedback2Select = (ImageView) findViewById(R.id.iv_trip_help_feedback_2_select);
        ivFeedback3Select = (ImageView) findViewById(R.id.iv_trip_help_feedback_3_select);
        ivFeedback4Select = (ImageView) findViewById(R.id.iv_trip_help_feedback_4_select);
        ivFeedback5Select = (ImageView) findViewById(R.id.iv_trip_help_feedback_5_select);
        ivFeedback6Select = (ImageView) findViewById(R.id.iv_trip_help_feedback_6_select);

        btnSubmit = (Button) findViewById(R.id.btn_trip_help_submit);

        btnSubmit.setTypeface(typeface);

        populateDetails();
    }

    private void ratingCheck() {

        if (tripBean.getRating() == 0) {
            txtRating.setVisibility(View.VISIBLE);
            customerRating.setVisibility(View.GONE);
        } else {
            txtRating.setVisibility(View.GONE);
            customerRating.setVisibility(View.VISIBLE);
        }
    }

    private void populateDetails() {

        ratingCheck();

        Log.i(TAG, "populateDetails: " + tripBean);

        onPlotLatLng(Double.parseDouble(tripBean.getSourceLatitude()), Double.parseDouble(tripBean.getSourceLongitude()),
                Double.parseDouble(tripBean.getDestinationLatitude()), Double.parseDouble(tripBean.getDestinationLongitude()));

        txtCustomerName.setText(tripBean.getDriverName());
        txtDate.setText(App.getUserDateFromUnix(String.valueOf(tripBean.getStartTime())));
        txtTime.setText(App.getUserTimeFromUnix(String.valueOf(tripBean.getStartTime())));
        txtAmount.setText(tripBean.getFare());

        txtSource.setText(tripBean.getSourceLocation());
        txtDestination.setText(tripBean.getDestinationLocation());

        customerRating.setRating(Float.parseFloat(String.valueOf(tripBean.getRating())));

        Glide.with(getApplicationContext())
                .load(tripBean.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .circleCrop())
                .into(ivUserPhoto);

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
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_source)));

            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_destination)));

        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
    }

    public void mapAutoZoom() {

        newLatLng1 = new LatLng(Double.parseDouble(tripBean.getSourceLatitude()), Double.parseDouble(tripBean.getSourceLongitude()));
        newLatLng2 = new LatLng(Double.parseDouble(tripBean.getDestinationLatitude()), Double.parseDouble(tripBean.getDestinationLongitude()));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(newLatLng1);
        builder.include(newLatLng2);
        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (20 * px)));
        if (mMap != null && myMapFragment.getView() != null && myMapFragment.getView().getHeight() > 0) {
            if (myMapFragment.getView().getHeight() > 150 * px)
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (20 * px)));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
        }
    }

    public void onTripHelpFeedBackClick(View view) {

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

                Toast.makeText(getApplicationContext(), R.string.message_your_feedback_is_recorded,
                        Toast.LENGTH_LONG).show();
                swipeView.setRefreshing(false);

                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                swipeView.setRefreshing(false);
            }
        });
    }

    private JSONObject getTripFeedbackJSObj() {

        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_feedback_type", tripFeedbackType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onTripHelpFeedbackSubmitClick(View view) {

        if (tripFeedbackType != null) {
            performTripFeedback();
        } else {
            Snackbar.make(coordinatorLayout, R.string.message_please_select_a_feedback, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    public void fetchPolyPoints() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", tripBean.getSourceLatitude() + "," + tripBean.getSourceLongitude());
        urlParams.put("destination", tripBean.getDestinationLatitude() + "," + tripBean.getDestinationLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.api_key));

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
}