package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.FileOp;

/*
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
*/


public class WelcomeActivity extends BaseAppCompatNoDrawerActivity {

//    private AuthConfig authConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.bg_welcome_header));
        }

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        initViews();

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        } else {
            new FileOp(this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        App.logout();
    }

    private void initViews() {

        /*AuthConfig.Builder builder = new AuthConfig.Builder();

        builder.withAuthCallBack(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phone) {
                performMobileAvailabilityCheck(phone);
            }

            @Override
            public void failure(DigitsException exception) {
                Snackbar.make(coordinatorLayout, "Phone Verification Failed..... Try Again!", Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
//                Log.i("Digits", "Sign in with Digits failure", exception);
            }
        });

        authConfig = builder.build();*/

    }

    public void onWelcomeActivitySignInClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        finish();
    }

    public void onWelcomeActivityRegisterClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

   /*     Digits.logout();
        Digits.authenticate(authConfig);*/
        Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
        startActivity(intent);
        finish();
    }


    public void performMobileAvailabilityCheck(final String phone) {

        setProgressScreenVisibility(true, true);

//        swipeView.setRefreshing(true);

        JSONObject postData = getPhoneNumberAvailabilityJSObj(phone);

        DataManager.performMobileAvailabilityCheck(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(false, false);

                if (basicBean.isPhoneAvailable()) {
                    setProgressScreenVisibility(false, false);
                    Toast.makeText(getApplicationContext(), R.string.message_phone_verified_successfully,
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(coordinatorLayout, phone + getString(R.string.message_is_already_registered), Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                setProgressScreenVisibility(false, false);
            }
        });
    }

    private JSONObject getPhoneNumberAvailabilityJSObj(String phone) {
//        String str = "{\"time_to_live\":60,\"data\":{\"response\":{\"status\":\"success\",\"data\":{\"id\":\"\".$fcm_data['id'].\"\"}}},\"to\":\"\".$fcm_token.\"\"}";
        JSONObject postData = new JSONObject();
        try {
            postData.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }
}
