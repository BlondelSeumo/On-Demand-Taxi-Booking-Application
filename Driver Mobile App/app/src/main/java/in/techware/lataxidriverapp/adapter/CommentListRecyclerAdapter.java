package in.techware.lataxidriverapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.CommentBean;
import in.techware.lataxidriverapp.model.CommentListBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.adapter
 * Project LaTaxiDriver
 */

public class CommentListRecyclerAdapter extends RecyclerView.Adapter<CommentListRecyclerAdapter.ViewHolder> {

    private static final String TAG = "CommentLRAda";
    private final Activity mContext;
    private CommentListRecyclerAdapterListener commentListRecyclerAdapterListener;
    private CommentListBean commentListBean;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;

    public CommentListRecyclerAdapter(Activity mContext, CommentListBean commentListBean) {
        this.mContext = mContext;
        this.commentListBean = commentListBean;
        try {
            currentPage = commentListBean.getPagination().getCurrentPage();
            totalPages = commentListBean.getPagination().getTotalPages();
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    public CommentListBean getCommentListBean() {
        return commentListBean;
    }

    public void setCommentListBean(CommentListBean commentListBean) {
        this.commentListBean = commentListBean;
        try {
            currentPage = commentListBean.getPagination().getCurrentPage();
            totalPages = commentListBean.getPagination().getTotalPages();
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
        View itemLayout = inflater.inflate(R.layout.item_comments, parent, false);
        return new ViewHolder(itemLayout);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setLayoutComment(holder, position);

        if (!isLoadMore && position + 1 == commentListBean.getComments().size()
                && currentPage < totalPages) {
            commentListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }

    }

    @Override
    public int getItemCount() {
        // comment count.. to be changed.
        int count;
        try {
            count = commentListBean.getComments().size();
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setLayoutComment(final ViewHolder holder, final int position) {
        final CommentBean bean = commentListBean.getComments().get(position);
        holder.txtDate.setText(App.getDateFromUnix(App.DATE_FORMAT_4, true, false, bean.getTime()));
        holder.txtComment.setText(bean.getCustomerComment());
        holder.ratingDriver.setRating(bean.getRating());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        TextView txtComment;
        RatingBar ratingDriver;

        ViewHolder(View lytItem) {
            super(lytItem);

            txtDate = (TextView) lytItem.findViewById(R.id.txt_item_comment_date);
            txtComment = (TextView) lytItem.findViewById(R.id.txt_item_comment_comment);
            ratingDriver = (RatingBar) lytItem.findViewById(R.id.rating_item_comment);


            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    mVibrator.vibrate(25);

                    CommentBean bean = commentListBean.getComments().get(getLayoutPosition());
                    /* mContext.startActivity(new Intent(mContext, DetailsActivity.class)
                            .putExtra("bean", bean));*/
                }
            });
        }
    }

    public static interface CommentListRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);
    }

    public CommentListRecyclerAdapterListener getCommentListRecyclerAdapterListener() {
        return commentListRecyclerAdapterListener;
    }


    public void setCommentListRecyclerAdapterListener(CommentListRecyclerAdapterListener commentListRecyclerAdapterListener) {
        this.commentListRecyclerAdapterListener = commentListRecyclerAdapterListener;
    }
}
