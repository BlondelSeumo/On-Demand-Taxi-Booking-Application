package in.techware.lataxidriverapp.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.TypefaceCache;
import in.techware.lataxidriverapp.dialogs.PopupMessage;
import in.techware.lataxidriverapp.listeners.PermissionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected static final int REQUEST_ENABLE_BT = 0;
    protected static final int REQUEST_PERMISSIONS = 1;
    protected static final int REQUEST_PERMISSIONS_LOCATION = 2;
    protected static final int REQUEST_PERMISSIONS_READ_CONTACTS = 3;
    protected static final int REQUEST_PERMISSIONS_READ_WRITE = 4;
    protected static final int REQUEST_PERMISSIONS_GET_ACCOUNTS = 5;
    protected static final int REQUEST_PERMISSIONS_READ_PHONE_STATE = 6;
    protected static final int REQUEST_PERMISSIONS_CALL = 7;
    protected static final int REQUEST_PERMISSIONS_SMS = 8;

    protected boolean isBluetoothEnableRequestShown;
    protected boolean isLocationServiceEnableRequestShown;

    protected boolean hasAllPermissions;
    protected boolean hasLocationPermissions;
    protected boolean hasReadWritePermissions;
    protected boolean hasReadContactsPermissions;
    protected boolean hasGetAccountsPermissions;
    protected boolean hasReadPhoneStatePermissions;
    protected boolean hasCallPermissions;
    protected boolean hasSMSPermissions;

    private PermissionListener permissionListener;

    protected View.OnClickListener snackBarDismissOnClickListener;

    private static final String TAG = "BaseFrag";
    protected View lytBase;
    protected CoordinatorLayout coordinatorLayout;
    protected FrameLayout lytContent;
    private View lytProgress;
    private ProgressBar progressBase;
    private View lytMessage;
    private TextView txtMessage;
    private ImageView ivMessage;
    protected Resources r;
    protected float px;
    protected int width;
    protected int height;
    protected Typeface typeface;
    protected Typeface typefaceBold;
    protected Typeface typefaceItalic;
    protected Typeface typefaceBoldItalic;

    protected Animation disappear;
    protected Animation slideLeftIn;
    protected Animation slideLeftOut;
    protected Animation slideUpIn;
    protected Animation slideDownOut;
    protected Animation slideDownIn;
    protected Animation slideUpOut;
    protected Animation fadeIn;
    protected Animation fadeOut;
    protected Animation fadeFastIn;
    protected Animation fadeFastOut;
    protected Animation growBottom;
    protected Animation shrinkBottom;
    protected Animation growFromBottomRightToTopLeft;
    protected Animation shrinkFromTopLeftToBottomRight;
    protected int selectableItemBackground;

    public BaseFragment() {
        // Required empty public constructor
    }

    public void initBase(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        lytBase = inflater.inflate(R.layout.layout_base_fragment, container, false);

        coordinatorLayout = (CoordinatorLayout) lytBase.findViewById(R.id.lyt_base_fragment);

        lytContent = (FrameLayout) lytBase.findViewById(R.id.lyt_contents_base_fragment);

        lytProgress = lytBase.findViewById(R.id.lyt_progress_base_fragment);
        progressBase = (ProgressBar) lytBase.findViewById(R.id.progress_base_fragment);

        lytMessage = lytBase.findViewById(R.id.lyt_default_message_base_fragment);
        txtMessage = (TextView) lytBase.findViewById(R.id.txt_default_message_base_fragment);
        ivMessage = (ImageView) lytBase.findViewById(R.id.iv_default_message_base_fragment);

        Log.i(TAG, "onCreateView: Base Fragment Initiated");

        setMessageScreenVisibility(false, false, true, R.drawable.logo_splash,
                getString(R.string.label_nothing_to_show));
        setProgressScreenVisibility(false, false);

        r = getResources();
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        width = r.getDisplayMetrics().widthPixels;
        height = r.getDisplayMetrics().heightPixels;

        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        selectableItemBackground = typedValue.resourceId;

        disappear = AnimationUtils.loadAnimation(getContext(), R.anim.disappear);
        slideLeftIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_left_out);
        slideUpIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_in);
        slideDownOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_out);
        slideDownIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_in);
        slideUpOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_out);
        fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        fadeFastIn = AnimationUtils.loadAnimation(getContext(), R.anim.fast_fade_in);
        fadeFastOut = AnimationUtils.loadAnimation(getContext(), R.anim.fast_fade_out);
        growBottom = AnimationUtils.loadAnimation(getContext(), R.anim.grow_from_bottom);
        shrinkBottom = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_from_top);
        growFromBottomRightToTopLeft = AnimationUtils.loadAnimation(getContext(), R.anim.grow_from_bottomright_to_topleft);
        shrinkFromTopLeftToBottomRight = AnimationUtils.loadAnimation(getContext(), R.anim.shrink_from_topleft_to_bottomright);

        try {
            typeface = TypefaceCache.getInstance().getTypeface(getString(R.string.font_roboto_regular));
            typefaceBold = TypefaceCache.getInstance().getTypeface(getString(R.string.font_roboto_bold));
            typefaceItalic = TypefaceCache.getInstance().getTypeface(getString(R.string.font_roboto_italic));
            typefaceBoldItalic = TypefaceCache.getInstance().getTypeface(getString(R.string.font_roboto_bold_italic));
        } catch (Exception e) {
            e.printStackTrace();
        }

        snackBarDismissOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                v.setVisibility(View.GONE);

            }
        };


    }

    protected void setMessageScreenVisibility(boolean isScreenVisible, boolean isImageVisible, boolean isTransparentBackground, int imagePath, String message) {
        if (isScreenVisible) {
            lytMessage.setVisibility(View.VISIBLE);
            txtMessage.setText(message);
            if (isImageVisible) {
                ivMessage.setVisibility(View.VISIBLE);
                ivMessage.setImageResource(imagePath);
/*                Glide.with(App.getInstance().getApplicationContext())
                        .load(imagePath)
                        .into(ivMessage);*/
            } else {
                ivMessage.setVisibility(View.GONE);
            }
        } else
            lytMessage.setVisibility(View.GONE);

        if (isTransparentBackground) {
            lytMessage.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.transparent));
        } else {
            lytMessage.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.white));
        }
    }

    protected void setProgressScreenVisibility(boolean isScreenVisible, boolean isProgressVisible) {
        if (isScreenVisible) {
            lytProgress.setVisibility(View.VISIBLE);
            if (isProgressVisible) {
                progressBase.setVisibility(View.VISIBLE);
            } else {
                progressBase.setVisibility(View.GONE);
            }
        } else
            lytProgress.setVisibility(View.GONE);
    }


    void setPermissionListener(PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    protected boolean checkForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.CLEAR_APP_CACHE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                /*String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.CLEAR_APP_CACHE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS);*/
                return hasAllPermissions = false;
            } else {
                return hasAllPermissions = true;
            }
        } else {
            return hasAllPermissions = true;
        }
    }

    protected void getAllPermssions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.CLEAR_APP_CACHE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.CLEAR_APP_CACHE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_PHONE_STATE};

                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS);
            }
        }
    }

    protected boolean checkForContactsPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

               /* String[] permissions = new String[]{
                        Manifest.permission.READ_CONTACTS};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_CONTACTS);*/
                return hasReadContactsPermissions = false;
            } else {
                return hasReadContactsPermissions = true;
            }
        } else {
            return hasReadContactsPermissions = true;
        }
    }

    protected void getContactsPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[]{
                        Manifest.permission.READ_CONTACTS};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_CONTACTS);
            }
        }
    }

    protected boolean checkForLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                /*String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_LOCATION);*/
                return hasLocationPermissions = false;
            } else {
                return hasLocationPermissions = true;
            }
        } else {
            return hasLocationPermissions = true;
        }
    }

    protected void getLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_LOCATION);
            }
        }
    }

    protected boolean checkForReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                /*String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_WRITE);*/
                return hasReadWritePermissions = false;
            } else {
                return hasReadWritePermissions = true;
            }
        } else {
            return hasReadWritePermissions = true;
        }
    }

    protected void getReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_WRITE);
            }
        }
    }

    protected boolean checkForGetAccountsPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
              /*  String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_GET_ACCOUNTS);*/
                return hasGetAccountsPermissions = false;
            } else {
                return hasGetAccountsPermissions = true;
            }
        } else {
            return hasGetAccountsPermissions = true;
        }
    }

    protected void getGetAccountsPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_GET_ACCOUNTS);
            }
        }
    }


    protected boolean checkForReadPhoneStatePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
