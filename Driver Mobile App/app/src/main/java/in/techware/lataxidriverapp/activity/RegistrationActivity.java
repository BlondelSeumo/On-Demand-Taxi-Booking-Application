package in.techware.lataxidriverapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.listeners.PhoneRegistrationListener;
import in.techware.lataxidriverapp.listeners.RegistrationListener;
import in.techware.lataxidriverapp.model.AuthBean;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.model.CountryBean;
import in.techware.lataxidriverapp.model.CountryListBean;
import in.techware.lataxidriverapp.model.RegistrationBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;
import in.techware.lataxidriverapp.widgets.OTPEditText;

public class RegistrationActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "RegistrationA";
    private ViewFlipper viewFlipper;
    private RegistrationBean registrationBean;
    private EditText etxtName;
    private EditText etxtPhone;
    private EditText etxtEmail;
    private EditText etxtPassword;
    private EditText etxtLocation;
    private Spinner spinnerCountryCodes;
    private ArrayAdapter<String> adapterCountryCodes;
    private String phone;
    private CountryListBean countryListBean;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ImageView ivFlag;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private OTPEditText etxtOne;
    private OTPEditText etxtTwo;
    private OTPEditText etxtThree;
    private OTPEditText etxtFour;
    private OTPEditText etxtFive;
    private OTPEditText etxtSix;
    private LinearLayout llVerification;
    private TextView txtVerificationLabel;
    private String otpCode;
    private boolean isVerificationEnabled;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();

        if (getIntent().hasExtra("phone")) {
            phone = getIntent().getStringExtra("phone");
            registrationBean.setPhone(phone);
//            etxtPhone.setText(phone);
        }

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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

                onHomeClick();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void initViews() {


        registrationBean = new RegistrationBean();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_registration);
        viewFlipper.setDisplayedChild(0);


        llVerification = (LinearLayout) findViewById(R.id.ll_registration_mobile_otp);
        txtVerificationLabel = (TextView) findViewById(R.id.txt_registration_mobile_otp_label);

        spinnerCountryCodes = (Spinner) findViewById(R.id.spinner_registration_mobile_country_code);
        countryListBean = AppConstants.getCountryBean();
        Collections.sort(countryListBean.getCountries());
        ArrayList<String> countryDialCodes = new ArrayList<>();
        for (CountryBean bean : countryListBean.getCountries()) {
            countryDialCodes.add(bean.getDialCode());
        }

