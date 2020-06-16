package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxidriverapp.model.AuthBean;
import in.techware.lataxidriverapp.net.invokers.RegistrationInvoker;

/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class RegistrationTask extends AsyncTask<String, Integer, AuthBean> {

    private RegistrationTaskListener registrationTaskListener;

    private JSONObject postData;

    public RegistrationTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected AuthBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RegistrationInvoker registrationInvoker = new RegistrationInvoker(null, postData);
        return registrationInvoker.invokeRegistrationWS();
    }

    @Override
    protected void onPostExecute(AuthBean result) {
        super.onPostExecute(result);
        if (result != null)
            registrationTaskListener.dataDownloadedSuccessfully(result);
        else
            registrationTaskListener.dataDownloadFailed();
    }

    public static interface RegistrationTaskListener {
        void dataDownloadedSuccessfully(AuthBean authBean);

        void dataDownloadFailed();
    }

    public RegistrationTaskListener getRegistrationTaskListener() {
        return registrationTaskListener;
    }

    public void setRegistrationTaskListener(RegistrationTaskListener registrationTaskListener) {
        this.registrationTaskListener = registrationTaskListener;
    }
}
