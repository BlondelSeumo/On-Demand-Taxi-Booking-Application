package in.techware.lataxicustomer.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.config.TypefaceCache;
import in.techware.lataxicustomer.dialogs.PopupMessage;
import in.techware.lataxicustomer.listeners.LocationUpdateListener;
import in.techware.lataxicustomer.listeners.PermissionListener;
import in.techware.lataxicustomer.util.FileOp;
import io.fabric.sdk.android.Fabric;

/*import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;*/

public abstract class BaseActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        LocationListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

/*    private static final String TWITTER_KEY = "PD5W66s5bqIUpzUjUQpX8aoQf";
    private static final String TWITTER_SECRET = "bCll6yMGQzWZB5NopEEUpkps0KgbBYtEePOPuAztLsEuuLTfd8";*/

    protected static final int REQUEST_ENABLE_BT = 0;
    protected static final int REQUEST_PERMISSIONS = 1;
    protected static final int REQUEST_PERMISSIONS_LOCATION = 2;
    protected static final int REQUEST_PERMISSIONS_READ_CONTACTS = 3;
    protected static final int REQUEST_PERMISSIONS_READ_WRITE = 4;
    protected static final int REQUEST_PERMISSIONS_GET_ACCOUNTS = 5;
    protected static final int REQUEST_PERMISSIONS_READ_PHONE_STATE = 6;
    protected static final int REQUEST_PERMISSIONS_CALL = 7;
    protected static final int REQUEST_PERMISSIONS_SMS = 8;
    private static final String TAG = "BaseA";

    private static int CURRENT_ACTIVITY = 0;
    protected static final int HOME_ACTIVITY = 1;
    protected static final int ON_TRIP_ACTIVITY = 2;

    protected FileOp fop;

    protected boolean isBluetoothEnableRequestShown;
    protected boolean isLocationServiceEnableRequestShown;

    protected View.OnClickListener snackBarDismissOnClickListener;

    protected Resources r;
    protected static float px;
    protected int width;
    protected int height;

    protected Animation disappear;
    protected Animation slideLeftIn;
    protected Animation slideLeftOut;
    protected Animation slideRightIn;
    protected Animation slideRightOut;
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
    protected Animation pushRightOut;
    protected Typeface typeface;
    protected Typeface typefaceBold;
    protected Typeface typefaceItalic;
    protected Typeface typefaceBoldItalic;

    protected Vibrator mVibrator;
    protected float mActionBarHeight;

    protected int selectableItemBackground;


    protected boolean hasAllPermissions;
    protected boolean hasLocationPermissions;
    protected boolean hasReadWritePermissions;
    protected boolean hasReadContactsPermissions;
    protected boolean hasGetAccountsPermissions;
    protected boolean hasReadPhoneStatePermissions;
    protected boolean hasCallPermissions;
    protected boolean hasSMSPermissions;

    private GoogleApiClient mGoogleApiClient;
    protected LocationListener locationListener;
    private static final LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback locationCallback;
    private boolean isRequestingLocationUpdates;
    private ArrayList<LocationUpdateListener> locationUpdateListeners = new ArrayList<>();
    private ArrayList<PermissionListener> permissionListeners = new ArrayList<>();

    protected void initBase() {

        App.checkForToken();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //	getActionBar().setHomeButtonEnabled(true);


        if (App.getInstance().getGoogleApiClient() == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(App.getInstance().getApplicationContext())
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            App.getInstance().setGoogleApiClient(mGoogleApiClient);
        } else {
            mGoogleApiClient = App.getInstance().getGoogleApiClient();
        }


        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    setLocationUpdate(location);
                }
            }
        };

        initLocationPermissionCheck();


        /*Remove this to remove Crashlytics and Fabric*/
//        Fabric.with(this, new Crashlytics());
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics()/*, new TwitterCore(authConfig), new Digits.Builder().build()*/);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //	getActionBar().setHomeButtonEnabled(true);

        fop = new FileOp(this.getApplicationContext());

        final TypedArray mstyled = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        mActionBarHeight = mstyled.getDimension(0, 0);
        mstyled.recycle();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
