package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class DriverLicenceTypeActivity extends BaseAppCompatNoDrawerActivity {

    private int driverType = AppConstants.DRIVER_TYPE_DRIVER_CUM_OWNER;
    private FrameLayout frameDriverCumOwner;
    private FrameLayout frameNonDrivingPartner;
    private ImageView ivTickDriverCumOwner;
    private ImageView ivTickNonDrivingPartner;
    private TextView txtDriverCumOwner;
    private TextView txtNonDrivingPartner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_licence_type);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        initViews();
    }

    private void initViews() {

        ivTickDriverCumOwner = (ImageView) findViewById(R.id.iv_driver_licence_type_driver_cum_owner);
        ivTickNonDrivingPartner = (ImageView) findViewById(R.id.iv_driver_licence_type_non_driving_partner);
        frameDriverCumOwner = (FrameLayout) findViewById(R.id.frame_driver_licence_type_driver_cum_owner);
        frameNonDrivingPartner = (FrameLayout) findViewById(R.id.frame_driver_licence_type_non_driving_partner);

        txtDriverCumOwner = (TextView) findViewById(R.id.txt_driver_licence_type_driver_cum_owner);
        txtNonDrivingPartner = (TextView) findViewById(R.id.txt_driver_licence_type_non_driving_partner);
    }

    public void onDriverLicenceTypeDriverCumOwnerClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        driverType = AppConstants.DRIVER_TYPE_DRIVER_CUM_OWNER;

        txtNonDrivingPartner.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_driver_Type));
        txtDriverCumOwner.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

        ivTickNonDrivingPartner.setVisibility(View.INVISIBLE);
        ivTickDriverCumOwner.setVisibility(View.VISIBLE);

        frameNonDrivingPartner.setBackgroundResource(R.color.transparent);
        frameDriverCumOwner.setBackgroundResource(R.color.bg_driver_Type);

    }

    public void onDriverLicenceTypeNonDrivingPartnerClick(View view) {

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        driverType = AppConstants.DRIVER_TYPE_NON_DRIVING_PARTNER;

        txtNonDrivingPartner.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        txtDriverCumOwner.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_driver_Type));

        ivTickNonDrivingPartner.setVisibility(View.VISIBLE);
        ivTickDriverCumOwner.setVisibility(View.INVISIBLE);

        frameNonDrivingPartner.setBackgroundResource(R.color.bg_driver_Type);
        frameDriverCumOwner.setBackgroundResource(R.color.transparent);

    }

    public void onDriverLicenceTypeSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        performDriverTypeRegistration();
    }

    public void performDriverTypeRegistration() {

        swipeView.setRefreshing(true);
        JSONObject postData = getDriverTypeRegistrationJSObj();

        DataManager.performDriverTypeRegistration(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
//                App.saveToken(getApplicationContext(), driverDetailsBean);
                startActivity(new Intent(DriverLicenceTypeActivity.this,
                        LegalConsentActivity.class));
                finish();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                 /* To Be Removed....*/
                if (App.getInstance().isDemo()) {
                    startActivity(new Intent(DriverLicenceTypeActivity.this, LegalConsentActivity.class));
                    finish();
                }
            }
        });
    }

    private JSONObject getDriverTypeRegistrationJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("driver_type", driverType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}
