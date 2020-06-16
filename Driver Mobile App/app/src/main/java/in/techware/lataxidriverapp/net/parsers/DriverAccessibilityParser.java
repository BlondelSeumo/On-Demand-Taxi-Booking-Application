package in.techware.lataxidriverapp.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;


import in.techware.lataxidriverapp.model.AccessibilityBean;

public class DriverAccessibilityParser {

    public AccessibilityBean parseDriverAccessibilityResponse(String wsResponseString) {

        AccessibilityBean accessibilityBean = new AccessibilityBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            accessibilityBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                }
                accessibilityBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                accessibilityBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        accessibilityBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        accessibilityBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        accessibilityBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        accessibilityBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    accessibilityBean.setErrorMsg(jsonObj.optString("message"));
                }
            }


            if (jsonObj.has("message")) {
                accessibilityBean.setWebMessage(jsonObj.optString("message"));
            }

            if (jsonObj.has("error")) {
                accessibilityBean.setError(jsonObj.optString("error"));
            }

            if (jsonObj.has("message")) {
                accessibilityBean.setErrorMsg(jsonObj.optString("message"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {

                        if (dataJSObject.has("is_deaf")) {
                            accessibilityBean.setDeaf(dataJSObject.optBoolean("is_deaf"));
                        }
                        if (dataJSObject.has("is_flash_required_for_requests")) {
                            accessibilityBean.setFlashRequired(dataJSObject.optBoolean("is_flash_required_for_requests"));
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
        return accessibilityBean;
    }
}