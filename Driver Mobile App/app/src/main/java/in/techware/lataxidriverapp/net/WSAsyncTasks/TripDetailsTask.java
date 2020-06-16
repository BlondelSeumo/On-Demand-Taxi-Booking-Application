package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.net.invokers.TripDetailsInvoker;

/**
 * Created by Jemsheer K D on 03 July, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class TripDetailsTask extends AsyncTask<String, Integer, TripBean> {

    private TripDetailsTaskListener tripDetailsTaskListener;

    private HashMap<String, String> urlParams;

    public TripDetailsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TripDetailsInvoker tripDetailsInvoker = new TripDetailsInvoker(urlParams, null);
        return tripDetailsInvoker.invokeTripDetailsWS();
    }

    @Override
    protected void onPostExecute(TripBean result) {
        super.onPostExecute(result);
        if (result != null)
            tripDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            tripDetailsTaskListener.dataDownloadFailed();
    }

    public static interface TripDetailsTaskListener {
        void dataDownloadedSuccessfully(TripBean tripBean);

        void dataDownloadFailed();
    }

    public TripDetailsTaskListener getTripDetailsTaskListener() {
        return tripDetailsTaskListener;
    }

    public void setTripDetailsTaskListener(TripDetailsTaskListener tripDetailsTaskListener) {
        this.tripDetailsTaskListener = tripDetailsTaskListener;
    }
}
