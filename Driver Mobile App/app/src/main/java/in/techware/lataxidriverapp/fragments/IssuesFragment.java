package in.techware.lataxidriverapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.adapter.IssueListRecyclerAdapter;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.IssueListListener;
import in.techware.lataxidriverapp.model.IssueBean;
import in.techware.lataxidriverapp.model.IssueListBean;
import in.techware.lataxidriverapp.model.PaginationBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class IssuesFragment extends BaseFragment {

    private static final String TAG = "IssuesFrag";
    private IssuesFragmentListener mListener;
    private IssueListBean issueListBean;
    private RecyclerView rvIssues;
    private LinearLayoutManager linearLayoutManager;
    private IssueListRecyclerAdapter adapter;
    private View.OnClickListener snackBarRefreshOnClickListener;

    public IssuesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_issues, null);
        lytContent.addView(rootView);


        intiView(rootView);

        return lytBase;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    public void onRefresh() {

        if (issueListBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }

    }

    private void getData(boolean isSwipeRefreshing) {
        if (getActivity() != null)
            mListener.onSwipeRefreshChange(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchIssues(false, 0);
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void intiView(View rootView) {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };


        rvIssues = (RecyclerView) rootView.findViewById(R.id.rv_issues);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvIssues.setLayoutManager(linearLayoutManager);
        rvIssues.addItemDecoration(
                new DividerItemDecoration(App.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL));

        rvIssues.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int ydy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    try {
                        Log.i(TAG, "Scroll Up : " + ViewCompat.canScrollVertically(recyclerView, -1));
                        if (!ViewCompat.canScrollVertically(recyclerView, -1)) {
                            Log.i(TAG, "SwipteViewEnabled : true");
                            mListener.onSwipeEnabled(true);
                        } else {
                            Log.i(TAG, "SwipteViewEnabled : false");
                            mListener.onSwipeEnabled(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
/*
                int offset = dy - ydy;
                ydy = dy;
                boolean shouldRefresh = (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
                        && (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) && offset > 30;
                if (shouldRefresh) {
                    //swipeRefreshLayout.setRefreshing(true);
                    //Refresh to load data here.
                    mListener.onSwipeEnabled(true);
                    return;
                } else {
                    mListener.onSwipeEnabled(false);
                }
                boolean shouldPullUpRefresh = linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getChildCount() - 1
                        && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING && offset < -30;
                if (shouldPullUpRefresh) {
                    //swipeRefreshLayout.setRefreshing(true);
                    //refresh to load data here.
                    return;
                }
                mListener.onSwipeEnabled(false);*/
            }
        });
    }


    private void fetchIssues(final boolean isLoadMore, int currentPage) {

        HashMap<String, String> urlParams = new HashMap<>();

        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }
        DataManager.fetchIssueList(urlParams, new IssueListListener() {

            @Override
            public void onLoadCompleted(IssueListBean issueListBean) {

                setIssueListBean(issueListBean, isLoadMore);
                populateIssueList();
            }

            @Override
            public void onLoadFailed(String error) {
                if (getActivity() != null) {
                    mListener.onSwipeRefreshChange(false);
                    mListener.onSwipeEnabled(true);
                }
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                setProgressScreenVisibility(false, false);
                if (App.getInstance().isDemo()) {
                    issueListBean = new IssueListBean();
                    ArrayList<IssueBean> list = new ArrayList<>();
                    issueListBean.setIssues(list);
                    PaginationBean paginationBean = new PaginationBean();
                    paginationBean.setCount(0);
                    paginationBean.setCurrentPage(0);
                    paginationBean.setPerPage(20);
                    paginationBean.setTotalCount(0);
                    paginationBean.setTotalPages(0);
                    issueListBean.setPagination(paginationBean);

                    populateIssueList();
                }
            }
        });


    }

    private void populateIssueList() {

        if (adapter == null) {
            adapter = new IssueListRecyclerAdapter(getActivity(), issueListBean);
            adapter.setIssueListRecyclerAdapterListener(new IssueListRecyclerAdapter.IssueListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    fetchIssues(isLoadMore, currentPageNumber + 1);
                }

                @Override
                public void onRefresh() {
                    fetchIssues(false, 0);
                }

                @Override
                public void onSwipeRefreshingChange(boolean isSwipeRefreshing) {
                    if (getActivity() != null)
                        mListener.onSwipeRefreshChange(isSwipeRefreshing);

                }

                @Override
                public void onSnackBarShow(String message) {
                    Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            });
            rvIssues.setAdapter(adapter);
        } else {
            adapter.setLoadMore(false);
            adapter.setIssueListBean(issueListBean);
            adapter.notifyDataSetChanged();
        }

        if (issueListBean != null && issueListBean.getIssues() != null && !issueListBean.getIssues().isEmpty()) {
            rvIssues.setVisibility(View.VISIBLE);
//            setMessageScreenVisibility(false, false, true, 0, "");
        } else {
            rvIssues.setVisibility(View.GONE);
/*            setMessageScreenVisibility(true, false, false, 0,
                    getString(R.string.label_no_trips_taken));*/
        }

        if (getActivity() != null) {
            mListener.onSwipeRefreshChange(false);
        }
        setProgressScreenVisibility(false, false);
    }

    private void setIssueListBean(IssueListBean issueListBeanWS, boolean isLoadMore) {

        if (isLoadMore && issueListBean != null && issueListBean.getIssues() != null) {

            ArrayList<IssueBean> listExisting = issueListBean.getIssues();
            ArrayList<IssueBean> listFromWS = issueListBeanWS.getIssues();

            listExisting.addAll(listFromWS);
            issueListBean = issueListBeanWS;
            issueListBean.setIssues(listExisting);
        } else {
            issueListBean = issueListBeanWS;
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setListener(activity);
        }
    }

    private void setListener(Context context) {
        if (getActivity() instanceof IssuesFragmentListener) {
            mListener = (IssuesFragmentListener) getActivity();
        } else if (getParentFragment() instanceof IssuesFragmentListener) {
            mListener = (IssuesFragmentListener) getParentFragment();
        } else if (context instanceof IssuesFragmentListener) {
            mListener = (IssuesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IssuesFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IssuesFragmentListener) {
            mListener = (IssuesFragmentListener) getActivity();
        } else if (getParentFragment() instanceof IssuesFragmentListener) {
            mListener = (IssuesFragmentListener) getParentFragment();
        } else if (context instanceof IssuesFragmentListener) {
            mListener = (IssuesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IssuesFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface IssuesFragmentListener {
        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);

    }
}
