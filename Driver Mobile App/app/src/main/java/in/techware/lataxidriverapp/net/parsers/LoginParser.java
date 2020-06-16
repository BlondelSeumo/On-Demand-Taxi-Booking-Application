package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.AuthBean;


/**
 * Created by Jemsheer K D on 03 December, 2016.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class LoginParser {

    public AuthBean parseLoginResponse(String wsResponseString) {

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
                if (jsonObj.optString("status").equals("updation success")) {
                    authBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    authBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                authBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                authBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                authBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                authBean.setStatus("success");
            }*/

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if (dataObj.has("token")) {
                            authBean.setAuthToken(dataObj.optString("token"));
                        }
                        if (dataObj.has("auth_token")) {
                            authBean.setAuthToken(dataObj.optString("auth_token"));
                        }
                        if (dataObj.has("user")) {
                            JSONObject userObj = dataObj.optJSONObject("user");

                            if (userObj != null) {
                                if (userObj.has("auth_token")) {
                                    authBean.setAuthToken(userObj.optString("auth_token"));
                                }
                                if (userObj.has("username")) {
                                    authBean.setUsername(userObj.optString("username"));
                                }
                                if (userObj.has("user_id")) {
                                    authBean.setUserID(userObj.optString("user_id"));
                                }
                                if (userObj.has("id")) {
                                    authBean.setUserID(userObj.optString("id"));
                                }
                                if (userObj.has("profile_photo")) {
                                    authBean.setProfilePhoto(App.getImagePath(userObj.optString("profile_photo")));
                                }
                                if (userObj.has("name")) {
                                    authBean.setName(userObj.optString("name"));
                                }
                                if (userObj.has("first_name")) {
                                    authBean.setFirstName(userObj.optString("first_name"));
                                }
                                if (userObj.has("last_name")) {
                                    authBean.setLastName(userObj.optString("last_name"));
                                }
                                if (userObj.has("gender")) {
                                    authBean.setGender(userObj.optString("gender"));
                                }
                                if (userObj.has("DOB")) {
                                    authBean.setDOB(userObj.optString("DOB"));
                                }
                                if (userObj.has("phone")) {
                                    authBean.setPhone(userObj.optString("phone"));
                                }
                                if (userObj.has("email")) {
                                    authBean.setEmail(userObj.optString("email"));
                                }
                                if (userObj.has("password")) {
                                    authBean.setPassword(userObj.optString("password"));
                                }
                                if (userObj.has("country")) {
                                    authBean.setCountry(userObj.optString("country"));
                                }
                                if (userObj.has("state")) {
                                    authBean.setState(userObj.optString("state"));
                                }
                                if (userObj.has("city")) {
                                    authBean.setCity(userObj.optString("city"));
                                }
                                if (userObj.has("is_all_documents_uploaded")) {
                                    authBean.setAllDocumentsUploaded(userObj.optBoolean("is_all_documents_uploaded"));
                                }
                                if (userObj.has("is_all_documents_verified")) {
                                    authBean.setAllDocumentsVerified(userObj.optBoolean("is_all_documents_verified"));
                                }
                                if (userObj.has("is_phone_verified")) {
                                    authBean.setPhoneVerified(userObj.optBoolean("is_phone_verified"));
                                }
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
        return authBean;
    }
}
