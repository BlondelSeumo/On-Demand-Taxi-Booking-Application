package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.CashCollectionInvoker;

/**
 * Created by Jemsheer K D on 12 June, 2017.
 * Package in.techware.lataxidriver.net.WSAsyncTasks
 * Project LaTaxiDriver
 */

public class CashCollectionTask extends AsyncTask<String, Integer, BasicBean> {

    private CashCollectionTaskListener cashCollectionTaskListener;

    private JSONObject postData;

    public CashCollectionTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        CashCollectionInvoker cashCollectionInvoker = new CashCollectionInvoker(null, postData);
        return cashCollectionInvoker.invokeCashCollectionWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            cashCollectionTaskListener.dataDownloadedSuccessfully(result);
        else
            cashCollectionTaskListener.dataDownloadFailed();
    }

    public static interface CashCollectionTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public CashCollectionTaskListener getCashCollectionTaskListener() {
        return cashCollectionTaskListener;
    }

    public void setCashCollectionTaskListener(CashCollectionTaskListener cashCollectionTaskListener) {
        this.cashCollectionTaskListener = cashCollectionTaskListener;
    }
}
