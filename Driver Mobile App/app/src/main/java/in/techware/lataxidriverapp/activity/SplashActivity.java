package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.dialogs.PopupMessage;
import in.techware.lataxidriverapp.listeners.AppStatusListener;
import in.techware.lataxidriverapp.model.AppStatusBean;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.net.parsers.RequestParser;
import in.techware.lataxidriverapp.util.AppConstants;

public class SplashActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "Splash";
    private String requestID = "";
    private View.OnClickListener snackBarRefreshOnClickListener;
    private AppStatusBean appStatusBean;
    private PopupMessage popupMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        App.getInstance().setDemo(false);
        swipeView.setPadding(0, 0, 0, 0);
        getSupportActionBar().hide();
        lytBase.setFitsSystemWindows(false);


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Log.i(TAG, "onCreate: Key : " + key + " Value: " + getIntent().getExtras().get(key));
            }
        }
        if (getIntent().hasExtra("response")) {
            String body = getIntent().getStringExtra("response");
            RequestParser requestParser = new RequestParser();
            BasicBean basicBean = requestParser.parseBasicResponse(body);

            if (basicBean != null) {
                if (basicBean.getStatus().equalsIgnoreCase("Success")) {
//                    initiateDriverRatingService(basicBean.getId());
                    requestID = basicBean.getRequestID();
                }
            }
        }

        initViews();

        new Handler().postDelayed(checkAppStatusTask, 500);
//        new Handler().postDelayed(splashTask, 1000);

//        getData(false);

    }

    private void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(false, false);
                getData(true);
            }
        };
    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchAppStatus();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    Runnable splashTask = new Runnable() {
        @Override
        public void run() {

//                Log.i(TAG, "PeriodicTask: " + Config.getInstance().isFirstTime());
/*            if(App.getInstance().isDemo()){
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                startActivity(new Intent(SplashActivity.this, TripDetailsActivity.class));
                finish();
            }else{
                navigate();
            }*/
//            getData(false);

            navigate();
//            new Handler().postDelayed(splashTask, 2000);
        }
    };

    Runnable checkAppStatusTask = new Runnable() {
        @Override
        public void run() {

            if (App.checkForToken() && fop.checkSPHash()
                    && Config.getInstance().isPhoneVerified()
                    && requestID != null
                    && requestID.equalsIgnoreCase("")) {
                getData(false);
            } else {
                new Handler().postDelayed(splashTask, 2000);
            }
        }
    };

    private void navigate() {

        if (App.checkForToken() && fop.checkSPHash()) {
            if (Config.getInstance().isPhoneVerified()) {
//                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));

                if (requestID.equalsIgnoreCase("")) {

                    if (appStatusBean.getAppStatus() == AppConstants.APP_STATUS_IDLE
                            || appStatusBean.getTripStatus().equalsIgnoreCase(String.valueOf(AppConstants.TRIP_STATUS_CANCELLED))
                            || appStatusBean.getTripStatus().equalsIgnoreCase(String.valueOf(AppConstants.TRIP_STATUS_COMPLETED))
                            || appStatusBean.getTripStatus().equalsIgnoreCase(String.valueOf(AppConstants.TRIP_STATUS_PENDING))) {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                        startActivity(new Intent(SplashActivity.this, DriverDocumentsActivity.class));
                    } else {
                        TripBean tripBean = new TripBean();
                        tripBean.setId(appStatusBean.getId());
                        tripBean.setTripStatus(appStatusBean.getTripStatus());
                        tripBean.setDriverID(appStatusBean.getDriverID());
                        tripBean.setDriverName(appStatusBean.getDriverName());
                        tripBean.setDriverPhoto(appStatusBean.getDriverPhoto());
                        tripBean.setDriverStatus(appStatusBean.getDriverStatus());
                        tripBean.setCustomerID(appStatusBean.getCustomerID());
                        tripBean.setCustomerName(appStatusBean.getCustomerName());
                        tripBean.setCustomerPhoto(appStatusBean.getCustomerPhoto());
                        tripBean.setSourceLocation(appStatusBean.getSourceLocation());
                        tripBean.setSourceLatitude(appStatusBean.getSourceLatitude());
                        tripBean.setSourceLongitude(appStatusBean.getSourceLongitude());
                        tripBean.setDestinationLocation(appStatusBean.getDestinationLocation());
                        tripBean.setDestinationLatitude(appStatusBean.getDestinationLatitude());
                        tripBean.setDestinationLongitude(appStatusBean.getDestinationLongitude());

                        Log.i(TAG, "navigate: TripBean : " + new Gson().toJson(tripBean));
                        startActivity(new Intent(this, OnTripActivity.class)
                                .putExtra("bean", tripBean));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, RequestConfirmationActivity.class)
                            .putExtra("request_id", requestID));
//                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                }
                finish();
            } else {
                startActivity(new Intent(SplashActivity.this, OtpVerificationActivity.class));
                finish();
            }
        } else {
            App.logout();
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }
    }

    private void fetchAppStatus() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchAppStatus(urlParams, new AppStatusListener() {
            @Override
            public void onLoadCompleted(AppStatusBean appStatusBeanWS) {
                appStatusBean = appStatusBeanWS;
                new Handler().postDelayed(splashTask, 2000);
            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!isDestroyed() && !isFinishing()) {
                        showErrorPopup(error);
                    }
                } else {
                    if (!isFinishing()) {
                        showErrorPopup(error);
                    }
                }
            }

        });

    }

    private void showErrorPopup(String error) {
        if (popupMessage == null)
            popupMessage = new PopupMessage(SplashActivity.this);
        popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
            @Override
            public void actionCompletedSuccessfully(boolean result) {
                getData(false);
            }

            @Override
            public void actionFailed() {
                Toast.makeText(SplashActivity.this, R.string.message_thank_you, Toast.LENGTH_SHORT).show();
                App.logout();
                finish();
            }
        });
        popupMessage.show(error, 0, getString(R.string.btn_retry), getString(R.string.btn_cancel));
    }
}
