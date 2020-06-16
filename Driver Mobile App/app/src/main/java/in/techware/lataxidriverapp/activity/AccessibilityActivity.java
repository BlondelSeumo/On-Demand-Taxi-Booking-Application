package in.techware.lataxidriverapp.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.AccessibilityListener;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.model.AccessibilityBean;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class AccessibilityActivity extends BaseAppCompatNoDrawerActivity {

    private CheckBox cbAccessibilityDeaf;
    private CheckBox cbAccessibilityVision;
    private boolean isDeaf;
    private boolean isFlashRequiredForRequests;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private AccessibilityBean accessibilityBean;
    private boolean isInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        getSupportActionBar().setTitle(R.string.label_accessibility);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accessibilityBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }

    private void getData(boolean isSwipeRefreshing) {

        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchDriverAccessibility();
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


        cbAccessibilityDeaf = (CheckBox) findViewById(R.id.cb_accessibility_one);
        cbAccessibilityVision = (CheckBox) findViewById(R.id.cb_accessibility_two);

        cbAccessibilityDeaf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isInit)
                    performDriverAccessibility();
            }
        });


        cbAccessibilityVision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isInit)
                    performDriverAccessibility();
            }
        });

    }

    public void fetchDriverAccessibility() {

//        swipeView.setRefreshing(true);

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchDriverAccessibility(urlParams, new AccessibilityListener() {

            @Override
            public void onLoadCompleted(AccessibilityBean accessibilityBeanWS) {

                accessibilityBean = accessibilityBeanWS;
                cbAccessibilityDeaf.setChecked(accessibilityBeanWS.isDeaf());
                cbAccessibilityVision.setChecked(accessibilityBeanWS.isFlashRequired());

                isInit = false;

                setProgressScreenVisibility(false, false);
                swipeView.setRefreshing(false);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                setProgressScreenVisibility(true, false);
                swipeView.setRefreshing(false);

            }
        });
    }

    public void performDriverAccessibility() {

        swipeView.setRefreshing(true);

        JSONObject postData = getDriverAccessibilityJSObj();

        DataManager.performDriverAccessibility(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, R.string.message_accessibility_settings_updated, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getDriverAccessibilityJSObj() {

        JSONObject postData = new JSONObject();

        try {

            postData.put("is_deaf", cbAccessibilityDeaf.isChecked());
            postData.put("is_flash_required_for_requests", cbAccessibilityVision.isChecked());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;

    }

    public void onAccessibilityDeafClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        performDriverAccessibility();
    }

    public void onAccessibilityVisionClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        performDriverAccessibility();
    }
}
