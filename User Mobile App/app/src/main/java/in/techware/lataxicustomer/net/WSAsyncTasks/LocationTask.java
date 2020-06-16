package in.techware.lataxicustomer.net.WSAsyncTasks;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.model.PlaceBean;

/**
 * Created by Jemsheer K D on 11 July, 2017.
 * Package in.techware.lataxi.net.WSAsyncTasks
 * Project LaTaxi
 */

public class LocationTask extends AsyncTask<String, Integer, PlaceBean> {


    private static final String TAG = "LocationTask";
    private double latitude;
    private double longitude;
    private LocationTaskListener locationTaskListener;
    private PlaceBean placeBean;


    public LocationTask(double latitude, double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected PlaceBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");

        Geocoder geocoder = new Geocoder(App.getInstance(), Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);


            Log.i(TAG, "doInBackground: " + new Gson().toJson(addresses));
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                System.out.println("Location Name Retrieved : " + address);
                Config.getInstance().setCurrentLocation(address.getFeatureName());

                if (placeBean == null)
                    placeBean = new PlaceBean();
                placeBean.setName(address.getFeatureName());
                placeBean.setName(address.getAddressLine(0));
                placeBean.setLatitude(String.valueOf(latitude));
                placeBean.setLongitude(String.valueOf(longitude));
                return placeBean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    protected void onPostExecute(PlaceBean result) {

        super.onPostExecute(result);
        if (result != null)
            locationTaskListener.dataDownloadedSuccessfully(result);
        else
            locationTaskListener.dataDownloadFailed();
    }

   /* @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        if (result != null)
            locationTaskListener.dataDownloadedSuccessfully(result);
        else
            locationTaskListener.dataDownloadFailed();
    }*/

    public interface LocationTaskListener {
        //        void dataDownloadedSuccessfully(String address);
        void dataDownloadedSuccessfully(PlaceBean placeBean);

        void dataDownloadFailed();
    }

    public LocationTask.LocationTaskListener getLocationTaskListener() {
        return locationTaskListener;
    }


    public void setLocationTaskListener(LocationTask.LocationTaskListener locationTaskListener) {
        this.locationTaskListener = locationTaskListener;
    }
}
