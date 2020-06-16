package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.PaginationBean;
import in.techware.lataxidriverapp.model.TripBean;

/**
 * Created by Jemsheer K D on 03 July, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class TripDetailsParser {

    private static final String TAG = "TripDetailsParser";

    public TripBean parseTripDetailsResponse(String wsResponseString) {

        TripBean tripBean = new TripBean();
        PaginationBean paginationBean = new PaginationBean();


        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            tripBean.setError(errorJSObj.optString("error"));
                            tripBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tripBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                tripBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        tripBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        tripBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        tripBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    tripBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    tripBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    tripBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                tripBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                tripBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                tripBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                tripBean.setLocationStatus("success");
            }*/


            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {

                    if (dataObj.has("id")) {
                        tripBean.setId(dataObj.optString("id"));
                    }
                    if (dataObj.has("trip_status")) {
                        tripBean.setTripStatus(dataObj.optString("trip_status"));
                    }
                    if (dataObj.has("driver_id")) {
                        tripBean.setDriverID(dataObj.optString("driver_id"));
                    }
                    if (dataObj.has("driver_name")) {
                        tripBean.setDriverName(dataObj.optString("driver_name"));
                    }
                    if (dataObj.has("driver_photo")) {
                        tripBean.setDriverPhoto(App.getImagePath(dataObj.optString("driver_photo")));
                    }
                    if (dataObj.has("customer_id")) {
                        tripBean.setCustomerID(dataObj.optString("customer_id"));
                    }
                    if (dataObj.has("customer_name")) {
                        tripBean.setCustomerName(dataObj.optString("customer_name"));
                    }
                    if (dataObj.has("customer_photo")) {
                        tripBean.setCustomerPhoto(App.getImagePath(dataObj.optString("customer_photo")));
                    }
                    if (dataObj.has("source_location")) {
                        tripBean.setSourceLocation(dataObj.optString("source_location"));
                    }
                    if (dataObj.has("source_latitude")) {
                        tripBean.setSourceLatitude(dataObj.optString("source_latitude"));
                    }
                    if (dataObj.has("source_longitude")) {
                        tripBean.setSourceLongitude(dataObj.optString("source_longitude"));
                    }
                    if (dataObj.has("destination_location")) {
                        tripBean.setDestinationLocation(dataObj.optString("destination_location"));
                    }
                    if (dataObj.has("destination_latitude")) {
                        tripBean.setDestinationLatitude(dataObj.optString("destination_latitude"));
                    }
                    if (dataObj.has("destination_longitude")) {
                        tripBean.setDestinationLongitude(dataObj.optString("destination_longitude"));
                    }
                    if (dataObj.has("start_time")) {
                        tripBean.setStartTime(dataObj.optLong("start_time") * 1000);
                    }
                    if (dataObj.has("end_time")) {
                        tripBean.setEndTime(dataObj.optLong("end_time") * 1000);
                    }
                    if (dataObj.has("fare")) {
                        tripBean.setFare(dataObj.optString("fare"));
                    }
                    if (dataObj.has("fee")) {
                        tripBean.setFee(dataObj.optString("fee"));
                    }
                    if (dataObj.has("tax")) {
                        tripBean.setTax(dataObj.optString("tax"));
                    }
                    if (dataObj.has("estimated_payout")) {
                        tripBean.setEstimatedPayout(dataObj.optString("estimated_payout"));
                    }
                    if (dataObj.has("duration")) {
                        tripBean.setDuration(dataObj.optString("duration"));
                    }
                    if (dataObj.has("distance")) {
                        tripBean.setDistance(dataObj.optString("distance"));
                    }
                    if (dataObj.has("rating")) {
                        tripBean.setRating((float) dataObj.optDouble("rating"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tripBean;
    }


}
