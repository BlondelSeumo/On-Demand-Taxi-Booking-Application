package in.techware.lataxicustomer.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxicustomer.net.invokers.LocationNameInvoker;

public class LocationNameTask extends AsyncTask<String, Integer, String> {

    private LocationNameTaskListener locationNameTaskListener;


    private HashMap<String, String> urlParams = new HashMap<>();

    public LocationNameTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }


    @Override
    protected String doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        LocationNameInvoker locationNameInvoker = new LocationNameInvoker(urlParams, null);
        return locationNameInvoker.invokeLocationNameWS();
    }


    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        if (result != null)
            locationNameTaskListener.dataDownloadedSuccessfully(result);
        else
            locationNameTaskListener.dataDownloadFailed();
    }

    public interface LocationNameTaskListener {
        void dataDownloadedSuccessfully(String address);

        void dataDownloadFailed();
    }

    public LocationNameTaskListener getLocationNameTaskListener() {
        return locationNameTaskListener;
    }


    public void setLocationNameTaskListener(LocationNameTaskListener locationNameTaskListener) {
        this.locationNameTaskListener = locationNameTaskListener;
    }

}