//        adapterCountryCodes = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);
        adapterCountryCodes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryDialCodes);
        adapterCountryCodes.setDropDownViewResource(R.layout.item_spinner);
        spinnerCountryCodes.setAdapter(adapterCountryCodes);

        ivFlag = (ImageView) findViewById(R.id.iv_registration_mobile_country_flag);

        etxtName = (EditText) findViewById(R.id.etxt_registration_name);
        etxtPhone = (EditText) findViewById(R.id.etxt_registration_phone);
        etxtEmail = (EditText) findViewById(R.id.etxt_registration_email);
        etxtPassword = (EditText) findViewById(R.id.etxt_registration_password);
        etxtLocation = (EditText) findViewById(R.id.etxt_registration_location);


        etxtOne = (OTPEditText) findViewById(R.id.etxt_registration_mobile_one);
        etxtTwo = (OTPEditText) findViewById(R.id.etxt_registration_mobile_two);
        etxtThree = (OTPEditText) findViewById(R.id.etxt_registration_mobile_three);
        etxtFour = (OTPEditText) findViewById(R.id.etxt_registration_mobile_four);
        etxtFive = (OTPEditText) findViewById(R.id.etxt_registration_mobile_five);
        etxtSix = (OTPEditText) findViewById(R.id.etxt_registration_mobile_six);


        etxtOne.setTypeface(typeface);
        etxtTwo.setTypeface(typeface);
        etxtThree.setTypeface(typeface);
        etxtFour.setTypeface(typeface);
        etxtFive.setTypeface(typeface);
        etxtSix.setTypeface(typeface);

        etxtName.setTypeface(typeface);
        etxtPhone.setTypeface(typeface);
        etxtEmail.setTypeface(typeface);
        etxtPassword.setTypeface(typeface);
        etxtLocation.setTypeface(typeface);
        etxtPassword.setTransformationMethod(new PasswordTransformationMethod());

        viewFlipper.setDisplayedChild(0);

        mAuth = FirebaseAuth.getInstance();
        setVerificationLayoutVisibility(false);

        Glide.with(getApplicationContext())
                .load("file:///android_asset/" + "flags/"
                        + countryListBean.getCountries().get(0).getCountryCode().toLowerCase() + ".gif")
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop())
                .into(ivFlag);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        spinnerCountryCodes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Glide.with(getApplicationContext())
                        .load("file:///android_asset/" + "flags/"
                                + countryListBean.getCountries().get(position).getCountryCode().toLowerCase() + ".gif")
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop())
                        .into(ivFlag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Glide.with(getApplicationContext())
                        .load("file:///android_asset/" + "flags/"
                                + countryListBean.getCountries().get(0).getCountryCode().toLowerCase() + ".gif")
                        .apply(new RequestOptions()
                                .centerCrop()
                                .circleCrop())
                        .into(ivFlag);
            }
        });


        etxtOne.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etxtOne.getText().length();

                if (textlength1 >= 1) {
                    etxtOne.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtTwo.requestFocus();
                } else {
                    etxtOne.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        etxtTwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = etxtTwo.getText().length();

                if (textlength2 >= 1) {
                    etxtTwo.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtThree.requestFocus();
                } else {
                    etxtTwo.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        etxtThree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength3 = etxtThree.getText().length();

                if (textlength3 >= 1) {
                    etxtThree.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtFour.requestFocus();
                } else {
                    etxtThree.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        etxtFour.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFour.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtFive.requestFocus();
                } else {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        etxtFive.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFive.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFive.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtSix.requestFocus();
                } else {
                    etxtFive.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        etxtSix.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtSix.getText().toString().length();

                if (textlength4 == 1) {
                    etxtSix.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                } else {
                    etxtSix.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        etxtSix.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtSix.getText().toString().length();
                if (i == 0) {
                    etxtFive.setText("");
                    etxtFive.requestFocus();
                }
            }
        });
        etxtFive.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtFive.getText().toString().length();
                if (i == 0) {
                    etxtFour.setText("");
                    etxtFour.requestFocus();
                }
            }
        });
        etxtFour.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtFour.getText().toString().length();
                if (i == 0) {
                    etxtThree.setText("");
                    etxtThree.requestFocus();
                }
            }
        });

        etxtThree.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtThree.getText().toString().length();
                if (i == 0) {
                    etxtTwo.setText("");
                    etxtTwo.requestFocus();
                }
            }
        });

        etxtTwo.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtTwo.getText().toString().length();
                if (i == 0) {
                    etxtOne.setText("");
                    etxtOne.requestFocus();
                }
            }
        });

        etxtSix.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFour.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFive.requestFocus();
                    }
                }
            }
        });

        etxtFive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFour.requestFocus();
                    }
                }
            }
        });

        etxtFour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    }
                }
            }
        });

        etxtThree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    }

                }
            }
        });

        etxtTwo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    }
                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.i(TAG, "onVerificationFailed: " + e);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.i(TAG, "onVerificationFailed: " + e);
                }

                /*Snackbar.make(coordinatorLayout, R.string.message_phone_verification_failed, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
*/
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                Snackbar.make(coordinatorLayout, getString(R.string.message_verification_code_sent_to) + " " + registrationBean.getPhone(),
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                setVerificationLayoutVisibility(true);
                swipeView.setRefreshing(false);

            }
        };

    }

    private void setVerificationLayoutVisibility(boolean isVisible) {

        if (isVisible) {
            llVerification.setVisibility(View.VISIBLE);
            txtVerificationLabel.setVisibility(View.VISIBLE);
            etxtOne.requestFocus();
            isVerificationEnabled = true;
        } else {
            llVerification.setVisibility(View.GONE);
            txtVerificationLabel.setVisibility(View.GONE);
            etxtOne.setText("");
            etxtTwo.setText("");
            etxtThree.setText("");
            etxtFour.setText("");
            etxtFive.setText("");
            etxtSix.setText("");
            isVerificationEnabled = false;
        }

    }

    /* MOBILE NUMBER REGISTRATION */

    public void onRegistrationMobileNumberSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

