package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.HelpPageReviewInvoker;

/**
 * Created by Jemsheer K D on 15 June, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class HelpPageReviewTask extends AsyncTask<String, Integer, BasicBean> {

    private HelpPageReviewTaskListener helpPageReviewTaskListener;

    private JSONObject postData;

    public HelpPageReviewTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        HelpPageReviewInvoker helpPageReviewInvoker = new HelpPageReviewInvoker(null, postData);
        return helpPageReviewInvoker.invokeHelpPageReviewWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            helpPageReviewTaskListener.dataDownloadedSuccessfully(result);
        else
            helpPageReviewTaskListener.dataDownloadFailed();
    }

    public static interface HelpPageReviewTaskListener {
        void dataDownloadedSuccessfully(BasicBean helpPageReviewBean);

        void dataDownloadFailed();
    }

    public HelpPageReviewTaskListener getHelpPageReviewTaskListener() {
        return helpPageReviewTaskListener;
    }

    public void setHelpPageReviewTaskListener(HelpPageReviewTaskListener helpPageReviewTaskListener) {
        this.helpPageReviewTaskListener = helpPageReviewTaskListener;
    }
}
