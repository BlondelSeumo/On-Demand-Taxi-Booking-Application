package in.techware.lataxidriverapp.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.adapter.TripListRecyclerAdapter;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.TripListListener;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.model.TripListBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class TripHistoryActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "TripHAct";
    private View.OnClickListener snackBarRefreshOnClickListener;
    private RecyclerView rvTripList;
    private LinearLayoutManager linearLayoutManager;
    private TripListBean tripListBean;
    private TripListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        initViews();

        getSupportActionBar().setTitle(R.string.title_trip_history);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tripListBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }

    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchTripList(false, 0);
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


        rvTripList = (RecyclerView) findViewById(R.id.rv_trip_history);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTripList.setLayoutManager(linearLayoutManager);
        rvTripList.addItemDecoration(
                new DividerItemDecoration(App.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL));


        rvTripList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });


    }

    private void fetchTripList(final boolean isLoadMore, int currentPage) {

        HashMap<String, String> urlParams = new HashMap<>();

        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }

        DataManager.fetchTripHistory(urlParams, new TripListListener() {
            @Override
            public void onLoadCompleted(TripListBean tripListBeanWS) {

                setTripListBean(tripListBeanWS, isLoadMore);
                populateTripList();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                if (App.getInstance().isDemo()) {
                    tripListBean = new TripListBean();
                    tripListBean.setTrips(new ArrayList<TripBean>());
                    tripListBean.setTotalFare(AppConstants.INR + " 00");
                    tripListBean.setTotalRidesTaken(0);
                    tripListBean.setTotalTimeOnline("2:30 Hours Online");
                    populateTripList();
                }
            }
        });

    }

    private void populateTripList() {

        if (adapter == null) {
            adapter = new TripListRecyclerAdapter(this, tripListBean);
            adapter.setTripListRecyclerAdapterListener(new TripListRecyclerAdapter.TripListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    swipeView.setRefreshing(true);
                    fetchTripList(isLoadMore, currentPageNumber);
                }

                @Override
                public void onItemSelected(TripBean bean) {

                }

                @Override
                public void onRefresh() {
                    fetchTripList(false, 0);
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
            rvTripList.setAdapter(adapter);
        } else {
            adapter.setLoadMore(false);
            adapter.setTripListBean(tripListBean);
            adapter.notifyDataSetChanged();
        }

        if (tripListBean != null && tripListBean.getTrips() != null && !tripListBean.getTrips().isEmpty()) {
            rvTripList.setVisibility(View.VISIBLE);
            setMessageScreenVisibility(false, false, true, 0, "");
        } else {
            rvTripList.setVisibility(View.GONE);
            setMessageScreenVisibility(true, false, false, 0,
                    getString(R.string.label_no_trips_taken));
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }

    private void setTripListBean(TripListBean tripListBeanWS, boolean isLoadMore) {

        if (isLoadMore && tripListBean != null && tripListBean.getTrips() != null) {

            ArrayList<TripBean> listExisting = tripListBean.getTrips();
            ArrayList<TripBean> listFromWS = tripListBeanWS.getTrips();

            listExisting.addAll(listFromWS);
            tripListBean = tripListBeanWS;
            tripListBean.setTrips(listExisting);
        } else {
            tripListBean = tripListBeanWS;
        }

    }
}
