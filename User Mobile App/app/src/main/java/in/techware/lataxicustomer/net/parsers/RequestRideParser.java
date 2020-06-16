package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.RequestBean;

public class RequestRideParser {

    public RequestBean parseRequestRideResponse(String wsResponseString) {

        RequestBean requestBean = new RequestBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            requestBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requestBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                requestBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        requestBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        requestBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        requestBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        requestBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    requestBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    requestBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    requestBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                requestBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    requestBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    requestBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        requestBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                requestBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                requestBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("id")) {
                            requestBean.setId(dataJSObject.optString("id"));
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
        return requestBean;

    }
}
