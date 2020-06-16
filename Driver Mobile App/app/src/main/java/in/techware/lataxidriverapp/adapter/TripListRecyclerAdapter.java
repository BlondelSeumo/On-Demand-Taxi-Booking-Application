package in.techware.lataxidriverapp.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import in.techware.lataxidriverapp.R;
import in.techware.lataxidriverapp.activity.TripDetailsActivity;
import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.listeners.PolyPointListener;
import in.techware.lataxidriverapp.model.PolyPointBean;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.model.TripListBean;
import in.techware.lataxidriverapp.net.DataManager;

import static in.techware.lataxidriverapp.app.App.DATE_FORMAT_1;
import static in.techware.lataxidriverapp.app.App.TIME_FORMAT_0;

/**
 * Created by Jemsheer K D on 08 May, 2017.
 * Package in.techware.lataxidriver.adapter
 * Project LaTaxiDriver
 */

public class TripListRecyclerAdapter extends RecyclerView.Adapter<TripListRecyclerAdapter.ViewHolder> {

    private static final String TAG = "TripListRecyclerAdapter";
    private Activity mContext;
    private final HashSet<MapView> mMapSet;
    private TripListRecyclerAdapterListener tripListRecyclerAdapterListener;
    private boolean isLoadMore;
    private TripListBean tripListBean;
    private int currentPage;
    private int totalPages;

    public TripListRecyclerAdapter(Activity mContext, TripListBean tripListBean) {
        this.mContext = mContext;
        this.tripListBean = tripListBean;
        mMapSet = new HashSet<>();
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

    public HashSet<MapView> getMaps() {
        return mMapSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_trip_history, parent, false);
        return new ViewHolder(itemLayout);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        setLayoutTrip(holder, position);

        if (!isLoadMore && position + 1 == tripListBean.getTrips().size()
                && currentPage < totalPages) {
            tripListRecyclerAdapterListener.onRequestNextPage(true, currentPage);
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

        holder.txtDate.setText(App.getDateFromUnix(DATE_FORMAT_1, true, false, bean.getStartTime()));
        holder.txtTime.setText(App.getTimeFromUnix(TIME_FORMAT_0, true, false, bean.getStartTime()));
        holder.txtFare.setText(bean.getTripStatus().equalsIgnoreCase("0")
                ? mContext.getString(R.string.label_cancelled) : bean.getFare());

        Glide.with(mContext.getApplicationContext())
                .load(bean.getCustomerPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(holder.ivDriverPhoto);

        holder.initializeMapView();

        if (holder.map != null) {
            // The map is already ready to be used
            setMapLocation(mContext, holder.map, bean);
        }
    }


    private static void populateMap(GoogleMap mMap, TripBean tripBean) {
        mMap.clear();
        onPlotLatLng(mMap, tripBean);
        mapAutoZoom(mMap, tripBean);
    }

    private static void onPlotLatLng(GoogleMap mMap, TripBean tripBean) {

        fetchPolyPoint(mMap, tripBean);

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(tripBean.getDSourceLatitude(), tripBean.getDSourceLongitude());

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));

//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_source)));


            newLatLng = new LatLng(tripBean.getDDestinationLatitude(), tripBean.getDDestinationLongitude());
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_destination)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    public static void mapAutoZoom(GoogleMap mMap, TripBean tripBean) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(tripBean.getSourceLatLng());
        builder.include(tripBean.getDestinationLatLng());
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * App.getInstance().getPx())));

    }

    private static void fetchPolyPoint(final GoogleMap mMap, TripBean tripBean) {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", tripBean.getSourceLatitude() + "," + tripBean.getSourceLongitude());
        urlParams.put("destination", tripBean.getDestinationLatitude() + "," + tripBean.getDestinationLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", App.getInstance().getString(R.string.browser_api_key));

        DataManager.fetchPolyPoints(urlParams, new PolyPointListener() {

            @Override
            public void onLoadCompleted(PolyPointBean polyPointBeanWS) {
                populatePath(mMap, polyPointBeanWS);
            }

            @Override
            public void onLoadFailed(String error) {
            }
        });
    }

    private static void populatePath(GoogleMap mMap, PolyPointBean polyPointBean) {

        ArrayList<List<HashMap<String, String>>> routes = polyPointBean.getRoutes();

        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap point = (HashMap) path.get(j);

                double lat = Double.parseDouble((String) point.get("lat"));
                double lng = Double.parseDouble((String) point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(8);
            polyLineOptions.color(ContextCompat.getColor(App.getInstance(), R.color.map_path));

        }

        Polyline polyLine = mMap.addPolyline(polyLineOptions);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        private final TextView txtDate;
        private final TextView txtFare;
        private final ImageView ivDriverPhoto;
        private final TextView txtTime;

        public MapView mapView;
        GoogleMap map;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(mContext.getApplicationContext());
            map = googleMap;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            TripBean bean = tripListBean.getTrips().get(getLayoutPosition());
            if (bean != null) {
                setMapLocation(mContext, map, bean);
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

            txtDate = (TextView) lytItem.findViewById(R.id.txt_item_trip_history_date);
            txtTime = (TextView) lytItem.findViewById(R.id.txt_item_trip_history_time);
            txtFare = (TextView) lytItem.findViewById(R.id.txt_item_trip_history_fare);

            ivDriverPhoto = (ImageView) lytItem.findViewById(R.id.iv_item_trip_history_customer_photo);

            mapView = (MapView) lytItem.findViewById(R.id.mapview_item_trip_history);

            lytItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                    //                    mVibrator.vibrate(25);

                    TripBean bean = tripListBean.getTrips().get(getLayoutPosition());
                    mContext.startActivity(new Intent(mContext, TripDetailsActivity.class)
//                            .putExtra("bean", bean));
                            .putExtra("trip_id", bean.getId()));
                }
            });
        }
    }

    private static void setMapLocation(final Activity mContext, final GoogleMap map, final TripBean tripBean) {
        // Add a marker for this item and set the camera

        map.clear();
        map.getUiSettings().setMapToolbarEnabled(false);

        LatLng latLng = new LatLng(Double.parseDouble(tripBean.getSourceLatitude()), Double.parseDouble(tripBean.getSourceLongitude()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
       /* final Marker mapMarker = map.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_marker))
        );*/

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mContext.startActivity(new Intent(mContext, TripDetailsActivity.class)
//                        .putExtra("bean", tripBean));
                        .putExtra("trip_id", tripBean.getId()));
            }
        });

        populateMap(map, tripBean);

    }

    public static interface TripListRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onItemSelected(TripBean bean);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);

    }

    public TripListRecyclerAdapterListener getTripListRecyclerAdapterListener() {
        return tripListRecyclerAdapterListener;
    }

    public void setTripListRecyclerAdapterListener(TripListRecyclerAdapterListener tripListRecyclerAdapterListener) {
        this.tripListRecyclerAdapterListener = tripListRecyclerAdapterListener;

    }
}