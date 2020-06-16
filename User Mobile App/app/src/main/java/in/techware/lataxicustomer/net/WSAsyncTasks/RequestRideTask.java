package in.techware.lataxicustomer.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import in.techware.lataxicustomer.model.RequestBean;
import in.techware.lataxicustomer.net.invokers.RequestRideInvoker;

public class RequestRideTask extends AsyncTask<String, Integer, RequestBean> {

    private RequestRideTaskListener requestRideTaskListener;

    private JSONObject postData;

    public RequestRideTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected RequestBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RequestRideInvoker requestRideInvoker = new RequestRideInvoker(null, postData);
        return requestRideInvoker.invokeRequestRideWS();
    }

    @Override
    protected void onPostExecute(RequestBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            requestRideTaskListener.dataDownloadedSuccessfully(result);
        else
            requestRideTaskListener.dataDownloadFailed();
    }

    public static interface RequestRideTaskListener {

        void dataDownloadedSuccessfully(RequestBean dummyBean);

        void dataDownloadFailed();
    }

    public RequestRideTaskListener getRequestRideTaskListener() {
        return requestRideTaskListener;
    }

    public void setRequestRideTaskListener(RequestRideTaskListener requestRideTaskListener) {
        this.requestRideTaskListener = requestRideTaskListener;
    }
}
