package in.techware.lataxicustomer.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jemsheer K D on 03 December, 2016.
 * Package com.company.sample.net.invokers
 * Project TeluguCatholicMatrimony
 */

public class BaseInvoker {
    HashMap<String, String> urlParams;
    JSONObject postData;


    BaseInvoker(HashMap<String, String> urlParams, JSONObject postData) {
        super();
        this.urlParams = urlParams;
        this.postData = postData;
    }

    BaseInvoker() {
        super();

    }

    public HashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(HashMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public JSONObject getPostData() {
        return postData;
    }

    public void setPostData(JSONObject postData) {
        this.postData = postData;
    }
}
