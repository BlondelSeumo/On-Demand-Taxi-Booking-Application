package in.techware.lataxidriverapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.adapter.RiderFeedbackPagerAdapter;
import in.techware.lataxidriverapp.fragments.CommentsFragment;
import in.techware.lataxidriverapp.fragments.IssuesFragment;
import in.techware.lataxidriverapp.widgets.CustomTextView;

public class RiderFeedbackActivity extends BaseAppCompatNoDrawerActivity implements IssuesFragment.IssuesFragmentListener,
        CommentsFragment.CommentsFragmentListener {

    private ViewPager pager;
    private TabLayout tabLayout;
    private LayoutInflater inflater;
    private RiderFeedbackPagerAdapter adapterPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_feedback);

        initViews();

        getSupportActionBar().setTitle("Rider Feedback");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    private void initViews() {

        pager = (ViewPager) findViewById(R.id.pager_rider_feedback);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_rider_feedback);

        if (inflater == null)
            inflater = getLayoutInflater();


        CustomTextView customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_issues);
        customTab.setBackgroundResource(R.drawable.btn_click_app_rectangle_with_semicircle_edge);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_issues)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_comments);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_comments)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

//        tab_layout.addTab(tab_layout.newTab()/*.setText("Stream")*/.setIcon(R.drawable.ic_action_stream));


        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

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
        adapterPager = new RiderFeedbackPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterPager);

        pager.setCurrentItem(0);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
//        tabLayout.setSelectedTabIndicatorHeight((int) mActionBarHeight);
        pager.setOffscreenPageLimit(1);

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                int pos = pager.getCurrentItem();
                switch (pos) {
                    case 0:
                        ((IssuesFragment) adapterPager.getItem(0)).onRefresh();
                        break;
                    case 1:
                        ((CommentsFragment) adapterPager.getItem(1)).onRefresh();
                        break;
                }

            }
        });

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
