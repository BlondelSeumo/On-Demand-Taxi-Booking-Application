package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.TripDetailsBean;

public class TripDetailsParser {

    public TripDetailsBean parseTripDetailsResponse(String wsResponseString) {

        TripDetailsBean tripDetailsBean = new TripDetailsBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            tripDetailsBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tripDetailsBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                tripDetailsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        tripDetailsBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        tripDetailsBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        tripDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    tripDetailsBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    tripDetailsBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    tripDetailsBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                tripDetailsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    tripDetailsBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    tripDetailsBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                tripDetailsBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                tripDetailsBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("id")) {
                            tripDetailsBean.setId(dataJSObject.optString("id"));
                        }
                        if (dataJSObject.has("trip_status")) {
                            tripDetailsBean.setTripStatus(dataJSObject.optString("trip_status"));
                        }
                        if (dataJSObject.has("profile_photo")) {
                            tripDetailsBean.setDriverPhoto(App.getImagePath(dataJSObject.optString("profile_photo")));
                        }
                        if (dataJSObject.has("time")) {
                            tripDetailsBean.setTime(dataJSObject.optString("time"));
                        }
                        if (dataJSObject.has("rating")) {
                            tripDetailsBean.setRating((float) dataJSObject.optDouble("rating"));
                        }
                        if (dataJSObject.has("driver_name")) {
                            tripDetailsBean.setDriverName(dataJSObject.optString("driver_name"));
                        }
                        if (dataJSObject.has("kilometers")) {
                            tripDetailsBean.setKilometer(dataJSObject.optString("kilometers"));
                        }
                        if (dataJSObject.has("minutes")) {
                            tripDetailsBean.setMinute(dataJSObject.optString("minutes"));
                        }
                        if (dataJSObject.has("base_fare")) {
                            tripDetailsBean.setBaseFare(dataJSObject.optString("base_fare"));
                        }
                        if (dataJSObject.has("kilometer_fare")) {
                            tripDetailsBean.setKilometerFare(dataJSObject.optString("kilometer_fare"));
                        }
                        if (dataJSObject.has("minutes_fare")) {
                            tripDetailsBean.setMinutesFare(dataJSObject.optString("minutes_fare"));
                        }
                        if (dataJSObject.has("sub_total_fare")) {
                            tripDetailsBean.setSubTotalFare(dataJSObject.optString("sub_total_fare"));
                        }
                        if (dataJSObject.has("promotion_fare")) {
                            tripDetailsBean.setPromotionFare(dataJSObject.optString("promotion_fare"));
                        }
                        if (dataJSObject.has("total_fare")) {
                            tripDetailsBean.setTotalFare(dataJSObject.optString("total_fare"));
                        }
                        if (dataJSObject.has("source_latitude")) {
                            tripDetailsBean.setSourceLatitude(dataJSObject.optString("source_latitude"));
                        }
                        if (dataJSObject.has("source_longitude")) {
                            tripDetailsBean.setSourceLongitude(dataJSObject.optString("source_longitude"));
                        }
                        if (dataJSObject.has("destination_latitude")) {
                            tripDetailsBean.setDestinationLatitude(dataJSObject.optString("destination_latitude"));
                        }
                        if (dataJSObject.has("destination_longitude")) {
                            tripDetailsBean.setDestinationLongitude(dataJSObject.optString("destination_longitude"));
                        }
                        if (dataJSObject.has("source_name")) {
                            tripDetailsBean.setSourceName(dataJSObject.optString("source_name"));
                        }
                        if (dataJSObject.has("destination_name")) {
                            tripDetailsBean.setDestinationName(dataJSObject.optString("destination_name"));
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
        return tripDetailsBean;
    }
}
