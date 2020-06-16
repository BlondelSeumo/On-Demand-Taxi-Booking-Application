package in.techware.lataxicustomer.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxicustomer.model.BasicBean;
import in.techware.lataxicustomer.net.invokers.UpdateFCMTokenInvoker;

/**
 * Created by SIB-QC4 on 4/4/2017.
 */

public class UpdateFCMTokenTask extends AsyncTask<String, Integer, BasicBean> {

    private UpdateFCMTokenTaskListener updateFCMTokenTaskListener;

    private JSONObject postData;

    public UpdateFCMTokenTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        UpdateFCMTokenInvoker updateFCMTokenInvoker = new UpdateFCMTokenInvoker(null, postData);
        return updateFCMTokenInvoker.invokeUpdateFCMTokenWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            updateFCMTokenTaskListener.dataDownloadedSuccessfully(result);
        else
            updateFCMTokenTaskListener.dataDownloadFailed();
    }

    public static interface UpdateFCMTokenTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public UpdateFCMTokenTaskListener getUpdateFCMTokenTaskListener() {
        return updateFCMTokenTaskListener;
    }

    public void setUpdateFCMTokenTaskListener(UpdateFCMTokenTaskListener updateFCMTokenTaskListener) {
        this.updateFCMTokenTaskListener = updateFCMTokenTaskListener;
    }
}