//        viewFlipper.setDisplayedChild(0);

        /*if (collectMobileNumber()) {
//            performPhoneRegistration();
            performMobileAvailabilityCheck(registrationBean.getPhone());
        }*/

        if (isVerificationEnabled) {
            otpCode = "" + etxtOne.getText().toString() + etxtTwo.getText().toString()
                    + etxtThree.getText().toString() + etxtFour.getText().toString()
                    + etxtFive.getText().toString() + etxtSix.getText().toString();

            if (!otpCode.equalsIgnoreCase("")) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpCode);
                signInWithPhoneAuthCredential(credential);
            } else {
                Snackbar.make(coordinatorLayout, getString(R.string.message_invalid_verification_code), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
            }

        } else {
            if (collectMobileNumber()) {
//            performPhoneRegistration();
                performMobileAvailabilityCheck(registrationBean.getPhone());
            }
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        swipeView.setRefreshing(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            swipeView.setRefreshing(false);
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();


                            Log.i(TAG, "onComplete: " + new Gson().toJson(task));

                            viewFlipper.setInAnimation(slideLeftIn);
                            viewFlipper.setOutAnimation(slideLeftOut);
                            viewFlipper.showNext();
                            getSupportActionBar().show();
                            swipeView.setPadding(0, (int) mActionBarHeight, 0, 0);


                        } else {
                            swipeView.setRefreshing(false);
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                Snackbar.make(coordinatorLayout, R.string.message_invalid_verification_code, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                            }
                        }
                    }
                });
    }

    private boolean collectMobileNumber() {

        Log.i(TAG, "collectMobileNumber: Spinner Value : " + spinnerCountryCodes.getSelectedItem().toString());

        if (spinnerCountryCodes.getSelectedItem().toString().equalsIgnoreCase("")) {
            Snackbar.make(coordinatorLayout, getString(R.string.message_please_select_a_country_dial_code), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
            return false;
        }
        if (etxtPhone.getText().toString().equalsIgnoreCase("")) {
            Snackbar.make(coordinatorLayout, getString(R.string.message_phone_number_is_required), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
            return false;
        }


        registrationBean.setPhone(spinnerCountryCodes.getSelectedItem().toString() + etxtPhone.getText().toString());

        return true;
    }

    private void initiatePhoneVerification() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                registrationBean.getPhone(),        // Phone number to verify
                2,                 // Timeout duration
                TimeUnit.MINUTES,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);


        Snackbar.make(coordinatorLayout, R.string.message_sending_verification_code, Snackbar.LENGTH_LONG)
                .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        swipeView.setRefreshing(true);

    }

    public void performMobileAvailabilityCheck(final String phone) {

//        setProgressScreenVisibility(true, true);

        swipeView.setRefreshing(true);

        JSONObject postData = getPhoneNumberAvailabilityJSObj(phone);

        DataManager.performMobileAvailabilityCheck(postData, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
//                setProgressScreenVisibility(false, false);

                if (basicBean.isPhoneAvailable()) {
                    initiatePhoneVerification();
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
//                setProgressScreenVisibility(false, false);
            }
        });
    }

    private JSONObject getPhoneNumberAvailabilityJSObj(String phone) {

        JSONObject postData = new JSONObject();
        try {
            postData.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postData;
    }

    private void performPhoneRegistration() {

        swipeView.setRefreshing(true);
        JSONObject postData = getPhoneRegistrationJSObj();

        DataManager.performPhoneRegistration(postData, new PhoneRegistrationListener() {
            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);
                App.saveToken(authBean);

                Intent intent = new Intent(RegistrationActivity.this, OtpVerificationActivity.class);
                intent.putExtra("phone", registrationBean.getPhone());
//                intent.putExtra("phone_code", phone_code);
                startActivity(intent);
                finish();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getPhoneRegistrationJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("phone", registrationBean.getPhone());
//            postData.put("phone_code", phone_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


   /* EMAIL REGISTRATION */

    public void onRegistrationEmailSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (collectEmailAddress()) {
            viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();
        }
    }

    private boolean collectEmailAddress() {

        registrationBean.setEmail(etxtEmail.getText().toString());

        if (registrationBean.getEmail() == null || registrationBean.getEmail().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_email_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(registrationBean.getEmail()).matches()) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }

    /* PASSWORD REGISTRATION */

    public void onRegistrationPasswordSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        if (collectPassword()) {
            viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();
        }

    }

    private boolean collectPassword() {

        registrationBean.setPassword(etxtPassword.getText().toString());

        if (registrationBean.getPassword() == null || registrationBean.getPassword().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_password_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (registrationBean.getPassword().length() < 8) {
            Snackbar.make(coordinatorLayout, R.string.message_password_minimum_character, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }

    public void onRegistrationNameSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        if (collectName()) {
            viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();
        }
    }

    private boolean collectName() {

        registrationBean.setName(etxtName.getText().toString());

        if (registrationBean.getName() == null || registrationBean.getName().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_name_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }

    public void onRegistrationLocationSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        if (collectLocation()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            viewFlipper.setInAnimation(slideLeftIn);
            viewFlipper.setOutAnimation(slideLeftOut);
            viewFlipper.showNext();
        }

    }

    private boolean collectLocation() {

        registrationBean.setLocation(etxtLocation.getText().toString());

        if (registrationBean.getLocation() == null || registrationBean.getLocation().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_location_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }

    public void onRegistrationSubmitClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performRegistration();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    private void performRegistration() {

        swipeView.setRefreshing(true);

        JSONObject postData = getRegistrationJSObj();

//        ArrayList<String> fileList = getImageFileList();

        DataManager.performRegistration(postData, new RegistrationListener() {
            @Override
            public void onLoadCompleted(AuthBean authBean) {
                swipeView.setRefreshing(false);
                App.saveToken(authBean);

                startActivity(new Intent(RegistrationActivity.this, DriverLicenceTypeActivity.class));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();


                /* To Be Removed....*/
                if (App.getInstance().isDemo()) {
                    startActivity(new Intent(RegistrationActivity.this, DriverLicenceTypeActivity.class));
                    finish();
                }
            }
        });

    }

   /* private ArrayList<String> getImageFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        if (displayPicImage != null && !displayPicImage.equalsIgnoreCase(""))
            fileList.add(displayPicImage);

        return fileList;
    }
*/

    private JSONObject getRegistrationJSObj() {

        JSONObject postData = new JSONObject();

        try {
            postData.put("name", registrationBean.getName());
//            postData.put("gender", registrationBean.getGender());
//            postData.put("DOB", registrationBean.getDOB());
            postData.put("phone", registrationBean.getPhone());
            postData.put("email", registrationBean.getEmail());
            postData.put("password", registrationBean.getPassword());
            postData.put("city", registrationBean.getLocation());
//            postData.put("", registrationBean.get());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //            onBackPressed();

            onHomeClick();
            return true;
        }
        return false;
    }

    private void onHomeClick() {
        int index = viewFlipper.getDisplayedChild();
        if (index > 1) {
            viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
        } else if (index == 1) {
            viewFlipper.setInAnimation(slideRightIn);
            viewFlipper.setOutAnimation(slideRightOut);
            viewFlipper.showPrevious();
            setVerificationLayoutVisibility(false);
            getSupportActionBar().hide();
            swipeView.setPadding(0, 0, 0, 0);
        } else {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }
    }
}
