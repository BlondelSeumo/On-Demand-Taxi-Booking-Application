package in.techware.lataxidriverapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.method.PasswordTransformationMethod;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.LoginListener;
import in.techware.lataxidriverapp.model.AuthBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class LoginActivity extends BaseAppCompatNoDrawerActivity {

    private EditText etxtEmail;
    private EditText etxtPassword;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            lytBase.setPadding(0, App.getStatusBarHeight(), 0, 0);
//            toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }

        initViews();

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);
        lytBase.setFitsSystemWindows(false);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                lytContent.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);

                startActivity(new Intent(this, WelcomeActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, WelcomeActivity.class));
        super.onBackPressed();
    }

    private void initViews() {

        etxtEmail = (EditText) findViewById(R.id.etxt_login_email);
        etxtPassword = (EditText) findViewById(R.id.etxt_login_password);

        Button btnLogin = (Button) findViewById(R.id.btn_login_submit);
        Button btnForgotPassword = (Button) findViewById(R.id.btn_login_forgot_password);

        btnLogin.setTypeface(typeface);
        btnForgotPassword.setTypeface(typefaceBold);

        etxtEmail.setTypeface(typeface);
        etxtPassword.setTypeface(typeface);
        etxtPassword.setTransformationMethod(new PasswordTransformationMethod());

    }


    public void onLoginSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (collectLoginData()) {
            if (App.isNetworkAvailable()) {
                performLogin();
            } else {
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    private boolean collectLoginData() {
        if (!etxtEmail.getText().toString().equals("")) {
            email = etxtEmail.getText().toString();
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                return false;
            }
        } else {
            Snackbar.make(coordinatorLayout, R.string.message_email_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        if (!etxtPassword.getText().toString().equals(""))
            password = etxtPassword.getText().toString();
        else {
            Snackbar.make(coordinatorLayout, R.string.message_password_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }

    private void performLogin() {
        swipeView.setRefreshing(true);

        JSONObject postData = getLoginJSObj();

        DataManager.performLogin(postData, new LoginListener() {
            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);
                App.saveToken(authBean);
                if (!Config.getInstance().isPhoneVerified()) {
                    startActivity(new Intent(LoginActivity.this, OtpVerificationActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                   /* To Be Removed....*/
                if (App.getInstance().isDemo()) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });

    }

    private JSONObject getLoginJSObj() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onLoginForgotPasswordClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }
}
