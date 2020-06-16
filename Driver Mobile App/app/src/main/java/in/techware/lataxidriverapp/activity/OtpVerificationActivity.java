package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.widgets.OTPEditText;

public class OtpVerificationActivity extends BaseAppCompatNoDrawerActivity {

    private String phone_number;
    private String phone_code;
    private OTPEditText etxtOne;
    private OTPEditText etxtTwo;
    private OTPEditText etxtThree;
    private OTPEditText etxtFive;
    private OTPEditText etxtSix;
    private OTPEditText etxtFour;
    //    private TextView txtHeaderTime;
//    private TextView txtPhoneCode;
    private TextView txtPhoneNumber;
    private FloatingActionButton fabNext;
    private String otpCode;
    private String resendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        initViews();
        getIntentData();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }*/

    public void initViews() {

        etxtOne = (OTPEditText) findViewById(R.id.etxt_otp_verification_one);
        etxtTwo = (OTPEditText) findViewById(R.id.etxt_otp_verification_two);
        etxtThree = (OTPEditText) findViewById(R.id.etxt_otp_verification_three);
        etxtFour = (OTPEditText) findViewById(R.id.etxt_otp_verification_four);
        etxtFive = (OTPEditText) findViewById(R.id.etxt_otp_verification_five);
        etxtSix = (OTPEditText) findViewById(R.id.etxt_otp_verification_six);
//        txtHeaderTime = (TextView) findViewById(R.id.txt_timer);

        fabNext = (FloatingActionButton) findViewById(R.id.fab_otp_verification_next);

//        txtPhoneCode = (TextView) findViewById(R.id.txt_phone_code);
        txtPhoneNumber = (TextView) findViewById(R.id.txt_phone_number);

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
                Integer textlength4 = etxtFour.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_app_edge);
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
        etxtSix.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFour.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_app_edge);
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
    }

    public void getIntentData() {

        phone_number = getIntent().getStringExtra("phone_number");
        phone_code = getIntent().getStringExtra("phone_code");

//        txtPhoneCode.setText(phone_code);
        txtPhoneNumber.setText(phone_number);
    }

    /*private boolean isCountDownRunning;
    CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {


        public void onTick(long millisUntilFinished) {
            txtHeaderTime.setText("" + String.format(" 0%d : %d ",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }

        public void onFinish() {
            fabNext.setEnabled(false);
            txtHeaderTime.setText(" RESEND CODE!");
            txtHeaderTime.setEnabled(true);
            isCountDownRunning = false;
            etxtOne.setText("");
            etxtTwo.setText("");
            etxtThree.setText("");
            etxtFour.setText("");
            etxtOne.requestFocus();
        }
    }.start();*/

    public void onOTPResendClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//        onResentClick();
        performOTPResendCode();
//        //mVibrator.vibrate(25);

    }

/*    private void onResentClick() {
        fabNext.setEnabled(true);

        if (!isCountDownRunning) {
            isCountDownRunning = true;
            countDownTimer.start();
        } else {
            countDownTimer.cancel(); // cancel
            countDownTimer.start();  // then restart
        }
    }*/

    public void performOtpSubmit() {

        swipeView.setRefreshing(true);
        JSONObject postData = getOTPCodeSubmitJSObj();

        DataManager.performOTPSubmit(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);

                startActivity(new Intent(OtpVerificationActivity.this, RegistrationActivity.class)
                        .putExtra("isOTPVerified", true));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getOTPCodeSubmitJSObj() {
        JSONObject postData = new JSONObject();

        otpCode = etxtOne.getText().toString() + etxtTwo.getText().toString() + etxtThree.getText().toString() + etxtFour.getText().toString();

        try {
            postData.putOpt("otp_code", otpCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void performOTPResendCode() {

        swipeView.setRefreshing(true);
        JSONObject postData = getOTPCodeJSObj();

        DataManager.performOTPResendCode(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, R.string.message_otp_send_to_your_phone_number, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }

    private JSONObject getOTPCodeJSObj() {
        JSONObject postData = new JSONObject();

        return postData;
    }

    public void onOtpNextButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (collectOTPData()) {
            performOtpSubmit();
        }

    }

    private boolean collectOTPData() {

        if (etxtOne.getText().toString().length() == 0) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_OTP, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (etxtTwo.getText().toString().length() == 0) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_OTP, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (etxtThree.getText().toString().length() == 0) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_OTP, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (etxtFour.getText().toString().length() == 0) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_OTP, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }

        return true;
    }

    public void onSkipClick(View view) {
        finish();
    }

    public void onOtpPageBackButtonClick(View view) {
        finish();
    }
}
