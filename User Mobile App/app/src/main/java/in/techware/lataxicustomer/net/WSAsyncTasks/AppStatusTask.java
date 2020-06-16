package in.techware.lataxicustomer.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxicustomer.model.DriverBean;
import in.techware.lataxicustomer.net.invokers.AppStatusInvoker;

public class AppStatusTask extends AsyncTask<String, Integer, DriverBean> {

    private AppStatusTaskListener appStatusTaskListener;

    private HashMap<String, String> urlParams;

    public AppStatusTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected DriverBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        AppStatusInvoker appStatusInvoker = new AppStatusInvoker(urlParams, null);
        return appStatusInvoker.invokeAppStatusWS();
    }

    @Override
    protected void onPostExecute(DriverBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            appStatusTaskListener.dataDownloadedSuccessfully(result);
        else
            appStatusTaskListener.dataDownloadFailed();
    }

    public static interface AppStatusTaskListener {

        void dataDownloadedSuccessfully(DriverBean driverBean);

        void dataDownloadFailed();
    }

    public AppStatusTaskListener getAppStatusTaskListener() {
        return appStatusTaskListener;
    }

    public void setAppStatusTaskListener(AppStatusTaskListener appStatusTaskListener) {
        this.appStatusTaskListener = appStatusTaskListener;
    }
}
