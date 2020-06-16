package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.LocationBean;

public class SavedLocationParser {

    public LocationBean parseSavedLocationResponse(String wsResponseString) {

        LocationBean locationBean = new LocationBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            locationBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                locationBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                locationBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        locationBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        locationBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        locationBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        locationBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    locationBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    locationBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    locationBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                locationBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    locationBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    locationBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        locationBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                locationBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                locationBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("home")) {
                            locationBean.setHomeLocation(
                                    dataJSObject.optString("home").equalsIgnoreCase("Null")
                                            ? "" : dataJSObject.optString("home"));
                        }
                        if (dataJSObject.has("work")) {
                            locationBean.setWorkLocation(
                                    dataJSObject.optString("work").equalsIgnoreCase("Null")
                                            ? "" : dataJSObject.optString("work"));
                        }
                        if (dataJSObject.has("home_latitude")) {
                            locationBean.setHomeLatitude(
                                    dataJSObject.optString("home_latitude").equalsIgnoreCase("Null")
                                            ? "" : dataJSObject.optString("home_latitude"));
                        }
                        if (dataJSObject.has("home_longitude")) {
                            locationBean.setHomeLongitude(
                                    dataJSObject.optString("home_longitude").equalsIgnoreCase("Null")
                                            ? "" : dataJSObject.optString("home_longitude"));
                        }
                        if (dataJSObject.has("work_latitude")) {
                            locationBean.setWorkLatitude(
                                    dataJSObject.optString("work_latitude").equalsIgnoreCase("Null")
                                            ? "" : dataJSObject.optString("work_latitude"));
                        }
                        if (dataJSObject.has("work_longitude")) {
                            locationBean.setWorkLongitude(
                                    dataJSObject.optString("work_longitude").equalsIgnoreCase("Null")
                                            ? "" : dataJSObject.optString("work_longitude"));
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
        return locationBean;
    }
}