/*        if (android.os.Build.VERSION.SDK_INT >= 21) {
            toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }*/

        //	llBottomBarActionPopup=(LinearLayout)findViewById(R.id.ll_bottombar_popmenu);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        selectableItemBackground = typedValue.resourceId;

        pushRightOut = AnimationUtils.loadAnimation(this, R.anim.push_right_out);
        disappear = AnimationUtils.loadAnimation(this, R.anim.disappear);
        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
        slideUpIn = AnimationUtils.loadAnimation(this, R.anim.slide_up_in);
        slideDownOut = AnimationUtils.loadAnimation(this, R.anim.slide_down_out);
        slideDownIn = AnimationUtils.loadAnimation(this, R.anim.slide_down_in);
        slideUpOut = AnimationUtils.loadAnimation(this, R.anim.slide_up_out);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeFastIn = AnimationUtils.loadAnimation(this, R.anim.fast_fade_in);
        fadeFastOut = AnimationUtils.loadAnimation(this, R.anim.fast_fade_out);
        growBottom = AnimationUtils.loadAnimation(this, R.anim.grow_from_bottom);
        shrinkBottom = AnimationUtils.loadAnimation(this, R.anim.shrink_from_top);
        growFromBottomRightToTopLeft = AnimationUtils.loadAnimation(this, R.anim.grow_from_bottomright_to_topleft);
        shrinkFromTopLeftToBottomRight = AnimationUtils.loadAnimation(this, R.anim.shrink_from_topleft_to_bottomright);


        try {
            typeface = TypefaceCache.getInstance().getTypeface("Roboto-Regular.ttf");
            typefaceBold = TypefaceCache.getInstance().getTypeface("Roboto-Bold.ttf");
            typefaceItalic = TypefaceCache.getInstance().getTypeface("Roboto-Italic.ttf");
            typefaceBoldItalic = TypefaceCache.getInstance().getTypeface("Roboto-BoldItalic.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        r = getResources();
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        width = r.getDisplayMetrics().widthPixels;
        height = r.getDisplayMetrics().heightPixels;

        snackBarDismissOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                //mVibrator.vibrate(25);
                v.setVisibility(View.GONE);

            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequestingLocationUpdates) {
            getCurrentLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    void setCurrentActivity(int currentActivity) {
        CURRENT_ACTIVITY = currentActivity;
    }

    int getCurrentActivity() {
        return CURRENT_ACTIVITY;
    }

    private void initLocationPermissionCheck() {
        if (CURRENT_ACTIVITY == HOME_ACTIVITY) {
            if (!checkForLocationPermissions()) {
                getLocationPermissions();
            } else if (checkLocationSettingsStatus()) {
                getCurrentLocation();
            }
        }
    }

    protected static void restart(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Intent restartIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent intent = PendingIntent.getActivity(
                context, 0,
                restartIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        System.exit(2);
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void performCall(String phone) {
        String url = "tel:" + phone;
        Log.i(TAG, "performCall:  PHONE : " + phone);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }

/*
    public static String getUserDateFromUnix(String time) {

        if (time.equalsIgnoreCase("-62169984000") || time.equalsIgnoreCase("false") || time.equalsIgnoreCase("true"))
            return "";
        try {
            Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calTemp.setTimeInMillis(Long.valueOf(time) * 1000);
            calTemp.setTimeZone(Calendar.getInstance().getTimeZone());
            time = new SimpleDateFormat("MMM dd, yyyy", Locale.US)
                    .format(new Date(calTemp.getTimeInMillis()));
            return time;
        } catch (Exception e) {
            //	e.printStackTrace();
            return time;
        }
    }

    public static String getUserTimeFromUnix(String date) {

        if (date.equalsIgnoreCase("-62169984000") || date.equalsIgnoreCase("false") || date.equalsIgnoreCase("true"))
            return "";
        try {
            Calendar calTemp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calTemp.setTimeInMillis(Long.valueOf(date) * 1000);
            calTemp.setTimeZone(Calendar.getInstance().getTimeZone());
            date = new SimpleDateFormat("hh:mma", Locale.US)
                    .format(new Date(calTemp.getTimeInMillis()));
            return date;
        } catch (Exception e) {
            //	e.printStackTrace();
            return date;
        }
    }
*/


    protected String getDeviceID() {
        String DEVICEID = "";
        String IMEI = "";

        try {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            IMEI = mngr.getDeviceId();

            System.out.println("IMEI : " + IMEI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DEVICEID = Build.SERIAL + IMEI;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DEVICE ID : " + DEVICEID);
        return DEVICEID;
    }


    void addPermissionListener(PermissionListener permissionListener) {
        if (permissionListeners == null)
            permissionListeners = new ArrayList<>();
        permissionListeners.add(permissionListener);
    }

    protected boolean checkForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CLEAR_APP_CACHE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
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
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);*/
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CLEAR_APP_CACHE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
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

                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            }
        }
    }

    protected boolean checkForContactsPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

               /* String[] permissions = new String[]{
                        Manifest.permission.READ_CONTACTS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_CONTACTS);*/
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[]{
                        Manifest.permission.READ_CONTACTS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_CONTACTS);
            }
        }
    }

    protected boolean checkForLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                /*String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION);*/
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION);
            }
        }
    }

    protected boolean checkForReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                /*String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_WRITE);*/
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_WRITE);
            }
        }
    }

    protected boolean checkForGetAccountsPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
              /*  String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_GET_ACCOUNTS);*/
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.GET_ACCOUNTS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_GET_ACCOUNTS);
            }
        }
    }


    protected boolean checkForReadPhoneStatePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
            }
        }
    }

    protected boolean checkForCallPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CALL);
            }
        }
    }

    protected boolean checkForSMSPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//                String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_READ_PHONE_STATE);
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
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.SEND_SMS};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_SMS);
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
                setPermissionCheckStatus(requestCode, hasAllPermissions);
            } catch (Exception ignored) {
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_LOCATION) {
            if (grantResults.length == 2) {
                hasLocationPermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasLocationPermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_READ_WRITE) {
            if (grantResults.length == 2) {
                hasReadWritePermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasReadWritePermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_READ_CONTACTS) {
            if (grantResults.length == 1) {
                hasReadContactsPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasReadContactsPermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_GET_ACCOUNTS) {
            if (grantResults.length == 1) {
                hasGetAccountsPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasGetAccountsPermissions);
                } catch (Exception ignored) {
                }
            }
        }


        if (requestCode == REQUEST_PERMISSIONS_READ_PHONE_STATE) {
            if (grantResults.length == 1) {
                hasReadPhoneStatePermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasReadPhoneStatePermissions);
                } catch (Exception ignored) {
                }
            }
        }

        if (requestCode == REQUEST_PERMISSIONS_CALL) {
            if (grantResults.length == 1) {
                hasCallPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasCallPermissions);
                } catch (Exception ignored) {
                }
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_SMS) {
            if (grantResults.length == 1) {
                hasSMSPermissions = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                try {
                    setPermissionCheckStatus(requestCode, hasSMSPermissions);
                } catch (Exception ignored) {
                }
            }
        }

    }

    private void setPermissionCheckStatus(int requestCode, boolean hasPermission) {
        for (PermissionListener permissionListener : permissionListeners) {
            permissionListener.onPermissionCheckCompleted(requestCode, hasPermission);
        }
    }

    protected boolean checkLocationSettingsStatus() {

        int locationMode = 0;
        String locationProviders;
        boolean isLocationServiceEnabled = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            isLocationServiceEnabled = (locationMode != Settings.Secure.LOCATION_MODE_OFF);
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            isLocationServiceEnabled = !TextUtils.isEmpty(locationProviders);
        }

        if (!isLocationServiceEnabled) {
            // notify user
            if (!isFinishing()) {
                if (!isLocationServiceEnableRequestShown) {
                    isLocationServiceEnableRequestShown = true;
                    PopupMessage popupMessage = new PopupMessage(this);
                    popupMessage.show(getString(R.string.message_please_enable_location_service_from_settings), 0, getString(R.string.btn_open_settings));
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

    private void setLocationUpdate(Location location) {
        if (locationUpdateListeners != null && !locationUpdateListeners.isEmpty()) {
            for (LocationUpdateListener locationUpdateListener : locationUpdateListeners) {
                locationUpdateListener.onLocationUpdated(location);
            }
        }
    }

    void addLocationUpdateListener(LocationUpdateListener listener) {
        isRequestingLocationUpdates = true;
        if (locationUpdateListeners == null) {
            locationUpdateListeners = new ArrayList<>();
        }
        locationUpdateListeners.add(listener);
    }

    void setUpLocationClientIfNeeded() {
       /* if(!checkForLocationPermissions())
            getLocationPermissions();*/


        if (App.getInstance().getGoogleApiClient() == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            //		mGoogleApiClient = new LocationClient(getApplicationContext(),this,this);
            App.getInstance().setGoogleApiClient(mGoogleApiClient);
        }
    }

    void getCurrentLocation() {
        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions())
                getLocationPermissions();
        } else {

            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

                if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                    Config.getInstance().setCurrentLatitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Config.getInstance().setCurrentLongitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
//                    getLocationName();
                }
            }

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Logic to handle location object
                                setLocationUpdate(location);
                            }
                        }
                    });

            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    locationCallback, null /* Looper */);

//            Toast.makeText(BaseAppCompatActivity.this, "Retrieving Current Location...", Toast.LENGTH_SHORT).show();
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
            //			mHandler.postDelayed(periodicTask, 3000);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            setLocationUpdate(location);
        } catch (Exception e) {
        }
        Config.getInstance().setCurrentLatitude("" + location.getLatitude());
        Config.getInstance().setCurrentLongitude("" + location.getLongitude());

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult arg0) {
    }

    @Override
    public void onConnected(Bundle arg0) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!checkForLocationPermissions())
                    getLocationPermissions();
                checkLocationSettingsStatus();
            } else {
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    // Logic to handle location object
                                    setLocationUpdate(location);
                                }
                            }
                        });

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                //	mGoogleApiClient.requestLocationUpdates(mLocationRequest,HomeActivity.this);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onConnectionSuspended(int arg0) {

    }

}