//                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
                return hasReadPhoneStatePermissions = false;
            } else {
                return hasReadPhoneStatePermissions = true;
            }
        } else {
            return hasReadPhoneStatePermissions = true;
        }
    }

    protected void getReadPhoneStatePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
            }
        }
    }

    protected boolean checkForCallPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
//                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
                return hasCallPermissions = false;
            } else {
                return hasCallPermissions = true;
            }
        } else {
            return hasCallPermissions = true;
        }
    }

    protected void getCallPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_CALL);
            }
        }
    }

    protected boolean checkForSMSPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
//                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
                return hasSMSPermissions = false;
            } else {
                return hasSMSPermissions = true;
            }
        } else {
            return hasSMSPermissions = true;
        }
    }

    protected void getSMSPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.SEND_SMS};
                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_PERMISSIONS_SMS);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            hasAllPermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED
                    && grantResults[5] == PackageManager.PERMISSION_GRANTED
                    && grantResults[6] == PackageManager.PERMISSION_GRANTED
                    && grantResults[7] == PackageManager.PERMISSION_GRANTED
                    && grantResults[8] == PackageManager.PERMISSION_GRANTED
                    && grantResults[9] == PackageManager.PERMISSION_GRANTED;
            try {
                permissionListener.onPermissionCheckCompleted(requestCode, hasAllPermissions);
            } catch (Exception ignored) {
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_LOCATION) {
            if (grantResults.length == 2) {
                hasLocationPermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasLocationPermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_READ_WRITE) {
            if (grantResults.length == 2) {
                hasReadWritePermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasReadWritePermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_READ_CONTACTS) {
            if (grantResults.length == 1) {
                hasReadContactsPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasReadContactsPermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_GET_ACCOUNTS) {
            if (grantResults.length == 1) {
                hasGetAccountsPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasGetAccountsPermissions);
                } catch (Exception ignored) {
                }
            }
        }


        if (requestCode == REQUEST_PERMISSIONS_READ_PHONE_STATE) {
            if (grantResults.length == 1) {
                hasReadPhoneStatePermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasReadPhoneStatePermissions);
                } catch (Exception ignored) {
                }
            }
        }

        if (requestCode == REQUEST_PERMISSIONS_CALL) {
            if (grantResults.length == 1) {
                hasCallPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasCallPermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_SMS) {
            if (grantResults.length == 1) {
                hasSMSPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    permissionListener.onPermissionCheckCompleted(requestCode, hasSMSPermissions);
                } catch (Exception ignored) {
                }
            }
        }


    }

    protected boolean checkLocationSettingsStatus() {

        int locationMode = 0;
        String locationProviders;
        boolean isLocationServiceEnabled = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(App.getInstance().getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            isLocationServiceEnabled = (locationMode != Settings.Secure.LOCATION_MODE_OFF);
        } else {
            locationProviders = Settings.Secure.getString(App.getInstance().getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            isLocationServiceEnabled = !TextUtils.isEmpty(locationProviders);
        }

        if (!isLocationServiceEnabled) {
            // notify user
            if (!getActivity().isFinishing()) {
                if (!isLocationServiceEnableRequestShown) {
                    isLocationServiceEnableRequestShown = true;
                    PopupMessage popupMessage = new PopupMessage(getActivity());
                    popupMessage.show(getString(R.string.message_please_enable_location_service_from_the_settings),
                            0, getString(R.string.btn_open_settings));
                    popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
                        @Override
                        public void actionCompletedSuccessfully(boolean result) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                            isLocationServiceEnableRequestShown = false;
                        }

                        @Override
                        public void actionFailed() {
                            isLocationServiceEnableRequestShown = false;
                        }
                    });
                }
            }
        }
        return isLocationServiceEnabled;
    }
}
