package in.techware.lataxicustomer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.listeners.LocationSaveListener;
import in.techware.lataxicustomer.listeners.UserInfoListener;
import in.techware.lataxicustomer.model.LocationBean;
import in.techware.lataxicustomer.model.PlaceBean;
import in.techware.lataxicustomer.model.UserBean;
import in.techware.lataxicustomer.net.DataManager;
import in.techware.lataxicustomer.util.AppConstants;

//import com.digits.sdk.android.Digits;

public class SettingsPageActivity extends BaseAppCompatNoDrawerActivity {

    private static final int REQ_HOME_PLACE = 0;
    private static final int REQ_WORK_PLACE = 1;
    private static final int HOME_LOCATION = 0;
    private static final int WORK_LOCATION = 1;
    private static final String TAG = "SPAc";
    private Toolbar toolbarSettings;
    private TextView txtName;
    private TextView txtMobile;
    private TextView txtEmail;
    private UserBean userBean;
    private ImageView ivProfilePhoto;
    private TextView txtAddHome;
    private TextView txtAddWork;
    private int addHome;
    private int addWork;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private PlaceBean homeLocationBean;
    private PlaceBean workLocationBean;
    private LinearLayout llOnAddHome,llOnAddWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        initViews();

        getData();

//        swipeView.setRefreshing(true);
        setProgressScreenVisibility(true, true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void getData() {

        if (App.isNetworkAvailable()) {
            fetchUserDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        fetchUserDetails();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQ_HOME_PLACE && resultCode == RESULT_OK) {

            homeLocationBean = (PlaceBean) data.getSerializableExtra("location");
            Log.i(TAG, "onActivityResult: HOME LACE BEAN : " + new Gson().toJson(homeLocationBean));

            txtAddHome.setText(homeLocationBean.getName());

            Toast.makeText(getApplicationContext(), R.string.message_home_location_added,
                    Toast.LENGTH_LONG).show();

            performLocationSave(HOME_LOCATION);

        }

        if (requestCode == REQ_WORK_PLACE && resultCode == RESULT_OK) {

            workLocationBean = (PlaceBean) data.getSerializableExtra("location");
            Log.i(TAG, "onActivityResult: WORK LACE BEAN : " + new Gson().toJson(workLocationBean));

            txtAddWork.setText(workLocationBean.getName());

            Toast.makeText(getApplicationContext(), R.string.message_work_location_added,
                    Toast.LENGTH_LONG).show();

            performLocationSave(WORK_LOCATION);

        }
    }

    public void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //  mVibrator.vibrate(25);
                getData();
            }
        };

        coordinatorLayout.removeView(toolbar);

        toolbarSettings = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar_settings_page, toolbar);
        coordinatorLayout.addView(toolbarSettings, 0);
        setSupportActionBar(toolbarSettings);

        txtAddHome = (TextView) findViewById(R.id.txt_add_home);
        txtAddWork = (TextView) findViewById(R.id.txt_add_work);

        txtName = (TextView) findViewById(R.id.txt_name);
        txtMobile = (TextView) findViewById(R.id.txt_mobile);
//        txtMobileCode = (TextView) findViewById(R.id.txt_mobile_code);
        txtEmail = (TextView) findViewById(R.id.txt_email);

        ivProfilePhoto = (ImageView) findViewById(R.id.iv_profile_photo);

//        ivProfileBackground = (ImageView) findViewById(R.id.iv_profile_background);


        llOnAddHome=findViewById(R.id.ll_on_add_home);
        llOnAddHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                addHome = AppConstants.ADD_HOME;

                Intent intent = new Intent(SettingsPageActivity.this, SearchHomeWorkActivity.class);
                intent.putExtra("search_type", addHome);
                startActivityForResult(intent, REQ_HOME_PLACE);

            }
        });

        llOnAddWork=findViewById(R.id.ll_on_add_work);
        llOnAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                addWork = AppConstants.ADD_WORK;

                Intent intent = new Intent(SettingsPageActivity.this, SearchHomeWorkActivity.class);
                intent.putExtra("search_type", addWork);
                startActivityForResult(intent, REQ_WORK_PLACE);
            }
        });
    }

    public void fetchUserDetails() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("auth_token", Config.getInstance().getAuthToken());

        DataManager.fetchUserInfo(urlParams, Config.getInstance().getUserID(), new UserInfoListener() {
            @Override
            public void onLoadCompleted(UserBean userBeanWS) {

                swipeView.setRefreshing(false);
                setProgressScreenVisibility(false, false);

                System.out.println("Successfull  : UserBean : " + userBeanWS);
                userBean = userBeanWS;
                populateUserInfo(userBeanWS);

            }

            @Override
            public void onLoadFailed(String errorMsg) {

                swipeView.setRefreshing(false);

                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

            }
        });
    }

    private void populateUserInfo(UserBean userBean) {

        if (userBean.getAddHome().equalsIgnoreCase("null")) {
            txtAddHome.setText(R.string.title_add_home);
        } else {
            txtAddHome.setText(userBean.getAddHome());
        }

        if (userBean.getAddWork().equalsIgnoreCase("null")) {
            txtAddWork.setText(R.string.title_add_work);
        } else {
            txtAddWork.setText(userBean.getAddWork());
        }

        ivProfilePhoto.setVisibility(View.VISIBLE);

        txtName.setText(userBean.getName());
        txtEmail.setText(userBean.getEmail());
        txtMobile.setText(userBean.getMobileNumber());
//        txtMobileCode.setText(userBean.getMobileCode());

        Glide.with(getApplicationContext())
                .load(userBean.getProfilePhoto())
//                .fallback(R.drawable.ic_dummy_photo)
//                .error(R.drawable.ic_dummy_photo)
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);
    }

    public void onEditAccountClick(MenuItem item) {

        Intent intent = new Intent(SettingsPageActivity.this, ProfileEditActivity.class);
        intent.putExtra("bean", userBean);
        startActivity(intent);

    }

  /*  public void onAddHomeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        addHome = AppConstants.ADD_HOME;

        Intent intent = new Intent(SettingsPageActivity.this, SearchHomeWorkActivity.class);
        intent.putExtra("search_type", addHome);
        startActivityForResult(intent, REQ_HOME_PLACE);
    }

    public void onAddWorkClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        addWork = AppConstants.ADD_WORK;

        Intent intent = new Intent(SettingsPageActivity.this, SearchHomeWorkActivity.class);
        intent.putExtra("search_type", addWork);
        startActivityForResult(intent, REQ_WORK_PLACE);
    }*/

    public void performLocationSave(int type) {

        JSONObject postData = getLocationSaveJSObj(type);

        DataManager.performLocationSave(postData, new LocationSaveListener() {

            @Override
            public void onLoadCompleted(LocationBean locationBean) {

                swipeView.setRefreshing(false);

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getLocationSaveJSObj(int type) {
        JSONObject postData = new JSONObject();

        try {

            if (type == HOME_LOCATION) {
                postData.put("type", HOME_LOCATION);
                postData.put("home", homeLocationBean.getName());
                postData.put("home_latitude", homeLocationBean.getLatitude());
                postData.put("home_longitude", homeLocationBean.getLongitude());
            } else {
                postData.put("type", WORK_LOCATION);
                postData.put("work", workLocationBean.getName());
                postData.put("work_latitude", workLocationBean.getLatitude());
                postData.put("work_longitude", workLocationBean.getLongitude());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void onSignOutClick(MenuItem item) {

        App.logout();
        startActivity(new Intent(this, SplashActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }
}

