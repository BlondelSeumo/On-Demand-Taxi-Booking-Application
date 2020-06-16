package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.listeners.TripSummaryListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.model.TripSummaryBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class TripSummaryActivity extends BaseAppCompatNoDrawerActivity {

    private TextView txtRiderPays;
    private TextView txtBaseFare;
    private TextView txtLaTaxiFare;
    private TextView txtTolls;
    private TextView txtRidersDiscount;
    private TextView txtTax;
    private TripSummaryBean tripSummaryBean;
    private String TAG = " ";
    private View.OnClickListener snackBarRefreshOnClickListener;
    private ViewFlipper viewFilpper;
    private String tripID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        if (getIntent().hasExtra("trip_id"))
            tripID = getIntent().getStringExtra("trip_id");
        else {
            Toast.makeText(this, R.string.message_something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tripSummaryBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }

    private void getData(boolean isSwipeRefreshing) {

//        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchTripSummary();
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

        txtRiderPays = (TextView) findViewById(R.id.txt_trip_summary_rider_pays);

        txtBaseFare = (TextView) findViewById(R.id.txt_trip_summary_base_fare);
        txtLaTaxiFare = (TextView) findViewById(R.id.txt_trip_summary_la_taxi_fare);
        txtTax = (TextView) findViewById(R.id.txt_trip_summary_tax);
        txtTolls = (TextView) findViewById(R.id.txt_trip_summary_tolls_fare);
        txtRidersDiscount = (TextView) findViewById(R.id.txt_trip_summary_riders_discount);

        viewFilpper = (ViewFlipper) findViewById(R.id.viewflipper_trip_summary);

        viewFilpper.setDisplayedChild(0);

    }

    public void fetchTripSummary() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("trip_id", tripID);

        DataManager.fetchTripSummary(urlParams, new TripSummaryListener() {

            @Override
            public void onLoadCompleted(TripSummaryBean tripSummaryBeanWS) {

                Log.i(TAG, "populateTripSummary: RiderPays " + tripSummaryBeanWS.getTotalFare());
                Log.i(TAG, "populateTripSummary: BastFare " + tripSummaryBeanWS.getBaseFare());
                Log.i(TAG, "populateTripSummary: TollsFare " + tripSummaryBeanWS.getLaTaxiFee());
                Log.i(TAG, "populateTripSummary: LaTaxiFare " + tripSummaryBeanWS.getToll());
                Log.i(TAG, "populateTripSummary: RidersDiscount " + tripSummaryBeanWS.getDiscount());

                tripSummaryBean = tripSummaryBeanWS;
                populateTripSummary();
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

    private void populateTripSummary() {

        txtRiderPays.setText(tripSummaryBean.getTotalFare());
        txtBaseFare.setText(tripSummaryBean.getBaseFare());
        txtLaTaxiFare.setText(tripSummaryBean.getLaTaxiFee());
        txtTax.setText(tripSummaryBean.getTax());
        txtTolls.setText(tripSummaryBean.getToll());
        txtRidersDiscount.setText(tripSummaryBean.getDiscount());

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    public void onTripSummaryCollectCashClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performCashCollection();
        }else{
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    private void performCashCollection() {

        JSONObject postData = getCashCollectionJSObj();

        DataManager.performCashCollection(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {

                Toast.makeText(TripSummaryActivity.this, R.string.message_trip_completed_successfully, Toast.LENGTH_SHORT).show();
                viewFilpper.showNext();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });

    }

    private JSONObject getCashCollectionJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_id", tripID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //            onBackPressed();
            if (viewFilpper.getDisplayedChild() == 1) {
                startActivity(new Intent(this, SplashActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }


}
