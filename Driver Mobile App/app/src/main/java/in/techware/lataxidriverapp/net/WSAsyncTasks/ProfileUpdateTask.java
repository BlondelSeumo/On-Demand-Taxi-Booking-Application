package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.ProfileUpdateInvoker;

/**
 * Created by Jemsheer K D on 23 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class ProfileUpdateTask extends AsyncTask<String, Integer, BasicBean> {

    private final ArrayList<String> fileList;
    private ProfileUpdateTaskListener profileUpdateTaskListener;

    private JSONObject postData;

    public ProfileUpdateTask(JSONObject postData, ArrayList<String> fileList) {
        super();
        this.postData = postData;
        this.fileList = fileList;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        ProfileUpdateInvoker profileUpdateInvoker = new ProfileUpdateInvoker(null, postData);
        return profileUpdateInvoker.invokeProfileUpdateWS(fileList);
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            profileUpdateTaskListener.dataDownloadedSuccessfully(result);
        else
            profileUpdateTaskListener.dataDownloadFailed();
    }

    public static interface ProfileUpdateTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public ProfileUpdateTaskListener getProfileUpdateTaskListener() {
        return profileUpdateTaskListener;
    }

    public void setProfileUpdateTaskListener(ProfileUpdateTaskListener profileUpdateTaskListener) {
        this.profileUpdateTaskListener = profileUpdateTaskListener;
    }
}
