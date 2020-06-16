package in.techware.lataxidriverapp.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxidriverapp.model.AuthBean;
import in.techware.lataxidriverapp.net.invokers.PhoneRegistrationInvoker;

public class PhoneRegistrationTask extends AsyncTask<String, Integer, AuthBean> {

    private PhoneRegistrationTaskListener phoneRegistrationTaskListener;

    private JSONObject postData;

    public PhoneRegistrationTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected AuthBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        PhoneRegistrationInvoker phoneRegistrationInvoker = new PhoneRegistrationInvoker(null, postData);
        return phoneRegistrationInvoker.invokePhoneRegistrationWS();
    }

    @Override
    protected void onPostExecute(AuthBean result) {
        super.onPostExecute(result);
        if (result != null)
            phoneRegistrationTaskListener.dataDownloadedSuccessfully(result);
        else
            phoneRegistrationTaskListener.dataDownloadFailed();
    }

    public static interface PhoneRegistrationTaskListener {

        void dataDownloadedSuccessfully(AuthBean authBean);

        void dataDownloadFailed();
    }

    public PhoneRegistrationTaskListener getPhoneRegistrationTaskListener() {
        return phoneRegistrationTaskListener;
    }

    public void setPhoneRegistrationTaskListener(PhoneRegistrationTaskListener phoneRegistrationTaskListener) {
        this.phoneRegistrationTaskListener = phoneRegistrationTaskListener;
    }
}
