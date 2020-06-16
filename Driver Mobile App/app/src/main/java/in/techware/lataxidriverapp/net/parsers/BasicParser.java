package in.techware.lataxidriverapp.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.model.BasicBean;

public class BasicParser {

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

                        if (dataObj.has("is_available")) {
                            basicBean.setPhoneAvailable(dataObj.optBoolean("is_available"));
                        }
                        if (dataObj.has("driver_status")) {
                            basicBean.setDriverOnline(dataObj.optBoolean("driver_status"));
                        }
                        if (dataObj.has("phone")) {
                            basicBean.setPhone(dataObj.optString("phone"));
                        }
                        if (dataObj.has("edit")) {
                            JSONObject userObj = dataObj.optJSONObject("edit");

                            if (userObj != null) {

                             /*   if (userObj.has("driver_license")) {
                                    basicBean.setDriverLicense(userObj.optString("driver_license"));
                                }*/
                            }
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
