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
import in.techware.lataxidriverapp.adapter.CommentListRecyclerAdapter;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.CommentListListener;
import in.techware.lataxidriverapp.model.CommentBean;
import in.techware.lataxidriverapp.model.CommentListBean;
import in.techware.lataxidriverapp.model.PaginationBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;

public class CommentsFragment extends BaseFragment {

    private static final String TAG = "CommentsFrag";
    private CommentsFragmentListener mListener;
    private RecyclerView rvComments;
    private LinearLayoutManager linearLayoutManager;
    private CommentListBean commentListBean;
    private CommentListRecyclerAdapter adapter;
    private View.OnClickListener snackBarRefreshOnClickListener;

    public CommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_comments, null);
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

        if (commentListBean == null) {
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
            fetchComments(false, 0);
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

        rvComments = (RecyclerView) rootView.findViewById(R.id.rv_comments);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.addItemDecoration(
                new DividerItemDecoration(App.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL));

        rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


    private void fetchComments(final boolean isLoadMore, int currentPage) {

        HashMap<String, String> urlParams = new HashMap<>();

        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }
        DataManager.fetchCommentList(urlParams, new CommentListListener() {

            @Override
            public void onLoadCompleted(CommentListBean commentListBean) {

                setCommentListBean(commentListBean, isLoadMore);
                populateCommentList();
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
                    commentListBean = new CommentListBean();
                    ArrayList<CommentBean> list = new ArrayList<>();
                    commentListBean.setComments(list);
                    PaginationBean paginationBean = new PaginationBean();
                    paginationBean.setCount(0);
                    paginationBean.setCurrentPage(0);
                    paginationBean.setPerPage(20);
                    paginationBean.setTotalCount(0);
                    paginationBean.setTotalPages(0);
                    commentListBean.setPagination(paginationBean);

                    populateCommentList();
                }
            }
        });


    }

    private void populateCommentList() {

        if (adapter == null) {
            adapter = new CommentListRecyclerAdapter(getActivity(), commentListBean);
            adapter.setCommentListRecyclerAdapterListener(new CommentListRecyclerAdapter.CommentListRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    fetchComments(isLoadMore, currentPageNumber + 1);
                }

                @Override
                public void onRefresh() {
                    fetchComments(false, 0);
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
            rvComments.setAdapter(adapter);
        } else {
            adapter.setLoadMore(false);
            adapter.setCommentListBean(commentListBean);
            adapter.notifyDataSetChanged();
        }

        if (commentListBean != null && commentListBean.getComments() != null && !commentListBean.getComments().isEmpty()) {
            rvComments.setVisibility(View.VISIBLE);
//            setMessageScreenVisibility(false, false, true, 0, "");
        } else {
            rvComments.setVisibility(View.GONE);
/*            setMessageScreenVisibility(true, false, false, 0,
                    getString(R.string.label_no_trips_taken));*/
        }

        if (getActivity() != null) {
            mListener.onSwipeRefreshChange(false);
        }
        setProgressScreenVisibility(false, false);
    }

    private void setCommentListBean(CommentListBean commentListBeanWS, boolean isLoadMore) {

        if (isLoadMore && commentListBean != null && commentListBean.getComments() != null) {

            ArrayList<CommentBean> listExisting = commentListBean.getComments();
            ArrayList<CommentBean> listFromWS = commentListBeanWS.getComments();

            listExisting.addAll(listFromWS);
            commentListBean = commentListBeanWS;
            commentListBean.setComments(listExisting);
        } else {
            commentListBean = commentListBeanWS;
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
        if (getActivity() instanceof CommentsFragmentListener) {
            mListener = (CommentsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof CommentsFragmentListener) {
            mListener = (CommentsFragmentListener) getParentFragment();
        } else if (context instanceof CommentsFragmentListener) {
            mListener = (CommentsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CommentsFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof CommentsFragmentListener) {
            mListener = (CommentsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof CommentsFragmentListener) {
            mListener = (CommentsFragmentListener) getParentFragment();
        } else if (context instanceof CommentsFragmentListener) {
            mListener = (CommentsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CommentsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CommentsFragmentListener {
        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);
    }
}
