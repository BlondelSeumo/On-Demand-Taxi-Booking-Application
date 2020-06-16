package in.techware.lataxidriverapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.HashMap;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.activity.ProTipsActivity;
import in.techware.lataxidriverapp.activity.RiderFeedbackActivity;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.RatingDetailsListener;
import in.techware.lataxidriverapp.model.RatingDetailsBean;
import in.techware.lataxidriverapp.net.DataManager;
import in.techware.lataxidriverapp.util.AppConstants;


public class RatingsFragment extends BaseFragment {

    private static final String TAG = "RatingsFrag";
    private RatingsFragmentListener mListener;
    private TextView txtTotalRatings;
    private TextView txtCurrentRating;
    private TextView txtRequestAcceptedPercent;
    private TextView txtTripCancelledPercent;
    private RatingBar ratingDriver;
    private RatingDetailsBean ratingDetailsBean;
    private LinearLayout llRiderFeedback;
    private LinearLayout llProTips;
    private View.OnClickListener snackBarRefreshOnClickListener;

    public RatingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_ratings, null);
        lytContent.addView(rootView);

        intiView(rootView);

        populateRatingDetails();

        return lytBase;
    }

    @Override
    public void onResume() {
        super.onResume();

        onRefresh();
    }

    public void onRefresh() {
        setProgressScreenVisibility(true, true);
        getData(false);
    }

    private void getData(boolean isSwipeRefreshing) {
        mListener.onSwipeRefreshChange(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchRatingDetails();
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


        txtTotalRatings = (TextView) rootView.findViewById(R.id.txt_ratings_total_ratings);
        txtCurrentRating = (TextView) rootView.findViewById(R.id.txt_ratings_current_rating);
        txtRequestAcceptedPercent = (TextView) rootView.findViewById(R.id.txt_ratings_request_accepted_percent);
        txtTripCancelledPercent = (TextView) rootView.findViewById(R.id.txt_ratings_trips_cancelled_percent);

        ratingDriver = (RatingBar) rootView.findViewById(R.id.rating_ratings_driver);

        llRiderFeedback = (LinearLayout) rootView.findViewById(R.id.ll_ratings_rider_feedback);
        llProTips = (LinearLayout) rootView.findViewById(R.id.ll_ratings_pro_tips);

        llRiderFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), RiderFeedbackActivity.class));
            }
        });

        llProTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), ProTipsActivity.class));
            }
        });

    }

    private void fetchRatingDetails() {
        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchRatingDetails(urlParams, new RatingDetailsListener() {
            @Override
            public void onLoadCompleted(RatingDetailsBean ratingDetailsBeanWS) {

                ratingDetailsBean = ratingDetailsBeanWS;
                populateRatingDetails();
            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

                if (App.getInstance().isDemo()) {
                    ratingDetailsBean = new RatingDetailsBean();
                    ratingDetailsBean.setTotalRating(191);
                    ratingDetailsBean.setAverageRatings(4.8f);
                    ratingDetailsBean.setTotalRequests(286);
                    ratingDetailsBean.setRequestsAccepted(240);
                    ratingDetailsBean.setTotalTrips(240);
                    ratingDetailsBean.setTripsCancelled(12);

                    populateRatingDetails();
                }
            }
        });
    }

    private void populateRatingDetails() {

        if (ratingDetailsBean == null) {
            txtTotalRatings.setText(R.string.sample_00);
            txtCurrentRating.setText(R.string.label_not_available);
            txtRequestAcceptedPercent.setText(R.string.label_not_available);
            txtTripCancelledPercent.setText(R.string.label_not_available);
            ratingDriver.setRating(0f);
        } else {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(1);
            formatter.setMaximumFractionDigits(2);

            txtTotalRatings.setText(String.valueOf(ratingDetailsBean.getTotalRating()));
            txtCurrentRating.setText(String.valueOf(formatter.format(ratingDetailsBean.getAverageRatings())));
            txtRequestAcceptedPercent.setText(formatter.format(ratingDetailsBean.getRequestAcceptedPercentage()) + "%");
            txtTripCancelledPercent.setText(formatter.format(ratingDetailsBean.getTripsCancelledPercentage()) + "%");
            ratingDriver.setRating(ratingDetailsBean.getAverageRatings());
        }

        setProgressScreenVisibility(false, false);
        if (getActivity() != null)
            mListener.onSwipeRefreshChange(false);

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
        if (getActivity() instanceof RatingsFragmentListener) {
            mListener = (RatingsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof RatingsFragmentListener) {
            mListener = (RatingsFragmentListener) getParentFragment();
        } else if (context instanceof RatingsFragmentListener) {
            mListener = (RatingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RatingsFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof RatingsFragmentListener) {
            mListener = (RatingsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof RatingsFragmentListener) {
            mListener = (RatingsFragmentListener) getParentFragment();
        } else if (context instanceof RatingsFragmentListener) {
            mListener = (RatingsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RatingsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface RatingsFragmentListener {
        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);
    }
}
