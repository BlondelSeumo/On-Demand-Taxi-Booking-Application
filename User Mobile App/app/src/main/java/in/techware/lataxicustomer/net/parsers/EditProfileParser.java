package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.UserBean;

public class EditProfileParser {

    public UserBean parseEditProfileResponse(String wsResponseString) {

        UserBean userBean = new UserBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            userBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                userBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        userBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        userBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        userBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        userBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    userBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    userBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    userBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    userBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if (dataObj.has("edit")) {
                            JSONObject userObj = dataObj.optJSONObject("edit");

                            if (userObj != null) {

                                if (userObj.has("user_id")) {
                                    userBean.setUserID(userObj.optString("user_id"));
                                }
                                if (userObj.has("id")) {
                                    userBean.setUserID(userObj.optString("id"));
                                }
                                if (userObj.has("profile_photo")) {
                                    userBean.setProfilePhoto(App.getImagePath(userObj.optString("profile_photo")));
                                }
                                if (userObj.has("name")) {
                                    userBean.setName(userObj.optString("name"));
                                }
                                if (userObj.has("email")) {
                                    userBean.setEmail(userObj.optString("email"));
                                }
                                if (userObj.has("number")) {
                                    userBean.setMobileNumber(userObj.optString("number"));
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
        return userBean;
    }
}
