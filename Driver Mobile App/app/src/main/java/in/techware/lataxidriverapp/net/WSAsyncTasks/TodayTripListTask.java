package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.TripListBean;
import in.techware.lataxidriverapp.net.invokers.TodayTripListInvoker;

/**
 * Created by Jemsheer K D on 08 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class TodayTripListTask extends AsyncTask<String, Integer, TripListBean> {

    private TodayTripListTaskListener todayTripListTaskListener;

    private HashMap<String, String> urlParams;

    public TodayTripListTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripListBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TodayTripListInvoker todayTripListInvoker = new TodayTripListInvoker(urlParams, null);
        return todayTripListInvoker.invokeTodayTripListWS();
    }

    @Override
    protected void onPostExecute(TripListBean result) {
        super.onPostExecute(result);
        if (result != null)
            todayTripListTaskListener.dataDownloadedSuccessfully(result);
        else
            todayTripListTaskListener.dataDownloadFailed();
    }

    public static interface TodayTripListTaskListener {
        void dataDownloadedSuccessfully(TripListBean tripListBean);

        void dataDownloadFailed();
    }

    public TodayTripListTaskListener getTodayTripListTaskListener() {
        return todayTripListTaskListener;
    }

    public void setTodayTripListTaskListener(TodayTripListTaskListener todayTripListTaskListener) {
        this.todayTripListTaskListener = todayTripListTaskListener;
    }
}
