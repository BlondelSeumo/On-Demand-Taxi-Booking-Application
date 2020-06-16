package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.DriverStatusInvoker;

/**
 * Created by Jemsheer K D on 16 June, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class DriverStatusTask extends AsyncTask<String, Integer, BasicBean> {

    private DriverStatusTaskListener driverStatusTaskListener;

    private HashMap<String, String> urlParams;

    public DriverStatusTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        DriverStatusInvoker driverStatusInvoker = new DriverStatusInvoker(urlParams, null);
        return driverStatusInvoker.invokeDriverStatusWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            driverStatusTaskListener.dataDownloadedSuccessfully(result);
        else
            driverStatusTaskListener.dataDownloadFailed();
    }

    public static interface DriverStatusTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public DriverStatusTaskListener getDriverStatusTaskListener() {
        return driverStatusTaskListener;
    }

    public void setDriverStatusTaskListener(DriverStatusTaskListener driverStatusTaskListener) {
        this.driverStatusTaskListener = driverStatusTaskListener;
    }
}
