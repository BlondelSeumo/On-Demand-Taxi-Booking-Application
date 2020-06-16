package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.MobileAvailabilityCheckInvoker;

/**
 * Created by Jemsheer K D on 24 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class MobileAvailabilityCheckTask extends AsyncTask<String, Integer, BasicBean> {

    private MobileAvailabilityCheckTaskListener mobileAvailabilityCheckTaskListener;

    private JSONObject postData;

    public MobileAvailabilityCheckTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        MobileAvailabilityCheckInvoker mobileAvailabilityCheckInvoker = new MobileAvailabilityCheckInvoker(null, postData);
        return mobileAvailabilityCheckInvoker.invokeMobileAvailabilityCheckWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            mobileAvailabilityCheckTaskListener.dataDownloadedSuccessfully(result);
        else
            mobileAvailabilityCheckTaskListener.dataDownloadFailed();
    }

    public static interface MobileAvailabilityCheckTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public MobileAvailabilityCheckTaskListener getMobileAvailabilityCheckTaskListener() {
        return mobileAvailabilityCheckTaskListener;
    }

    public void setMobileAvailabilityCheckTaskListener(MobileAvailabilityCheckTaskListener mobileAvailabilityCheckTaskListener) {
        this.mobileAvailabilityCheckTaskListener = mobileAvailabilityCheckTaskListener;
    }
}
