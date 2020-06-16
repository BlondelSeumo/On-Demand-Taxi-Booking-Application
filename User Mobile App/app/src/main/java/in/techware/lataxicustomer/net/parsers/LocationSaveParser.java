package in.techware.lataxicustomer.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.LocationBean;

public class LocationSaveParser {

    public LocationBean parseLocationSaveResponse(String wsResponseString) {

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
                if (jsonObj.optString("status").equals("notfound"))
                    locationBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    locationBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    locationBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {

                        if (dataObj.has("home")) {
                            locationBean.setHomeLocation(dataObj.optString("home"));
                        }
                        if (dataObj.has("work")) {
                            locationBean.setWorkLocation(dataObj.optString("work"));
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


