package in.techware.lataxidriverapp.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.OTPSubmitInvoker;

public class OTPSubmitTask extends AsyncTask<String, Integer, BasicBean> {

    private OTPSubmitTask.OTPSubmitTaskListener otpSubmitTaskListener;

    private JSONObject postData;

    public OTPSubmitTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        OTPSubmitInvoker otpSubmitInvoker = new OTPSubmitInvoker(null, postData);
        return otpSubmitInvoker.invokeOTPSubmitWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            otpSubmitTaskListener.dataDownloadedSuccessfully(result);
        else
            otpSubmitTaskListener.dataDownloadFailed();
    }

    public static interface OTPSubmitTaskListener {

        void dataDownloadedSuccessfully(BasicBean otpBean);

        void dataDownloadFailed();
    }

    public OTPSubmitTaskListener getOtpSubmitTaskListener() {
        return otpSubmitTaskListener;
    }

    public void setOtpSubmitTaskListener(OTPSubmitTaskListener otpSubmitTaskListener) {
        this.otpSubmitTaskListener = otpSubmitTaskListener;
    }
}
