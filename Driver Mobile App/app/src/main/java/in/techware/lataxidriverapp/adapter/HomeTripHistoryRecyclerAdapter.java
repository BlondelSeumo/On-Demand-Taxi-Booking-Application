package in.techware.lataxidriverapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.activity.TripDetailsActivity;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.model.TripListBean;

/**
 * Created by Jemsheer K D on 05 May, 2017.
 * Package in.techware.lataxidriver.adapter
 * Project LaTaxiDriver
 */

public class HomeTripHistoryRecyclerAdapter extends RecyclerView.Adapter<HomeTripHistoryRecyclerAdapter.ViewHolder> {

    private static final String TAG = "HomeTripHistoryRecyclerAdapter";
    private final Activity mContext;
    private int currentPage;
    private int totalPages;
    private HomeTripHistoryRecyclerAdapterListener homeTripHistoryRecyclerAdapterListener;
    private TripListBean tripListBean;
    private boolean isLoadMore;

    public HomeTripHistoryRecyclerAdapter(Activity mContext, TripListBean tripListBean) {
        this.mContext = mContext;
        this.tripListBean = tripListBean;

        try {
            currentPage = tripListBean.getPagination().getCurrentPage();
            totalPages = tripListBean.getPagination().getTotalPages();
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }

    }

    public TripListBean getTripListBean() {
        return tripListBean;
    }

    public void setTripListBean(TripListBean tripListBean) {
        this.tripListBean = tripListBean;
        try {
            currentPage = tripListBean.getPagination().getCurrentPage();
            totalPages = tripListBean.getPagination().getTotalPages();
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_home_trip_history, parent, false);
        return new ViewHolder(itemLayout);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setLayoutTrip(holder, position);

        if (!isLoadMore && position + 1 == tripListBean.getTrips().size()
                && currentPage < totalPages) {
            homeTripHistoryRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }

    }

    @Override
    public int getItemCount() {
        // trip count.. to be changed.
        int count;
        try {
            count = tripListBean.getTrips().size();
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setLayoutTrip(final ViewHolder holder, final int position) {
        TripBean bean = tripListBean.getTrips().get(position);
        holder.txtTime.setText(App.getDateFromUnix(App.DATE_FORMAT_3, true, false, bean.getStartTime()));
        holder.txtSourceLocation.setText(bean.getSourceLocation());
        holder.txtDestinationLocation.setText(bean.getDestinationLocation());
        holder.txtFare.setText(bean.getFare());

        Glide.with(mContext)
                .load(bean.getCustomerPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(holder.ivDriverPhoto);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDriverPhoto;
        TextView txtTime;
        TextView txtSourceLocation;
        TextView txtDestinationLocation;
        TextView txtFare;

        ViewHolder(View lytItem) {
            super(lytItem);

            ivDriverPhoto = (ImageView) lytItem.findViewById(R.id.iv_item_home_trip_history_driver_photo);
            txtTime = (TextView) lytItem.findViewById(R.id.txt_item_home_trip_history_time);
            txtSourceLocation = (TextView) lytItem.findViewById(R.id.txt_item_home_trip_history_source_location);
            txtDestinationLocation = (TextView) lytItem.findViewById(R.id.txt_item_home_trip_history_destination_location);
            txtFare = (TextView) lytItem.findViewById(R.id.txt_item_home_trip_history_fare);


            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    mVibrator.vibrate(25);

                    TripBean bean = tripListBean.getTrips().get(getLayoutPosition());
                    mContext.startActivity(new Intent(mContext, TripDetailsActivity.class)
                            .putExtra("trip_id", bean.getId()));
                }
            });
        }
    }

    public static interface HomeTripHistoryRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);
    }

    public HomeTripHistoryRecyclerAdapterListener getHomeTripHistoryRecyclerAdapterListener() {
        return homeTripHistoryRecyclerAdapterListener;
    }


    public void setHomeTripHistoryRecyclerAdapterListener(HomeTripHistoryRecyclerAdapterListener homeTripHistoryRecyclerAdapterListener) {
        this.homeTripHistoryRecyclerAdapterListener = homeTripHistoryRecyclerAdapterListener;
    }
}
