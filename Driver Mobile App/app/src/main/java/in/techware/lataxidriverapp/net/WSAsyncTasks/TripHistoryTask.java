package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripListBean;
import in.techware.lataxidriverapp.net.invokers.TripHistoryInvoker;

/**
 * Created by Jemsheer K D on 11 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class TripHistoryTask extends AsyncTask<String, Integer, TripListBean> {

    private TripHistoryTaskListener tripHistoryTaskListener;

    private HashMap<String, String> urlParams;

    public TripHistoryTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripListBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TripHistoryInvoker tripHistoryInvoker = new TripHistoryInvoker(urlParams, null);
        return tripHistoryInvoker.invokeTripHistoryWS();
    }

    @Override
    protected void onPostExecute(TripListBean result) {
        super.onPostExecute(result);
        if (result != null)
            tripHistoryTaskListener.dataDownloadedSuccessfully(result);
        else
            tripHistoryTaskListener.dataDownloadFailed();
    }

    public static interface TripHistoryTaskListener {
        void dataDownloadedSuccessfully(TripListBean tripListBean);

        void dataDownloadFailed();
    }

    public TripHistoryTaskListener getTripHistoryTaskListener() {
        return tripHistoryTaskListener;
    }

    public void setTripHistoryTaskListener(TripHistoryTaskListener tripHistoryTaskListener) {
        this.tripHistoryTaskListener = tripHistoryTaskListener;
    }
}
