package in.techware.lataxidriverapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.model.IssueBean;
import in.techware.lataxidriverapp.model.IssueListBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.adapter
 * Project LaTaxiDriver
 */

public class IssueListRecyclerAdapter extends RecyclerView.Adapter<IssueListRecyclerAdapter.ViewHolder> {

    private static final String TAG = "IssueLRAda";
    private final Activity mContext;
    private IssueListRecyclerAdapterListener issueListRecyclerAdapterListener;
    private IssueListBean issueListBean;
    private boolean isLoadMore;
    private int currentPage;
    private int totalPages;

    public IssueListRecyclerAdapter(Activity mContext, IssueListBean issueListBean) {
        this.mContext = mContext;
        this.issueListBean = issueListBean;
        try {
            currentPage = issueListBean.getPagination().getCurrentPage();
            totalPages = issueListBean.getPagination().getTotalPages();
        } catch (Exception e) {
            currentPage = 0;
            totalPages = 0;
        }
    }

    public IssueListBean getIssueListBean() {
        return issueListBean;
    }

    public void setIssueListBean(IssueListBean issueListBean) {
        this.issueListBean = issueListBean;
        try {
            currentPage = issueListBean.getPagination().getCurrentPage();
            totalPages = issueListBean.getPagination().getTotalPages();
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
        View itemLayout = inflater.inflate(R.layout.item_issues, parent, false);
        return new ViewHolder(itemLayout);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setLayoutIssue(holder, position);

        if (!isLoadMore && position + 1 == issueListBean.getIssues().size()
                && currentPage < totalPages) {
            issueListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
        }

    }

    @Override
    public int getItemCount() {
        // issue count.. to be changed.
        int count;
        try {
            count = issueListBean.getIssues().size();
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setLayoutIssue(final ViewHolder holder, final int position) {
        final IssueBean bean = issueListBean.getIssues().get(position);
        holder.txtIssue.setText(bean.getIssue());
        holder.txtComment.setText(bean.getCustomerComment());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIssue;
        TextView txtComment;

        ViewHolder(View lytItem) {
            super(lytItem);

            txtIssue = (TextView) lytItem.findViewById(R.id.txt_item_issues_issue);
            txtComment = (TextView) lytItem.findViewById(R.id.txt_item_issue_customer_comment);


            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    mVibrator.vibrate(25);

                    IssueBean bean = issueListBean.getIssues().get(getLayoutPosition());
                    /* mContext.startActivity(new Intent(mContext, DetailsActivity.class)
                            .putExtra("bean", bean));*/
                }
            });
        }
    }

    public static interface IssueListRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);
    }

    public IssueListRecyclerAdapterListener getIssueListRecyclerAdapterListener() {
        return issueListRecyclerAdapterListener;
    }


    public void setIssueListRecyclerAdapterListener(IssueListRecyclerAdapterListener issueListRecyclerAdapterListener) {
        this.issueListRecyclerAdapterListener = issueListRecyclerAdapterListener;
    }
}
