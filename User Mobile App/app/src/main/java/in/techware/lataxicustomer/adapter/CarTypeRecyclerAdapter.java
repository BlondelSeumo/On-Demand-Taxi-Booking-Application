package in.techware.lataxicustomer.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.CarBean;
import in.techware.lataxicustomer.model.LandingPageBean;

/**
 * Created by Jemsheer K D on 20 July, 2017.
 * Package in.techware.lataxi.adapter
 * Project LaTaxi
 */

public class CarTypeRecyclerAdapter extends RecyclerView.Adapter<CarTypeRecyclerAdapter.ViewHolder> {

    private static final String TAG = "CarTypeRecyclerAdapter";
    private final Activity mContext;
    private CarTypeRecyclerAdapterListener carTypeRecyclerAdapterListener;
    private LandingPageBean landingPageBean;
    private boolean isLoadMore;
    private String selectedCarID = "";
    private int width;
    private int widthOfItem;
    private ViewGroup.LayoutParams param;

    public CarTypeRecyclerAdapter(Activity mContext, LandingPageBean landingPageBean) {
        this.mContext = mContext;
        this.landingPageBean = landingPageBean;

        selectedCarID = landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()
                ? landingPageBean.getCars().get(0).getCarID() : "";

        generateItemWidth();
    }

    private void generateItemWidth() {
        width = App.getInstance().getWidth();
        if (landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()) {
            widthOfItem = (int) (landingPageBean.getCars().size() > 4
                    ? (width - (20 * App.getInstance().getPx())) / 4
                    : (width - (20 * App.getInstance().getPx())) / landingPageBean.getCars().size());
        } else {
            widthOfItem = 0;
        }
        param = new ViewGroup.LayoutParams(widthOfItem, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public LandingPageBean getLandingPageBean() {
        return landingPageBean;
    }

    public void setLandingPageBean(LandingPageBean landingPageBean) {
        this.landingPageBean = landingPageBean;


        if (selectedCarID.equalsIgnoreCase("") || landingPageBean.getCar(selectedCarID) == null) {
            selectedCarID = landingPageBean.getCars() != null && !landingPageBean.getCars().isEmpty()
                    ? landingPageBean.getCars().get(0).getCarID() : "";
        }
        generateItemWidth();
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
        View itemLayout = inflater.inflate(R.layout.item_car_type, parent, false);
        return new ViewHolder(itemLayout);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setLayoutCarType(holder, position);

    }

    @Override
    public int getItemCount() {
        // dummy count.. to be changed.
        int count;
        try {
            count = landingPageBean.getCars().size();
        } catch (Exception e) {
            return 0;
        }
        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setLayoutCarType(final ViewHolder holder, final int position) {
        final CarBean bean = landingPageBean.getCars().get(position);
        holder.txtCarType.setText(bean.getCarName());

        if (selectedCarID.equalsIgnoreCase(bean.getCarID())) {
            holder.txtCarType.setBackgroundResource(R.drawable.btn_click_green_dark_rectangle_with_semicircle_edge);
            holder.txtCarType.setTextColor(ContextCompat.getColor(mContext.getApplicationContext(), R.color.white));
        } else {
            holder.txtCarType.setBackgroundResource(R.color.transparent);
            holder.txtCarType.setTextColor(ContextCompat.getColor(mContext.getApplicationContext(), R.color.colorPrimary));
        }

        Glide.with(mContext.getApplicationContext())
                .load(bean.getCarImage())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_car_la_landing_page)
                        .fallback(R.drawable.ic_car_la_landing_page))
                .into(holder.ivCar);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCar;
        TextView txtCarType;

        ViewHolder(View lytItem) {
            super(lytItem);

            lytItem.setLayoutParams(param);


            ivCar = (ImageView) lytItem.findViewById(R.id.iv_item_car_type);
            txtCarType = (TextView) lytItem.findViewById(R.id.txt_item_car_type_name);


            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                    mVibrator.vibrate(25);

                    CarBean bean = landingPageBean.getCars().get(getLayoutPosition());

                    selectedCarID = bean.getCarID();

                    carTypeRecyclerAdapterListener.onSelectedCar(getLayoutPosition(), bean);
                    notifyDataSetChanged();
                    /* mContext.startActivity(new Intent(mContext, DetailsActivity.class)
                            .putExtra("bean", bean));*/
                }
            });
        }
    }

    public static interface CarTypeRecyclerAdapterListener {

        void onRefresh();

        void onSelectedCar(int position, CarBean carBean);

    }

    public CarTypeRecyclerAdapterListener getCarTypeRecyclerAdapterListener() {
        return carTypeRecyclerAdapterListener;
    }


    public void setCarTypeRecyclerAdapterListener(CarTypeRecyclerAdapterListener carTypeRecyclerAdapterListener) {
        this.carTypeRecyclerAdapterListener = carTypeRecyclerAdapterListener;
    }
}
