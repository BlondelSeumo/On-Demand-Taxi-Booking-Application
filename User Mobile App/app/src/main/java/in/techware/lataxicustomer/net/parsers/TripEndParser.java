package in.techware.lataxicustomer.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.BasicBean;

/**
 * Created by SIB-QC4 on 4/4/2017.
 */

public class TripEndParser {

    public BasicBean parseBasicResponse(String wsResponseString) {

        BasicBean basicBean = new BasicBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            basicBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                basicBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                basicBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        basicBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        basicBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        basicBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        basicBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    basicBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    basicBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    basicBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    basicBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if(dataObj.has("id")){
                            basicBean.setId(dataObj.optString("id"));
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return basicBean;
    }
}
