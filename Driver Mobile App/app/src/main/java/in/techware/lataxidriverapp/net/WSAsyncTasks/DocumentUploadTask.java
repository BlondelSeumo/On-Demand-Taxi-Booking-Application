package in.techware.lataxidriverapp.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.invokers.DocumentUploadInvoker;

public class DocumentUploadTask extends AsyncTask<String, Integer, BasicBean> {

    private final ArrayList<String> fileList;
    private JSONObject postData;

    private DocumentUploadTaskListener documentUploadTaskListener;

    public DocumentUploadTask(JSONObject postData, ArrayList<String> fileList) {
        super();
        this.postData = postData;
        this.fileList = fileList;
    }

    @Override
    protected BasicBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        DocumentUploadInvoker documentUploadInvoker = new DocumentUploadInvoker(null, postData);
        return documentUploadInvoker.invokeDocumentUploadWS(fileList);

    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);

        if (result != null)
            documentUploadTaskListener.dataDownloadedSuccessfully(result);
        else
            documentUploadTaskListener.dataDownloadFailed();
    }

    public interface DocumentUploadTaskListener {

        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public DocumentUploadTaskListener getDocumentUploadTaskListener() {
        return documentUploadTaskListener;
    }

    public void setDocumentUploadTaskListener(DocumentUploadTaskListener documentUploadTaskListener) {
        this.documentUploadTaskListener = documentUploadTaskListener;
    }
}
