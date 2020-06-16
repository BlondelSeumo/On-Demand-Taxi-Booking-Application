package in.techware.lataxidriverapp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.listeners.ProfileListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.model.ProfileBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

/*import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;*/

public class ProfileActivity extends BaseAppCompatNoDrawerActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQ_MOBILE_VERIFICATION = 2;
    private boolean isEditing = false;
    private MenuItem menuProfileEdit;
    private MenuItem menuProfileSave;
    private TextInputLayout tilAddress1;
    private TextInputLayout tilCity;
    private TextInputLayout tilState;
    private TextInputLayout tilPostalCode;
    private TextInputEditText etxtAddress1;
    private TextInputEditText etxtCity;
    private TextInputEditText etxtState;
    private TextInputEditText etxtPostalCode;
    private EditText etxtName;
    private EditText etxtEmail;
    private TextView txtPhone;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private ProfileBean profileBean;
    private ProfileBean editProfileBean;
    private ImageView ivProfilePhoto;
    private String imagePath;
    private String documentPath;
//    private AuthConfig authConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        initViews();

        getSupportActionBar().setTitle(R.string.label_profile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (menuProfileSave != null && menuProfileEdit != null) {
            if (isEditing) {
                menuProfileSave.setVisible(true);
                menuProfileEdit.setVisible(false);
            } else {
                menuProfileSave.setVisible(false);
                menuProfileEdit.setVisible(true);
            }
        }

        setEditable(isEditing);


        if (!isEditing) {
            if (profileBean == null) {
                setProgressScreenVisibility(true, true);
                getData(false);
            } else {
                getData(true);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        menuProfileEdit = menu.findItem(R.id.action_profile_edit);
        menuProfileSave = menu.findItem(R.id.action_profile_save);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                toolbar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                finish();
                return true;
            case R.id.action_profile_edit:
                toolbar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                isEditing = true;
                menuProfileEdit.setVisible(false);
                menuProfileSave.setVisible(true);

                setEditable(isEditing);

                return true;
            case R.id.action_profile_save:
                toolbar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);

                if (collectProfileEditData()) {
                    if (App.isNetworkAvailable()) {
                        performProfileUpdate();
                    } else {
                        Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                                .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                    }
                }

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchProfile();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            documentPath = imagePath;
            //    setBannerPic(tempImagePath);
            setProfilePhotoImage(imagePath);
            isEditing = true;
            menuProfileEdit.setVisible(false);
            menuProfileSave.setVisible(true);
            setEditable(true);
        }

        if (requestCode == REQ_MOBILE_VERIFICATION && resultCode == RESULT_OK) {

            String phone = "";
            if (data.hasExtra("phone"))
                phone = data.getStringExtra("phone");

            Toast.makeText(getApplicationContext(), R.string.message_phone_verified_successfully,
                    Toast.LENGTH_LONG).show();
            txtPhone.setText(phone);
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setPhone(phone);
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

        ivProfilePhoto = (ImageView) findViewById(R.id.iv_profile_display_photo);

        tilAddress1 = (TextInputLayout) findViewById(R.id.til_profile_address_1);
        tilCity = (TextInputLayout) findViewById(R.id.til_profile_city);
        tilState = (TextInputLayout) findViewById(R.id.til_profile_state);
        tilPostalCode = (TextInputLayout) findViewById(R.id.til_profile_postal_code);

        etxtAddress1 = (TextInputEditText) findViewById(R.id.tietxt_profile_address_1);
        etxtCity = (TextInputEditText) findViewById(R.id.tietxt_profile_city);
        etxtState = (TextInputEditText) findViewById(R.id.tietxt_profile_state);
        etxtPostalCode = (TextInputEditText) findViewById(R.id.tietxt_profile_postal_code);

        etxtName = (EditText) findViewById(R.id.etxt_profile_name);
        etxtEmail = (EditText) findViewById(R.id.etxt_profile_email);
        txtPhone = (TextView) findViewById(R.id.txt_profile_mobile);


        etxtAddress1.setTypeface(typeface);
        etxtCity.setTypeface(typeface);
        etxtState.setTypeface(typeface);
        etxtPostalCode.setTypeface(typeface);
        etxtName.setTypeface(typeface);
        etxtEmail.setTypeface(typeface);

/*
        AuthConfig.Builder builder = new AuthConfig.Builder();

        builder.withAuthCallBack(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phone) {

                Toast.makeText(getApplicationContext(), R.string.message_phone_verified_successfully,
                        Toast.LENGTH_LONG).show();
                txtPhone.setText(phone);
                if (editProfileBean == null)
                    editProfileBean = new ProfileBean();
                editProfileBean.setPhone(phone);
            }

            @Override
            public void failure(DigitsException exception) {
                *//*Snackbar.make(coordinatorLayout, "Phone Verification Failed..... Try Again!", Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();*//*
                Log.i("Digits", "Sign in with Digits failure", exception);
            }
        });

        authConfig = builder.build();*/

    }

    private void setProfilePhotoImage(String imagePath) {

        Glide.with(getApplicationContext())
                .load(imagePath)
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);

//        ibClearDisplayPic.setVisibility(View.VISIBLE);

    }

    private void setEditable(boolean isEditing) {

        etxtName.setEnabled(isEditing);
        etxtEmail.setEnabled(isEditing);
        txtPhone.setEnabled(isEditing);
        etxtCity.setEnabled(isEditing);
        etxtState.setEnabled(isEditing);
        etxtAddress1.setEnabled(isEditing);
        etxtPostalCode.setEnabled(isEditing);
    }

    private void fetchProfile() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("auth_token", Config.getInstance().getAuthToken());

