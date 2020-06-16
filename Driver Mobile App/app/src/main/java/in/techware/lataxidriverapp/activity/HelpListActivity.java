package in.techware.lataxidriverapp.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.adapter.HelpListRecyclerAdapter;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.HelpListListener;
import in.techware.lataxidriverapp.model.HelpBean;
import in.techware.lataxidriverapp.model.HelpListBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class HelpListActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "HelpLA";
    private View.OnClickListener snackBarRefreshOnClickListener;
    private RecyclerView rvHelpList;
    private LinearLayoutManager linearLayoutManager;
    private HelpListBean helpListBean;
    private HelpListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_list);

        initViews();

        getSupportActionBar().setTitle(R.string.label_help);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (helpListBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }

    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchHelpList(false, 0);
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
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


        rvHelpList = (RecyclerView) findViewById(R.id.rv_help_list);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHelpList.setLayoutManager(linearLayoutManager);
        rvHelpList.addItemDecoration(
                new DividerItemDecoration(App.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL));


        rvHelpList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int ydy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    try {
                        Log.i(TAG, "Scroll Up : " + ViewCompat.canScrollVertically(recyclerView, -1));
                        if (!ViewCompat.canScrollVertically(recyclerView, -1)) {
                            Log.i(TAG, "SwipteViewEnabled : true");
                            swipeView.setEnabled(true);
                        } else {
                            Log.i(TAG, "SwipteViewEnabled : false");
                            swipeView.setEnabled(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

               /* int offset = dy - ydy;
                ydy = dy;
                boolean shouldRefresh = (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
                        && (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) && offset > 30;
                if (shouldRefresh) {
                    //swipeRefreshLayout.setRefreshing(true);
                    //Refresh to load data here.
                    swipeView.setEnabled(true);
                    return;
                } else {
                    swipeView.setEnabled(false);
                }
                boolean shouldPullUpRefresh = linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getChildCount() - 1
                        && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && offset < -30;
                if (shouldPullUpRefresh) {
                    //swipeRefreshLayout.setRefreshing(true);
                    //refresh to load data here.
                    return;
                }
                swipeView.setEnabled(false);*/
            }
        });
    }

    private void fetchHelpList(final boolean isLoadMore, int currentPage) {

        HashMap<String, String> urlParams = new HashMap<>();

/*        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }*/

        DataManager.fetchHelpList(urlParams, new HelpListListener() {
            @Override
            public void onLoadCompleted(HelpListBean helpListBeanWS) {

                setHelpListBean(helpListBeanWS, isLoadMore);
                populateHelpList();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                if (App.getInstance().isDemo()) {
                    helpListBean = new HelpListBean();
                    helpListBean.setHelpList(new ArrayList<HelpBean>());
                    populateHelpList();
                }
            }
        });

    }

    private void populateHelpList() {

        if (adapter == null) {
            adapter = new HelpListRecyclerAdapter(this, helpListBean);
            adapter.setHelpListRecyclerAdapterListener(new HelpListRecyclerAdapter.HelpListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    fetchHelpList(isLoadMore, currentPageNumber + 1);
                }

                @Override
                public void onRefresh() {
                    fetchHelpList(false, 0);
                }

                @Override
                public void onSwipeRefreshingChange(boolean isSwipeRefreshing) {
                    swipeView.setRefreshing(isSwipeRefreshing);
                }

                @Override
                public void onSnackBarShow(String message) {
                    Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            });
            rvHelpList.setAdapter(adapter);
        } else {
            adapter.setLoadMore(false);
            adapter.setHelpListBean(helpListBean);
            adapter.notifyDataSetChanged();
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    private void setHelpListBean(HelpListBean helpListBeanWS, boolean isLoadMore) {

        if (isLoadMore && helpListBean != null && helpListBean.getHelpList() != null) {

            ArrayList<HelpBean> listExisting = helpListBean.getHelpList();
            ArrayList<HelpBean> listFromWS = helpListBeanWS.getHelpList();

            listExisting.addAll(listFromWS);
            helpListBean = helpListBeanWS;
            helpListBean.setHelpList(listExisting);
        } else {
            helpListBean = helpListBeanWS;
        }

    }
}
