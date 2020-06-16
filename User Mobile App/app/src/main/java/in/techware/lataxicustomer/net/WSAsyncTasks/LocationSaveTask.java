package in.techware.lataxicustomer.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxicustomer.model.LocationBean;
import in.techware.lataxicustomer.net.invokers.LocationSaveInvoker;

public class LocationSaveTask extends AsyncTask<String, Integer, LocationBean> {

    private LocationSaveTask.LocationTaskListener locationTaskListener;

    private JSONObject postData;

    public LocationSaveTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected LocationBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        LocationSaveInvoker locationSaveInvoker = new LocationSaveInvoker(null, postData);
        return locationSaveInvoker.invokeLocationSaveWS();
    }

    @Override
    protected void onPostExecute(LocationBean result) {
        super.onPostExecute(result);
        if (result != null)
            locationTaskListener.dataDownloadedSuccessfully(result);
        else
            locationTaskListener.dataDownloadFailed();
    }

    public static interface LocationTaskListener {

        void dataDownloadedSuccessfully(LocationBean locationBean);

        void dataDownloadFailed();
    }

    public LocationTaskListener getLocationTaskListener() {
        return locationTaskListener;
    }

    public void setLocationTaskListener(LocationTaskListener locationTaskListener) {
        this.locationTaskListener = locationTaskListener;
    }
}
