package in.techware.lataxidriverapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;

public class ProfilePhotoUploadActivity extends BaseAppCompatNoDrawerActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICKER_SELECT = 2;
    private String imagePath = "";
    private String documentPath;
    private ImageView ivProfilePhotoPreview;
    private Button btnRetake;
    private Button btnSave;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo_upload);

        initViews();


        getSupportActionBar().setTitle("Profile Photo");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            documentPath = imagePath;
            //    setBannerPic(tempImagePath);
            setProfilePhotoImage(imagePath);
        }
    }

    private void initViews() {

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_profile_photo_upload);

        ivProfilePhotoPreview = (ImageView) findViewById(R.id.iv_profile_photo_upload_preview);

        btnRetake = (Button) findViewById(R.id.btn_profile_photo_upload_retake);
        btnSave = (Button) findViewById(R.id.btn_profile_photo_upload_save);

        btnRetake.setTypeface(typeface);
        btnSave.setTypeface(typeface);


    }

    private void setProfilePhotoImage(String imagePath) {

        viewFlipper.setDisplayedChild(1);
        Glide.with(getApplicationContext())
                .load(imagePath)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(ivProfilePhotoPreview);

//        ibClearDisplayPic.setVisibility(View.VISIBLE);

    }

    public void onProfilePhotoUploadTakePhotoClick(View view) {

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


    public void onProfilePhotoUploadRetakeClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        onProfilePhotoUploadTakePhotoClick(view);
    }

    public void onProfilePhotoUploadSaveClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        performProfilePhotoUploadSave();
    }

    public void performProfilePhotoUploadSave() {

        swipeView.setRefreshing(true);
        JSONObject postData = getProfilePhotoUploadSaveJSObj();

        ArrayList<String> fileList = getFileList();

        DataManager.performProfilePhotoUpload(postData, fileList, new BasicListener() {

            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);

                startActivity(new Intent(ProfilePhotoUploadActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                 /* To Be Removed....*/
                if (App.getInstance().isDemo()) {
                    startActivity(new Intent(ProfilePhotoUploadActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
    }

    private ArrayList<String> getFileList() {
        ArrayList<String> fileList = new ArrayList<>();

        if (documentPath != null && !documentPath.equals(""))
            fileList.add(documentPath);

        return fileList;
    }

    private JSONObject getProfilePhotoUploadSaveJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("auth_token", Config.getInstance().getAuthToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

}
