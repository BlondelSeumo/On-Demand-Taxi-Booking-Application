package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

/**
 * Created by Jemsheer K D on 03 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class FCMRefreshTask extends AsyncTask<String, Integer, Boolean> {

    private FCMRefreshTaskListener fcmRefreshTaskListener;

    @Override
    protected Boolean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        try {
            if (result != null)
                fcmRefreshTaskListener.dataDownloadedSuccessfully(result);
            else
                fcmRefreshTaskListener.dataDownloadFailed();
        } catch (Exception ignored) {
        }
    }


    public interface FCMRefreshTaskListener {
        void dataDownloadedSuccessfully(Boolean result);

        void dataDownloadFailed();
    }

    public FCMRefreshTaskListener getFCMRefreshTaskListener() {
        return fcmRefreshTaskListener;
    }

    public void setFCMRefreshTaskListener(FCMRefreshTaskListener fcmRefreshTaskListener) {
        this.fcmRefreshTaskListener = fcmRefreshTaskListener;
    }
}