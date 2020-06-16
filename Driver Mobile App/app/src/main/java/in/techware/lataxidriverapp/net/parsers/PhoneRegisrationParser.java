package in.techware.lataxidriverapp.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.model.AuthBean;

public class PhoneRegisrationParser {

    public AuthBean parsePhoneRegistrationResponse(String wsResponseString) {

        AuthBean authBean = new AuthBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            authBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                authBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                authBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        authBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        authBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        authBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        authBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    authBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    authBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    authBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    authBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {
                        if (dataObj.has("auth_token")) {
                            authBean.setAuthToken(dataObj.optString("auth_token"));
                        }
                        if (dataObj.has("phone_number")) {
                            authBean.setAuthToken(dataObj.optString("phone_number"));
                        }
                        if (dataObj.has("phone_code")) {
                            authBean.setAuthToken(dataObj.optString("phone_code"));
                        }
                        /*if (dataObj.has("user")) {
                            JSONObject userObj = dataObj.optJSONObject("user");

                            if (userObj != null) {
                                if (userObj.has("auth_token")) {
                                    authBean.setAuthToken(userObj.optString("auth_token"));
                                }
                            }
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return authBean;
    }
}

