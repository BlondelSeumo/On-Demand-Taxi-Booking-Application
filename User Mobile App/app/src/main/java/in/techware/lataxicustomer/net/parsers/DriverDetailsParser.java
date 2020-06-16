package in.techware.lataxicustomer.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.DriverBean;


public class DriverDetailsParser {

    public DriverBean parseDriverDetailsResponse(String wsResponseString) {

        DriverBean driverBean = new DriverBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            driverBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                driverBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                driverBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        driverBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        driverBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        driverBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        driverBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    driverBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    driverBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    driverBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                driverBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    driverBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    driverBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        driverBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                driverBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                driverBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONObject dataObj = null;
                dataObj = jsonObj.optJSONObject("data");

                if (dataObj != null) {
                    try {
                        if (dataObj.has("app_status")) {
                            driverBean.setAppStatus(dataObj.optInt("app_status"));
                        }
                        if (dataObj.has("request_status")) {
                            driverBean.setRequestStatus(dataObj.optString("request_status"));
                        }
                        if (dataObj.has("trip_id")) {
                            driverBean.setTripID(dataObj.optString("trip_id"));
                        }
                        if (dataObj.has("car_photo")) {
                            driverBean.setCarPhoto(App.getImagePath(dataObj.optString("car_photo")));
                        }
                        if (dataObj.has("driver_name")) {
                            driverBean.setDriverName(dataObj.optString("driver_name"));
                        }
                        if (dataObj.has("driver_photo")) {
                            driverBean.setDriverPhoto(App.getImagePath(dataObj.optString("driver_photo")));
                        }
                        if (dataObj.has("driver_number")) {
                            driverBean.setDriverNumber(dataObj.optString("driver_number"));
                        }
                        if (dataObj.has("car_name")) {
                            driverBean.setCarName(dataObj.optString("car_name"));
                        }
                        if (dataObj.has("car_number")) {
                            driverBean.setCarNumber(dataObj.optString("car_number"));
                        }
                        if (dataObj.has("rating")) {
                            driverBean.setRating((float) dataObj.optDouble("rating", 0));
                        }
                        if (dataObj.has("time")) {
                            driverBean.setTime(dataObj.optString("time"));
                        }
                        if (dataObj.has("source_latitude")) {
                            driverBean.setSourceLatitude(dataObj.optString("source_latitude"));
                        }
                        if (dataObj.has("source_longitude")) {
                            driverBean.setSourceLongitude(dataObj.optString("source_longitude"));
                        }
                        if (dataObj.has("destination_latitude")) {
                            driverBean.setDestinationLatitude(dataObj.optString("destination_latitude"));
                        }
                        if (dataObj.has("destination_longitude")) {
                            driverBean.setDestinationLongitude(dataObj.optString("destination_longitude"));
                        }
                        if (dataObj.has("car_latitude")) {
                            driverBean.setCarLatitude(dataObj.optString("car_latitude"));
                        }
                        if (dataObj.has("car_longitude")) {
                            driverBean.setCarLongitude(dataObj.optString("car_longitude"));
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
        return driverBean;
    }
}
