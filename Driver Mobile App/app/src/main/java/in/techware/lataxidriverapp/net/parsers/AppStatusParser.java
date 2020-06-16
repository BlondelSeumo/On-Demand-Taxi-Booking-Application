package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.AppStatusBean;

/**
 * Created by Jemsheer K D on 14 June, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class AppStatusParser {

    private static final String TAG = "AppStatusParser";

    public AppStatusBean parseAppStatusResponse(String wsResponseString) {

        AppStatusBean appStatusBean = new AppStatusBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            appStatusBean.setError(errorJSObj.optString("error"));
                            appStatusBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                appStatusBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                appStatusBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        appStatusBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        appStatusBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        appStatusBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        appStatusBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    appStatusBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    appStatusBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    appStatusBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                appStatusBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                appStatusBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                appStatusBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                appStatusBean.setLocationStatus("success");
            }*/


            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {

                    if (dataObj.has("app_status")) {
                        appStatusBean.setAppStatus(dataObj.optInt("app_status"));
                    }
                    if (dataObj.has("id")) {
                        appStatusBean.setId(dataObj.optString("id"));
                    }
                    if (dataObj.has("trip_id")) {
                        appStatusBean.setId(dataObj.optString("trip_id"));
                    }
                    if (dataObj.has("trip_status")) {
                        appStatusBean.setTripStatus(dataObj.optString("trip_status"));
                    }
                    if (dataObj.has("driver_id")) {
                        appStatusBean.setDriverID(dataObj.optString("driver_id"));
                    }
                    if (dataObj.has("driver_name")) {
                        appStatusBean.setDriverName(dataObj.optString("driver_name"));
                    }
                    if (dataObj.has("driver_photo")) {
                        appStatusBean.setDriverPhoto(App.getImagePath(dataObj.optString("driver_photo")));
                    }
                    if (dataObj.has("driver_status")) {
                        appStatusBean.setDriverStatus(dataObj.optInt("driver_status"));
                    }
                    if (dataObj.has("customer_id")) {
                        appStatusBean.setCustomerID(dataObj.optString("customer_id"));
                    }
                    if (dataObj.has("customer_name")) {
                        appStatusBean.setCustomerName(dataObj.optString("customer_name"));
                    }
                    if (dataObj.has("customer_photo")) {
                        appStatusBean.setCustomerPhoto(App.getImagePath(dataObj.optString("customer_photo")));
                    }
                    if (dataObj.has("source_location")) {
                        appStatusBean.setSourceLocation(dataObj.optString("source_location"));
                    }
                    if (dataObj.has("source_latitude")) {
                        appStatusBean.setSourceLatitude(dataObj.optString("source_latitude"));
                    }
                    if (dataObj.has("source_longitude")) {
                        appStatusBean.setSourceLongitude(dataObj.optString("source_longitude"));
                    }
                    if (dataObj.has("destination_location")) {
                        appStatusBean.setDestinationLocation(dataObj.optString("destination_location"));
                    }
                    if (dataObj.has("destination_latitude")) {
                        appStatusBean.setDestinationLatitude(dataObj.optString("destination_latitude"));
                    }
                    if (dataObj.has("destination_longitude")) {
                        appStatusBean.setDestinationLongitude(dataObj.optString("destination_longitude"));
                    }
                    if (dataObj.has("start_time")) {
                        appStatusBean.setStartTime(dataObj.optLong("start_time"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return appStatusBean;
    }


}
