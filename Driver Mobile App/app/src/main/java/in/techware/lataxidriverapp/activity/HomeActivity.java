package in.techware.lataxidriverapp.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.adapter.HomePagerAdapter;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.fragments.AccountsFragment;
import in.techware.lataxidriverapp.fragments.EarningsFragment;
import in.techware.lataxidriverapp.fragments.HomeFragment;
import in.techware.lataxidriverapp.fragments.RatingsFragment;
import in.techware.lataxidriverapp.listeners.BasicListener;
import in.techware.lataxidriverapp.listeners.LocationUpdateListener;
import in.techware.lataxidriverapp.listeners.PermissionListener;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.net.WSAsyncTasks.FCMRegistrationTask;
import in.techware.lataxidriverapp.util.AppConstants;
import in.techware.lataxidriverapp.util.FileOp;
import in.techware.lataxidriverapp.widgets.CustomTextView;

public class HomeActivity extends BaseAppCompatActivity implements HomeFragment.HomeFragmentListener,
        EarningsFragment.EarningsFragmentListener, RatingsFragment.RatingsFragmentListener,
        AccountsFragment.AccountsFragmentListener {

    private FileOp fop = new FileOp(this);

    private static final String TAG = "HomeA";

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_EARNING = 1;
    private static final int FRAGMENT_RATING = 2;
    private static final int FRAGMENT_ACCOUNTS = 3;
    private ViewPager pager;
    private TabLayout tabLayout;
    private LayoutInflater inflater;
    private HomePagerAdapter adapterPager;
    private MenuItem menuOnlineSwitchItem;
    private SwitchCompat switchOnline;
    private CustomTextView customTab;
    private String requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        }

        initViews();
        initFCM();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.label_offline);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (switchOnline != null) {
            ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(switchOnline.isChecked());

            if (Config.getInstance().isOnline()) {
                switchOnline.setChecked(true);
            } else {
                switchOnline.setChecked(false);
            }
        }
        onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
/*        menuProgress = menu.findItem(R.id.action_progress);
        // Extract the action-view from the menu item
        ProgressBar progressActionBar = (ProgressBar) MenuItemCompat.getActionView(menuProgress);
        progressActionBar.setIndeterminate(true);*/
        menuOnlineSwitchItem = menu.findItem(R.id.action_online_offline_switch);
