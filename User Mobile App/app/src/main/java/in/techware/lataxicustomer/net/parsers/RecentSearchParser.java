package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.RecentSearchBean;

public class RecentSearchParser {

    private RecentSearchBean recentSearchBean;

    public RecentSearchBean parseRecentSearchResponse(String wsResponseString) {

        recentSearchBean = new RecentSearchBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            recentSearchBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recentSearchBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                recentSearchBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        recentSearchBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        recentSearchBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        recentSearchBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        recentSearchBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    recentSearchBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    recentSearchBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    recentSearchBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                recentSearchBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    recentSearchBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    recentSearchBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        recentSearchBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                recentSearchBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                recentSearchBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("id")) {
                            recentSearchBean.setId(dataJSObject.optString("id"));
                        }
                        if (dataJSObject.has("plcae")) {
                            recentSearchBean.setPlace(dataJSObject.optString("plcae"));
                        }
                        if (dataJSObject.has("address")) {
                            recentSearchBean.setAddress(dataJSObject.optString("address"));
                        }
                        if (dataJSObject.has("latitude")) {
                            recentSearchBean.setLatitude(dataJSObject.optString("latitude"));
                        }
                        if (dataJSObject.has("longitude")) {
                            recentSearchBean.setLongitude(dataJSObject.optString("longitude"));
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
        return recentSearchBean;
    }
}