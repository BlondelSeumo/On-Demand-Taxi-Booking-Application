package in.techware.lataxidriverapp.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.AccessibilityBean;
import in.techware.lataxidriverapp.net.invokers.DriverAccessibilityInvoker;

public class DriverAccessibilityTask extends AsyncTask<String, Integer, AccessibilityBean> {

    private DriverAccessibilityTaskListener driverAccessibilityTaskListener;

    private HashMap<String, String> urlParams;

    public DriverAccessibilityTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected AccessibilityBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        DriverAccessibilityInvoker driverAccessibilityInvoker = new DriverAccessibilityInvoker(urlParams, null);
        return driverAccessibilityInvoker.invokeDriverAccessibilityInvokerWS();
    }

    @Override
    protected void onPostExecute(AccessibilityBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            driverAccessibilityTaskListener.dataDownloadedSuccessfully(result);
        else
            driverAccessibilityTaskListener.dataDownloadFailed();
    }

    public static interface DriverAccessibilityTaskListener {

        void dataDownloadedSuccessfully(AccessibilityBean accessibilityBean);

        void dataDownloadFailed();
    }

    public DriverAccessibilityTaskListener getDriverAccessibilityTaskListener() {
        return driverAccessibilityTaskListener;
    }

    public void setDriverAccessibilityTaskListener(DriverAccessibilityTaskListener driverAccessibilityTaskListener) {
        this.driverAccessibilityTaskListener = driverAccessibilityTaskListener;
    }
}