package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.HelpListBean;
import in.techware.lataxidriverapp.net.invokers.HelpListInvoker;

/**
 * Created by Jemsheer K D on 20 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class HelpListTask extends AsyncTask<String, Integer, HelpListBean> {

    private HelpListTaskListener helpListTaskListener;

    private HashMap<String, String> urlParams;

    public HelpListTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected HelpListBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        HelpListInvoker helpListInvoker = new HelpListInvoker(urlParams, null);
        return helpListInvoker.invokeHelpListWS();
    }

    @Override
    protected void onPostExecute(HelpListBean result) {
        super.onPostExecute(result);
        if (result != null)
            helpListTaskListener.dataDownloadedSuccessfully(result);
        else
            helpListTaskListener.dataDownloadFailed();
    }

    public static interface HelpListTaskListener {
        void dataDownloadedSuccessfully(HelpListBean helpListBean);

        void dataDownloadFailed();
    }

    public HelpListTaskListener getHelpListTaskListener() {
        return helpListTaskListener;
    }

    public void setHelpListTaskListener(HelpListTaskListener helpListTaskListener) {
        this.helpListTaskListener = helpListTaskListener;
    }
}
