package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.OTPBean;

public class OTPSubmitParser {

    public OTPBean parseOtpSubmitResponse(String wsResponseString) {

        OTPBean otpBean = new OTPBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            otpBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                otpBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                otpBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        otpBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        otpBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        otpBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        otpBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    otpBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    otpBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    otpBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    otpBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if (dataObj.has("otp_code")) {
                            otpBean.setOtpOcde(dataObj.optString("otp_code"));
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
        return otpBean;
    }
}
