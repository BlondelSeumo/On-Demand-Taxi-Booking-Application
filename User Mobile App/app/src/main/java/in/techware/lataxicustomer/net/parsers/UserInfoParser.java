package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.UserBean;

public class UserInfoParser {

    public UserBean parseUserInfoResponse(String wsResponseString) {

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
                if (jsonObj.optString("status").equals("updation success")) {
                    userBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    userBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                userBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    userBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    userBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        userBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                userBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                userBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("id")) {
                            userBean.setUserID(dataJSObject.optString("id"));
                        }
                        if (dataJSObject.has("name")) {
                            userBean.setName(dataJSObject.optString("name"));
                        }
                        if (dataJSObject.has("email")) {
                            userBean.setEmail(dataJSObject.optString("email"));
                        }
                        if (dataJSObject.has("mobile_number")) {
                            userBean.setMobileNumber(dataJSObject.optString("mobile_number"));
                        }
                        if (dataJSObject.has("mobile_code")) {
                            userBean.setMobileCode(dataJSObject.optString("mobile_code"));
                        }
                        if (dataJSObject.has("profile_photo")) {
                            userBean.setProfilePhoto(App.getImagePath(dataJSObject.optString("profile_photo")));
                        }
                        if (dataJSObject.has("work")) {
                            userBean.setAddWork(dataJSObject.optString("work"));
                        }
                        if (dataJSObject.has("home")) {
                            userBean.setAddHome(dataJSObject.optString("home"));
                        }
                        if (dataJSObject.has("home_latitude")) {
                            userBean.setHomeLatitude(dataJSObject.optString("home_latitude"));
                        }
                        if (dataJSObject.has("home_longitude")) {
                            userBean.setHomeLongitude(dataJSObject.optString("home_longitude"));
                        }
                        if (dataJSObject.has("work_latitude")) {
                            userBean.setWorkLatitude(dataJSObject.optString("work_latitude"));
                        }
                        if (dataJSObject.has("work_longitude")) {
                            userBean.setWorkLongitude(dataJSObject.optString("work_longitude"));
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
