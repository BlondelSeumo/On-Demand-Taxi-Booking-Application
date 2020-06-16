package in.techware.lataxicustomer.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.model.AuthBean;
import in.techware.lataxicustomer.net.WSAsyncTasks.FCMRefreshTask;
import in.techware.lataxicustomer.util.AppConstants;
import in.techware.lataxicustomer.util.FileOp;
import in.techware.lataxicustomer.util.RobotoCondensedTextStyleExtractor;
import in.techware.lataxicustomer.util.TypefaceManager;

public class App extends Application {

    public static final int SERVER_CONNECTION_AVAILABLE = 0;
    public static final int NETWORK_NOT_AVAILABLE = 1;
    public static final int AUTH_TOKEN_NOT_AVAILABLE = 2;
    private static final String TAG = "App";


    private final Thread.UncaughtExceptionHandler defaultHandler;

    private static App instance;

    private FileOp fop = new FileOp(this);

    Resources r;
    float px;
    int width;
    int height;


    private boolean isDemo;

    public boolean isDemo() {
        return isDemo;
    }

    public void setDemo(boolean demo) {
        isDemo = demo;
    }

    private GoogleApiClient googleApiClient;

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }


    public float getPx() {
        return px;
    }

    public void setPx(float px) {
        this.px = px;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public App() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // setup handler for uncaught exception
        Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {

                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>IN EXCEPTION HANDLER>>>>>>>>>>>>>>>>>>>>>");
                final Writer result = new StringWriter();
                final PrintWriter printWriter = new PrintWriter(result);
                ex.printStackTrace(printWriter);
                printWriter.close();

                ex.printStackTrace();
                Toast.makeText(getApplicationContext(), "LaTaxi Has Crashed Due To An Error."
                        + "\nApplication Will Restart Now."
                        + "\nSorry For Any Inconvinience Caused", Toast.LENGTH_SHORT).show();
                //	    			restart(getApplicationContext(), 2000);
                //	check(getApplicationContext(), 1);
                fop = new FileOp(getApplicationContext());
                fop.writeDebug(result.toString());
                checkForToken();

                //	android.os.Process.killProcess(android.os.Process.myPid());
                defaultHandler.uncaughtException(thread, ex);
            }
        };
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);

    }

    public static App getInstance() {
        if (instance == null)
            instance = new App();
        return instance;
    }

    public static void restart(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Intent restartIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent intent = PendingIntent.getActivity(
                context, 0,
                restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        System.exit(2);
    }

    public static void check(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        System.exit(0);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

//        FacebookSdk.sdkInitialize(this.getApplicationContext());

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        checkForToken();

        TypefaceManager.addTextStyleExtractor(RobotoCondensedTextStyleExtractor.getInstance());
        setDefaultFont();

        r = getResources();
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        width = r.getDisplayMetrics().widthPixels;
        height = r.getDisplayMetrics().heightPixels;


    }

    private void setDefaultFont() {

        try {
            /*			final Typeface bold = Typeface.createFromAsset(getAssets(), "HelveticaNeueBold.ttf");
            final Typeface italic = Typeface.createFromAsset(getAssets(), "HelveticaNeueItalic.ttf");
			final Typeface boldItalic = Typeface.createFromAsset(getAssets(), "HelveticaNeueBoldItalic.ttf");
			final Typeface regular = Typeface.createFromAsset(getAssets(),"HelveticaNeue.ttf");

			final Typeface normal = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
			final Typeface monospace = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
			final Typeface serif = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
			final Typeface sansSerif = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
			final Typeface sans = Typeface.createFromAsset(getAssets(), "HelveticaNeue.ttf");
			 */

            final Typeface bold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
            final Typeface italic = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
            final Typeface boldItalic = Typeface.createFromAsset(getAssets(), "Roboto-BoldItalic.ttf");
            final Typeface regular = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");

            final Typeface normal = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            final Typeface monospace = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
            final Typeface serif = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            final Typeface sansSerif = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
            final Typeface sans = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");

            Field DEFAULT = Typeface.class.getDeclaredField("DEFAULT");
            DEFAULT.setAccessible(true);
            DEFAULT.set(null, regular);

            Field DEFAULT_BOLD = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            DEFAULT_BOLD.setAccessible(true);
            DEFAULT_BOLD.set(null, bold);

            Field SERIF = Typeface.class.getDeclaredField("SERIF");
            SERIF.setAccessible(true);
            SERIF.set(null, serif);

            Field SANS_SERIF = Typeface.class.getDeclaredField("SANS_SERIF");
            SANS_SERIF.setAccessible(true);
            SANS_SERIF.set(null, sansSerif);

            Field SANS = Typeface.class.getDeclaredField("SANS");
            SANS.setAccessible(true);
            SANS.set(null, sans);

            Field NORMAL = Typeface.class.getDeclaredField("NORMAL");
            NORMAL.setAccessible(true);
            NORMAL.set(null, normal);

            Field MONOSPACE = Typeface.class.getDeclaredField("MONOSPACE");
            MONOSPACE.setAccessible(true);
            MONOSPACE.set(null, monospace);

            Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{
                    regular, bold, italic, boldItalic, monospace, serif, sansSerif, normal, sans
            });

        } catch (Throwable e) {
            //cannot crash app if there is a failure with overriding the default font!
            System.out.println(e);
        }
    }

    public static Locale getCurrentLocale() {
        return new Locale(Config.getInstance().getLocale());
    }

    public static Locale getLocale(String lang) {
        return new Locale(lang);
    }

    public static void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = App.getInstance().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    public static String getImagePath(String path) {
        return !path.startsWith("http") ? AppConstants.BASE_URL + path : path;
    }

    public static boolean isNetworkAvailable() {
        Context context = getInstance().getApplicationContext();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // A method to find height of the status bar
    public static int getStatusBarHeight() {
        Context context = getInstance().getApplicationContext();
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

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

    public static String getDeviceID(Context context) {
        String DEVICEID = "";
        String IMEI = "";

        try {
            TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
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
        System.out.println("DEVICE id : " + DEVICEID);
        return DEVICEID;
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

    public static boolean checkForToken() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);

        Log.i(TAG, "checkForToken: SESSION : " + prfs.getAll());

        String token = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_TOKEN, "");
        String email = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_EMAIL, "");
        String userID = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_USERID, "");
        String name = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_NAME, "");
        String firstName = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_FIRSTNAME, "");
        String lastName = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_LASTNAME, "");
        String phone = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, "");
        String password = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_PASSWORD, "");
        String gender = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_GENDER, "");
        String DOB = prfs.getString(AppConstants.PREFERENCE_KEY_SESSION_DOB, "");
        boolean isFirstTime = prfs.getBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_FIRST_TIME, true);
        boolean isPhoneVerified = prfs.getBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, false);
      /*  boolean isEmailVerified = prfs.getBoolean(AppConstants.PREFERENCE_IS_EMAIL_VERIFIED, false);
        boolean isRegistrationCompleted = prfs.getBoolean(AppConstants.PREFERENCE_IS_REGISTRATION_COMPLETED, false);
*/
        if (!"".equals(token)) {
            Config.getInstance().setAuthToken(token);
            Config.getInstance().setUserID(userID);
            Config.getInstance().setEmail(email);
            Config.getInstance().setName(name);
            Config.getInstance().setFirstName(firstName);
            Config.getInstance().setLastName(lastName);
            Config.getInstance().setPassword(password);
            Config.getInstance().setPhone(phone);
            Config.getInstance().setGender(gender);
            Config.getInstance().setDOB(DOB);
            Config.getInstance().setFirstTime(isFirstTime);
            Config.getInstance().setPhoneVerified(isPhoneVerified);
            if (Config.getInstance().getCurrentLatitude() == null) {
                Config.getInstance().setCurrentLatitude("");
                Config.getInstance().setCurrentLongitude("");
            }
            return true;
        } else {
            if (Config.getInstance().getCurrentLatitude() == null) {
                Config.getInstance().setCurrentLatitude("");
                Config.getInstance().setCurrentLongitude("");
            }
            return false;
        }
    }

    public static void saveToken() {
        Context context = getInstance().getApplicationContext();

        System.out.println("SAVE STARTED");
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_TOKEN, Config.getInstance().getAuthToken());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_USERID, Config.getInstance().getUserID());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_EMAIL, Config.getInstance().getEmail());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_FIRSTNAME, Config.getInstance().getFirstName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_LASTNAME, Config.getInstance().getLastName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_NAME, Config.getInstance().getName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, Config.getInstance().getPhone());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_GENDER, Config.getInstance().getGender());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_DOB, Config.getInstance().getDOB());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_GCM_ID, Config.getInstance().getGCMID());
//        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_FIRST_TIME, Config.getInstance().isFirstTime());
        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, Config.getInstance().isPhoneVerified());
        editor.commit();
        FileOp fileOp = new FileOp(context);
        fileOp.writeHash();
        System.out.println("SAVE COMPLETE");
    }

    public static void saveToken(AuthBean authBean) {
        Context context = getInstance().getApplicationContext();

        setConfig(authBean);

        System.out.println("SAVE STARTED");
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_TOKEN, authBean.getAuthToken());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_USERID, authBean.getUserID());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_EMAIL, authBean.getEmail());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_FIRSTNAME, authBean.getFirstName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_LASTNAME, authBean.getLastName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_NAME, authBean.getName());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_PHONE, authBean.getPhone());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_GENDER, authBean.getGender());
        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_DOB, authBean.getDOB());
