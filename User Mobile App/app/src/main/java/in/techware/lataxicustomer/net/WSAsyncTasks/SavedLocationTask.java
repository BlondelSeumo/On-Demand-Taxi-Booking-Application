package in.techware.lataxicustomer.net.WSAsyncTasks;


import android.os.AsyncTask;

import java.util.HashMap;

import in.techware.lataxicustomer.model.LocationBean;
import in.techware.lataxicustomer.net.invokers.SavedLocationInvoker;

public class SavedLocationTask extends AsyncTask<String, Integer, LocationBean> {

    private SavedLocationTaskListener savedLocationTaskListener;

    private HashMap<String, String> urlParams;

    public SavedLocationTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected LocationBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        SavedLocationInvoker savedLocationInvoker = new SavedLocationInvoker(urlParams, null);
        return savedLocationInvoker.invokeDummyWS();
    }

    @Override
    protected void onPostExecute(LocationBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            savedLocationTaskListener.dataDownloadedSuccessfully(result);
        else
            savedLocationTaskListener.dataDownloadFailed();
    }

    public static interface SavedLocationTaskListener {

        void dataDownloadedSuccessfully(LocationBean locationBean);

        void dataDownloadFailed();
    }

    public SavedLocationTaskListener getSavedLocationTaskListener() {
        return savedLocationTaskListener;
    }

    public void setSavedLocationTaskListener(SavedLocationTaskListener savedLocationTaskListener) {
        this.savedLocationTaskListener = savedLocationTaskListener;
    }
}