/*        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }*/

        DataManager.fetchProfile(urlParams, new ProfileListener() {
            @Override
            public void onLoadCompleted(ProfileBean profileBeanWS) {

                profileBean = profileBeanWS;
                populateProfile();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                if (App.getInstance().isDemo()) {
                    profileBean = new ProfileBean();
                    populateProfile();
                }
            }
        });

    }

    private void populateProfile() {


        etxtName.setText(profileBean.getName());
        etxtEmail.setText(profileBean.getEmail());
        txtPhone.setText(profileBean.getPhone());
        etxtAddress1.setText(profileBean.getAddress());
        etxtCity.setText(profileBean.getCity());
        etxtState.setText(profileBean.getState());
        etxtPostalCode.setText(profileBean.getPostalCode());

        Glide.with(getApplicationContext())
                .load(profileBean.getProfilePhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_dummy_driver_photo)
                        .fallback(R.drawable.ic_dummy_driver_photo)
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    private boolean collectProfileEditData() {


        String email = etxtEmail.getText().toString();
        String name = etxtName.getText().toString();
        String phone = txtPhone.getText().toString();
        String address1 = etxtAddress1.getText().toString();
        String city = etxtCity.getText().toString();
        String state = etxtState.getText().toString();
        String postalCode = etxtPostalCode.getText().toString();

        if (name.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_name_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!name.equals(profileBean.getName())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setName(name);
        }

        if (email.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_email_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!email.equalsIgnoreCase(profileBean.getEmail())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setEmail(email);
        }

        if (address1.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_address_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!address1.equals(profileBean.getAddress())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setAddress(address1);
        }

        if (phone.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_phone_number_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!phone.equalsIgnoreCase(profileBean.getPhone())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setPhone(phone);
        }

        if (city.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_city_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!city.equalsIgnoreCase(profileBean.getCity())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setCity(city);
        }

        if (state.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_state_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!state.equalsIgnoreCase(profileBean.getState())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setState(state);
        }

        if (postalCode.equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_postal_code_is_required, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!postalCode.equalsIgnoreCase(profileBean.getPostalCode())) {
            if (editProfileBean == null)
                editProfileBean = new ProfileBean();
            editProfileBean.setPostalCode(postalCode);
        }


        return true;
    }

    public void onProfileTakePhotoClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
//                    imagePath = image.getAbsolutePath();
                    photoFile = App.createImageFile(0).getAbsoluteFile();
                    imagePath = photoFile.getAbsolutePath();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                if (photoFile != null) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                getApplicationContext().getPackageName() + ".provider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    }
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }

    }

    public void onProfileMobileClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        FirebaseAuth.getInstance().signOut();
//        Digits.logout();
//        Digits.authenticate(authConfig);

        startActivityForResult(new Intent(this, MobileVerificationActivity.class)
                , REQ_MOBILE_VERIFICATION);
    }

    private void performProfileUpdate() {

        swipeView.setRefreshing(true);
        JSONObject postData = getProfileUpdateJSObj();

        ArrayList<String> fileList = getFileList();

        DataManager.performProfileUpdate(postData, fileList, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, R.string.message_profile_updated_successfully, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                isEditing = false;
                setEditable(false);
                menuProfileSave.setVisible(false);
                menuProfileEdit.setVisible(true);
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });
    }

    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        if (documentPath != null && !documentPath.equals(""))
            fileList.add(documentPath);

        return fileList;
    }

    private JSONObject getProfileUpdateJSObj() {
        JSONObject postData = new JSONObject();

        if (editProfileBean == null)
            editProfileBean = new ProfileBean();

        try {
//            postData.put("auth_token", Config.getInstance().getAuthToken());
            postData.put("name", editProfileBean.getName() != null && !editProfileBean.getName().equalsIgnoreCase("")
                    ? editProfileBean.getName() : profileBean.getName());
            if (editProfileBean.getEmail() != null && !editProfileBean.getEmail().equalsIgnoreCase("")
                    && !editProfileBean.getEmail().equalsIgnoreCase(profileBean.getEmail()))
                postData.put("email", editProfileBean.getEmail());

            if (editProfileBean.getPhone() != null && !editProfileBean.getPhone().equalsIgnoreCase("")
                    && !editProfileBean.getPhone().equalsIgnoreCase(profileBean.getPhone()))
                postData.put("phone", editProfileBean.getPhone());

            postData.put("address", editProfileBean.getAddress() != null && !editProfileBean.getAddress().equalsIgnoreCase("")
                    ? editProfileBean.getAddress() : profileBean.getAddress());
            postData.put("city", editProfileBean.getCity() != null && !editProfileBean.getCity().equalsIgnoreCase("")
                    ? editProfileBean.getCity() : profileBean.getCity());
            postData.put("state", editProfileBean.getState() != null && !editProfileBean.getState().equalsIgnoreCase("")
                    ? editProfileBean.getState() : profileBean.getState());
            postData.put("postal_code", editProfileBean.getPostalCode() != null && !editProfileBean.getPostalCode().equalsIgnoreCase("")
                    ? editProfileBean.getPostalCode() : profileBean.getPostalCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
}
