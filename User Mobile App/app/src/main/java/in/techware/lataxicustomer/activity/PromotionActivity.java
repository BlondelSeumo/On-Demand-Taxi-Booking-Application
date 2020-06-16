package in.techware.lataxicustomer.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.listeners.FreeRideListener;
import in.techware.lataxicustomer.model.FreeRideBean;
import in.techware.lataxicustomer.model.TripBean;
import in.techware.lataxicustomer.net.DataManager;

public class PromotionActivity extends BaseAppCompatNoDrawerActivity {

    private Toolbar toolbarPromotion;
    private TextView txtDateTime;
    private TripBean bean;
    private EditText etxtPromoCode;
    private TextView txtAddCode;
    private String freeRideCode;
    private View.OnClickListener snackBarRefreshOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (etxtPromoCode.isShown()) {
                onBackClick();

            } else {
                onBackPressed();
            }
        }
        return true;
    }

    private void onBackClick() {

       /* etxtPromoCode.setVisibility(View.GONE);
        txtAddCode.setText("ADD CODE");*/
        finish();
    }

    public void initViews() {

        coordinatorLayout.removeView(toolbar);

        toolbarPromotion = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_promotion, toolbar);
        coordinatorLayout.addView(toolbarPromotion, 0);
        setSupportActionBar(toolbarPromotion);

        etxtPromoCode = (EditText) findViewById(R.id.etxt_promo_code);
        txtAddCode = (TextView) findViewById(R.id.txt_add_code);

    }

    public boolean validatePromocode() {

        boolean flag = true;

        if (etxtPromoCode.getText().toString().length() <= 2) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_promocode, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.btn_refresh, snackBarRefreshOnClickListener).show();
            etxtPromoCode.requestFocus();
            flag = false;
        }
        return flag;
    }

    public void performFreeRide() {

        swipeView.setRefreshing(true);
        JSONObject postData = getFreeRideJSObj();

        DataManager.performFreeRide(postData, new FreeRideListener() {

            @Override
            public void onLoadCompleted(FreeRideBean freeRideBean) {

                swipeView.setRefreshing(false);

                Toast.makeText(getApplicationContext(), R.string.message_promocode_is_successfully_sent, Toast.LENGTH_LONG).show();

                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    private JSONObject getFreeRideJSObj() {
        JSONObject postData = new JSONObject();

        freeRideCode = etxtPromoCode.getText().toString();

        try {
            postData.put("free_ride_code", freeRideCode);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onAddCodeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        if (etxtPromoCode.isShown()) {
            if (validatePromocode()) {
                performFreeRide();
            }
        } else {
            etxtPromoCode.setVisibility(View.VISIBLE);
            txtAddCode.setText(R.string.btn_apply_promo_code);
        }
    }
}