//        MenuItemCompat.setActionView(menuOnlineSwitchItem, R.layout.custom_action_online);
        switchOnline = (SwitchCompat) MenuItemCompat.getActionView(menuOnlineSwitchItem);
        switchOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                setDriverTitle();
                if (App.isNetworkAvailable()) {
                    performDriverStatusChange();
                } else {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                if (drawerLayout.isDrawerOpen(leftDrawer))
                    drawerLayout.closeDrawer(leftDrawer);
            /*			else if(drawerLayout.isDrawerOpen(rightDrawer))
                drawerLayout.closeDrawer(rightDrawer);*/
                else if (!drawerLayout.isDrawerOpen(leftDrawer))
                    drawerLayout.openDrawer(leftDrawer);
                return true;
           /* case R.id.action_search:
                drawerLayout.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);

                startActivity(new Intent(this, SearchActivity.class));
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    private void onRefresh() {
        if (App.isNetworkAvailable()) {
            fetchDriverStatus();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    private void initViews() {

        pager = (ViewPager) findViewById(R.id.pager_home);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_home);

        if (inflater == null)
            inflater = getLayoutInflater();

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_home);
        customTab.setBackgroundResource(R.drawable.btn_click_app_rectangle_with_semicircle_edge);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_home)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_earnings);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_earnings)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_ratings);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_ratings)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_accounts);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_accounts)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));
//        tab_layout.addTab(tab_layout.newTab()/*.setText("Stream")*/.setIcon(R.drawable.ic_action_stream));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                try {
                    tab.getCustomView().setBackgroundResource(R.drawable.btn_click_app_rectangle_with_semicircle_edge);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*try {
                    ((ActivityBase) getActivity()).getSupportActionBar().setTitle(getTabTitle(tab.getPosition()));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    tab.getCustomView().setBackgroundResource(selectableItemBackground);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               /* try {
                    tab.getCustomView().setBackgroundResource(R.drawable.btn_click_shadow_white_with_semicircle_edge);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }

        });
        adapterPager = new HomePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterPager);

        pager.setCurrentItem(0);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
//        tabLayout.setSelectedTabIndicatorHeight((int) mActionBarHeight);
        pager.setOffscreenPageLimit(4);

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                int pos = pager.getCurrentItem();
                switch (pos) {
                    case 0:
                        ((HomeFragment) adapterPager.getItem(0)).onRefresh();
                        break;
                    case 1:
                        ((EarningsFragment) adapterPager.getItem(1)).onRefresh();
                        break;
                    case 2:
                        ((RatingsFragment) adapterPager.getItem(2)).onRefresh();
                        break;

                }

            }
        });

        LocationUpdateListener locationUpdateListener = new LocationUpdateListener() {
            @Override
            public void onLocationUpdated(Location location) {
                ((HomeFragment) adapterPager.getItem(0)).onLocationUpdated(location);
            }
        };
        addLocationUpdateListener(locationUpdateListener);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionCheckCompleted(int requestCode, boolean isPermissionGranted) {
                if (requestCode == REQUEST_PERMISSIONS_LOCATION) {
                    if (isPermissionGranted) {
                        if (checkLocationSettingsStatus()) {
                            getCurrentLocation();
                        }
                    }
                }
            }
        };
        addPermissionListener(permissionListener);

    }


    private void initFCM() {

        FCMRegistrationTask fcmRegistrationTask = new FCMRegistrationTask();
        fcmRegistrationTask.setFCMRegistrationTaskListener(new FCMRegistrationTask.FCMRegistrationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(String fcmToken) {

                Log.i(TAG, "dataDownloadedSuccessfully: FCM TOKEN : " + fcmToken);

                JSONObject postData = getUpdateFCMTokenJSObj(fcmToken);

                DataManager.performUpdateFCMToken(postData, new BasicListener() {
                    @Override
                    public void onLoadCompleted(BasicBean basicBean) {

                    }

                    @Override
                    public void onLoadFailed(String error) {

                    }
                });


            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        fcmRegistrationTask.execute();

    }

    private JSONObject getUpdateFCMTokenJSObj(String fcmToken) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("fcm_token", fcmToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void fetchDriverStatus() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchDriverStatus(urlParams, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                setDriverStatus(basicBean.isDriverOnline());
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });

    }

    public void setDriverStatus(boolean isOnline) {
        Config.getInstance().setOnline(isOnline);
        if (switchOnline != null) {
            switchOnline.setChecked(isOnline);
        }
        setDriverTitle();
    }

    private void performDriverStatusChange() {

        swipeView.setRefreshing(true);
        JSONObject postData = getDriverStatusChangeJSObj();

        DataManager.performDriverStatusChange(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                setDriverTitle();
                if (switchOnline.isChecked()) {

                    Snackbar.make(coordinatorLayout, R.string.message_you_are_online, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                } else {
                    Snackbar.make(coordinatorLayout, R.string.message_you_are_offline_now, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
                ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(switchOnline.isChecked());
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                switchOnline.setChecked(!switchOnline.isChecked());
                setDriverTitle();
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                if (App.getInstance().isDemo()) {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    setDriverTitle();
                    ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(switchOnline.isChecked());
                }
            }
        });

    }

    private JSONObject getDriverStatusChangeJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("driver_status", switchOnline.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void setDriverTitle() {
        if (switchOnline != null) {
            if (switchOnline.isChecked()) {
                Config.getInstance().setOnline(true);
                getSupportActionBar().setTitle(R.string.label_online);
            } else {
                Config.getInstance().setOnline(false);
                getSupportActionBar().setTitle(R.string.label_offline);
            }
        }
    }


    @Override
    public void initiateLocationUpdates() {
        if (checkForLocationPermissions()) {
            if (checkLocationSettingsStatus()) {
                getCurrentLocation();
            }
        } else {
            getLocationPermissions();
        }
    }

    @Override
    public void onSwipeRefreshChange(boolean isRefreshing) {
        swipeView.setRefreshing(isRefreshing);
    }

    @Override
    public void onSwipeEnabled(boolean isEnabled) {
        swipeView.setEnabled(isEnabled);

    }
}
