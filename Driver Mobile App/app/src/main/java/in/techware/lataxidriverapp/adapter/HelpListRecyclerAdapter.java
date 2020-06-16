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
import in.techware.lataxidriverapp.activity.HelpActivity;
import in.techware.lataxidriverapp.model.HelpBean;
import in.techware.lataxidriverapp.model.HelpListBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.adapter
 * Project LaTaxiDriver
 */

public class HelpListRecyclerAdapter extends RecyclerView.Adapter<HelpListRecyclerAdapter.ViewHolder> {

    private static final String TAG = "HelpLRAda";
    private final Activity mContext;
    private HelpListRecyclerAdapterListener helpListRecyclerAdapterListener;
    private HelpListBean helpListBean;
    private boolean isLoadMore;

    public HelpListRecyclerAdapter(Activity mContext, HelpListBean helpListBean) {
        this.mContext = mContext;
        this.helpListBean = helpListBean;

    }

    public HelpListBean getHelpListBean() {
        return helpListBean;
    }

    public void setHelpListBean(HelpListBean helpListBean) {
        this.helpListBean = helpListBean;
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
        View itemLayout = inflater.inflate(R.layout.item_help, parent, false);
        return new ViewHolder(itemLayout);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setLayoutHelp(holder, position);

    }

    @Override
    public int getItemCount() {
        // help count.. to be changed.
        int count;
        try {
            count = helpListBean.getHelpList().size();
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setLayoutHelp(final ViewHolder holder, final int position) {
        final HelpBean bean = helpListBean.getHelpList().get(position);
        holder.txtTitle.setText(bean.getTitle());

        Glide.with(mContext.getApplicationContext())
                .load(bean.getIcon())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_help)
                        .fallback(R.drawable.ic_help))
                .into(holder.ivHelp);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHelp;
        TextView txtTitle;

        ViewHolder(View lytItem) {
            super(lytItem);

            ivHelp = (ImageView) lytItem.findViewById(R.id.iv_item_help);
            txtTitle = (TextView) lytItem.findViewById(R.id.txt_item_help_title);


            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    mVibrator.vibrate(25);

                    HelpBean bean = helpListBean.getHelpList().get(getLayoutPosition());
                    mContext.startActivity(new Intent(mContext, HelpActivity.class)
                            .putExtra("bean", bean));
                }
            });
        }
    }

    public static interface HelpListRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);
    }

    public HelpListRecyclerAdapterListener getHelpListRecyclerAdapterListener() {
        return helpListRecyclerAdapterListener;
    }


    public void setHelpListRecyclerAdapterListener(HelpListRecyclerAdapterListener helpListRecyclerAdapterListener) {
        this.helpListRecyclerAdapterListener = helpListRecyclerAdapterListener;
    }
}