//        editor.putString(AppConstants.PREFERENCE_KEY_SESSION_GCM_ID, Config.getInstance().getGCMID());
//        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_FIRST_TIME, false);
        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, authBean.isPhoneVerified());
//        editor.putBoolean(AppConstants.PREFERENCE_KEY_SESSION_IS_PHONE_VERIFIED, true);
        editor.commit();
        FileOp fileOp = new FileOp(context);
        fileOp.writeHash();
        System.out.println("SAVE COMPLETE");
    }

    private static void setConfig(AuthBean authBean) {
        Config.getInstance().setAuthToken(authBean.getAuthToken());
        Config.getInstance().setUserID(authBean.getUserID());
        Config.getInstance().setEmail(authBean.getEmail());
        Config.getInstance().setName(authBean.getName());
        Config.getInstance().setFirstName(authBean.getFirstName());
        Config.getInstance().setLastName(authBean.getLastName());
//            Config.getInstance().setUsername(username);
        Config.getInstance().setPhone(authBean.getPhone());
        Config.getInstance().setGender(authBean.getGender());
        Config.getInstance().setDOB(authBean.getDOB());
        Config.getInstance().setPhoneVerified(authBean.isPhoneVerified());
//            Config.getInstance().setEmailVerified(isEmailVerified);
//            Config.getInstance().setRegistrationCompleted(isRegistrationCompleted);
//        Config.getInstance().setFirstTime(false);
        if (Config.getInstance().getCurrentLatitude() == null) {
            Config.getInstance().setCurrentLatitude("");
            Config.getInstance().setCurrentLongitude("");
        }

    }

    public static void logout() {
        Context context = getInstance().getApplicationContext();
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.PREFERENCE_NAME_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        //editor.remove(AppConstants.PREFERENCE_KEY_SESSION_TOKEN);
        editor.apply();

//        Digits.logout();
        FCMRefreshTask fcmRefreshTask = new FCMRefreshTask();
        fcmRefreshTask.execute();
        FirebaseAuth.getInstance().signOut();
        clearApplicationData();

        //        restart(context, 500);

    }

    public static void clearApplicationData() {
        Context context = getInstance().getApplicationContext();
        File cache = context.getFilesDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                //		if (!s.equals("lib")) {
                (new File(appDir, s)).delete();
                //		}
            }
        }
    }


}
