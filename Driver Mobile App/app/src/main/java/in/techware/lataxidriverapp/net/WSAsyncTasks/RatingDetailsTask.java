package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.RatingDetailsBean;
import in.techware.lataxidriverapp.net.invokers.RatingDetailsInvoker;

/**
 * Created by Jemsheer K D on 18 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class RatingDetailsTask extends AsyncTask<String, Integer, RatingDetailsBean> {

    private RatingDetailsTaskListener ratingDetailsTaskListener;

    private HashMap<String, String> urlParams;

    public RatingDetailsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected RatingDetailsBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RatingDetailsInvoker ratingDetailsInvoker = new RatingDetailsInvoker(urlParams, null);
        return ratingDetailsInvoker.invokeRatingDetailsWS();
    }

    @Override
    protected void onPostExecute(RatingDetailsBean result) {
        super.onPostExecute(result);
        if (result != null)
            ratingDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            ratingDetailsTaskListener.dataDownloadFailed();
    }

    public static interface RatingDetailsTaskListener {
        void dataDownloadedSuccessfully(RatingDetailsBean ratingDetailsBean);

        void dataDownloadFailed();
    }

    public RatingDetailsTaskListener getRatingDetailsTaskListener() {
        return ratingDetailsTaskListener;
    }

    public void setRatingDetailsTaskListener(RatingDetailsTaskListener ratingDetailsTaskListener) {
        this.ratingDetailsTaskListener = ratingDetailsTaskListener;
    }
}
