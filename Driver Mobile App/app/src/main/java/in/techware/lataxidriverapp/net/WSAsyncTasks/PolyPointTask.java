package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxidriverapp.model.PolyPointBean;
import in.techware.lataxidriverapp.net.invokers.PolyPointInvoker;

/**
 * Created by Jemsheer K D on 09 May, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class PolyPointTask extends AsyncTask<String, Integer, PolyPointBean> {

    private PolyPointTaskListener polyPointTaskListener;

    private HashMap<String, String> urlParams;

    public PolyPointTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected PolyPointBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        PolyPointInvoker polyPointInvoker = new PolyPointInvoker(urlParams, null);
        return polyPointInvoker.invokePolyPointWS();
    }

    @Override
    protected void onPostExecute(PolyPointBean result) {
        super.onPostExecute(result);
        if (result != null)
            polyPointTaskListener.dataDownloadedSuccessfully(result);
        else
            polyPointTaskListener.dataDownloadFailed();
    }

    public static interface PolyPointTaskListener {
        void dataDownloadedSuccessfully(PolyPointBean polyPointBean);

        void dataDownloadFailed();
    }

    public PolyPointTaskListener getPolyPointTaskListener() {
        return polyPointTaskListener;
    }

    public void setPolyPointTaskListener(PolyPointTaskListener polyPointTaskListener) {
        this.polyPointTaskListener = polyPointTaskListener;
    }
}
