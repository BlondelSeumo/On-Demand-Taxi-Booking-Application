package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.SeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class SettingsActivity extends BaseAppCompatNoDrawerActivity {

    private SwitchCompat switchOnline;
    private boolean isOnline;
    private SeekBar seekbarVolume;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle(R.string.label_settings);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);




        initViews();



        if (mSharedPrefs != null) {
            int mProgress = mSharedPrefs.getInt("mMySeekBarProgress", 0);
            seekbarVolume.setProgress(mProgress);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor mEditor = mSharedPrefs.edit();
        int mProgress = seekbarVolume.getProgress();
        mEditor.putInt("mMySeekBarProgress", mProgress).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSharedPrefs != null) {
            int mProgress = mSharedPrefs.getInt("mMySeekBarProgress", 0);
            seekbarVolume.setProgress(mProgress);
        }
    }

    private void initViews() {

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        seekbarVolume = (SeekBar) findViewById(R.id.seekbar_settings_volume);

        switchOnline = (SwitchCompat) findViewById(R.id.settings_online_offline_switch);

        if (Config.getInstance().isOnline()) {
            switchOnline.setChecked(true);
        } else {
            switchOnline.setChecked(false);
        }

        switchOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                setDriverTitle();
                if (App.isNetworkAvailable()) {
                    performDriverStatusChange();
                } else {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }
        });
    }

    public void onSettingsAccessibilityClick(View view) {

        Intent intent = new Intent(SettingsActivity.this, AccessibilityActivity.class);
        startActivity(intent);

    }

    public void onSettingsContactClick(View view) {

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
    }

    private void performDriverStatusChange() {

        swipeView.setRefreshing(true);
        JSONObject postData = getDriverStatusChangeJSObj();

        DataManager.performDriverStatusChange(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                setDriverTitle();
                if (switchOnline.isChecked()) {
                    Snackbar.make(coordinatorLayout, R.string.message_you_are_online, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                } else {
                    Snackbar.make(coordinatorLayout, R.string.message_you_are_offline_now, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                switchOnline.setChecked(!switchOnline.isChecked());
                setDriverTitle();
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                if (App.getInstance().isDemo()) {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    setDriverTitle();

                }
            }
        });

    }

    private JSONObject getDriverStatusChangeJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("driver_status", switchOnline.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void setDriverTitle() {

        if (switchOnline != null) {
            if (switchOnline.isChecked()) {
                Config.getInstance().setOnline(true);
            } else {
                Config.getInstance().setOnline(false);
            }
        }
    }
}
