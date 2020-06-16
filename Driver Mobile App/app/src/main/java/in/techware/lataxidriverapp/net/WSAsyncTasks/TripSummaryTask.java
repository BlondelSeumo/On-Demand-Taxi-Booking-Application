package in.techware.lataxidriverapp.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripSummaryBean;
import in.techware.lataxidriverapp.net.invokers.TripSummaryInvoker;

public class TripSummaryTask extends AsyncTask<String, Integer, TripSummaryBean> {

    private TripSummaryTaskListener tripSummaryTaskListener;

    private HashMap<String, String> urlParams;

    public TripSummaryTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripSummaryBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TripSummaryInvoker tripSummaryInvoker = new TripSummaryInvoker(urlParams, null);
        return tripSummaryInvoker.invokeTripSummaryWS();
    }

    @Override
    protected void onPostExecute(TripSummaryBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            tripSummaryTaskListener.dataDownloadedSuccessfully(result);
        else
            tripSummaryTaskListener.dataDownloadFailed();
    }

    public static interface TripSummaryTaskListener {

        void dataDownloadedSuccessfully(TripSummaryBean tripSummaryBean);

        void dataDownloadFailed();
    }

    public TripSummaryTaskListener getTripSummaryTaskListener() {
        return tripSummaryTaskListener;
    }

    public void setTripSummaryTaskListener(TripSummaryTaskListener tripSummaryTaskListener) {
        this.tripSummaryTaskListener = tripSummaryTaskListener;
    }
}

