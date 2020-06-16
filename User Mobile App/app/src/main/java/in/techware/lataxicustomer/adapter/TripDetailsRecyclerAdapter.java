package in.techware.lataxicustomer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.HashSet;

import in.techware.lataxicustomer.R;
import in.techware.lataxicustomer.activity.TripDetailsActivity;
import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.TripBean;
import in.techware.lataxicustomer.model.TripDetailsBean;
import in.techware.lataxicustomer.model.TripListBean;


/**
 * Created by Jemsheer K D on 15 February, 2017.
 * Package in.techware.soulmate.adapter
 * Project Soulmate
 */

public class TripDetailsRecyclerAdapter extends RecyclerView.Adapter<TripDetailsRecyclerAdapter.ViewHolder> {

    private static final String TAG = "TripDetailsRAd";
    private Activity mContext;
    private static Intent intent;
    private final HashSet<MapView> mMapSet;
    private TripDetailsRecyclerAdapterListener tripDetailsRecyclerAdapterListener;
    private TripListBean tripListBean;
    private boolean isLoadMore;
    private TripDetailsBean tripDetailsBean;
    private TripBean bean;

    public TripDetailsRecyclerAdapter(Activity mContext, TripListBean tripListBean) {
        this.mContext = mContext;
        this.tripListBean = tripListBean;
        mMapSet = new HashSet<>();
    }

    public TripListBean getTripListBean() {
        return tripListBean;
    }

    public void setTripListBean(TripListBean tripListBean) {
        this.tripListBean = tripListBean;
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    public HashSet<MapView> getMaps() {
        return mMapSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_trips_list, parent, false);
        return new ViewHolder(itemLayout);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        setLayoutTrip(holder, position);
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
        final TripBean bean = tripListBean.getTrips().get(position);

        Log.i(TAG, "setLayoutTrip: TRIPBEAN : " + new Gson().toJson(bean));

        if (bean.getTripStatus().equalsIgnoreCase("3")) {
            holder.txtRate.setVisibility(View.VISIBLE);
            holder.txtCancelled.setVisibility(View.GONE);
        } else {
            holder.txtCancelled.setVisibility(View.VISIBLE);
            holder.txtRate.setVisibility(View.GONE);
        }

        holder.txtDate.setText(App.getUserDateFromUnix(String.valueOf(bean.getTime())));
        holder.txtTime.setText(App.getUserTimeFromUnix(String.valueOf(bean.getTime())));
        holder.txtCarName.setText(String.valueOf(bean.getCarName()));
        holder.txtRate.setText(String.valueOf(bean.getRate()));

        Glide.with(mContext.getApplicationContext())
                .load(bean.getDriverPhoto())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_dummy_photo)
                        //.error(R.drawable.ic_dummy_photo)
                       // .fallback(R.drawable.ic_dummy_photo)
                        .centerCrop()
                        .circleCrop())
                .into(holder.ivDriverPhoto);

        holder.initializeMapView();

        if (holder.map != null) {
            // The map is already ready to be used
            setMapLocation(mContext, holder.map, bean);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        private TextView txtDate;
        private TextView txtCarName;
        private TextView txtRate;
        private ImageView ivDriverPhoto;
        private TextView txtTime;
        private TextView txtCancelled;

        public MapView mapView;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(mContext.getApplicationContext());
            map = googleMap;
            if (tripListBean != null && !tripListBean.getTrips().isEmpty()) {
                TripBean bean = tripListBean.getTrips().get(getLayoutPosition());
                if (bean != null) {
                    setMapLocation(mContext, map, bean);

                }
            }
        }

        /*
         * Initialises the MapView by calling its lifecycle methods.
        */
        public void initializeMapView() {
            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
            }
        }

        ViewHolder(View lytItem) {
            super(lytItem);


            txtDate = (TextView) lytItem.findViewById(R.id.txt_date);
            txtTime = (TextView) lytItem.findViewById(R.id.txt_time);
            txtCarName = (TextView) lytItem.findViewById(R.id.txt_car_name);
            txtRate = (TextView) lytItem.findViewById(R.id.txt_rate);
            txtCancelled = (TextView) lytItem.findViewById(R.id.txt_cancelled);

            ivDriverPhoto = (ImageView) lytItem.findViewById(R.id.iv_driver_photo);

            mapView = (MapView) lytItem.findViewById(R.id.list_map_view);

            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    //                    mVibrator.vibrate(25);

                    bean = tripListBean.getTrips().get(getLayoutPosition());

                    mContext.startActivity(new Intent(mContext, TripDetailsActivity.class)
                            .putExtra("bean", bean));
                }
            });
        }
    }

    private static void setMapLocation(final Activity mContext, final GoogleMap map, final TripBean tripBean) {
        // Add a marker for this item and set the camera

        map.getUiSettings().setMapToolbarEnabled(false);
        try {
            LatLng latLng = new LatLng(Double.parseDouble(tripBean.getSourceLatitude()), Double.parseDouble(tripBean.getSourceLongitude()));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }

       /* final Marker mapMarker = map.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker))
        );*/

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mContext.startActivity(new Intent(mContext, TripDetailsActivity.class)
                        .putExtra("bean", tripBean));
            }
        });

        // Set the map type back to normal.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public static interface TripDetailsRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onItemSelected(TripBean bean);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);

    }

    public TripDetailsRecyclerAdapterListener getTripDetailsRecyclerAdapterListener() {
        return tripDetailsRecyclerAdapterListener;
    }

    public void setTripDetailsRecyclerAdapterListener(TripDetailsRecyclerAdapterListener tripDetailsRecyclerAdapterListener) {
        this.tripDetailsRecyclerAdapterListener = tripDetailsRecyclerAdapterListener;

    }
}
