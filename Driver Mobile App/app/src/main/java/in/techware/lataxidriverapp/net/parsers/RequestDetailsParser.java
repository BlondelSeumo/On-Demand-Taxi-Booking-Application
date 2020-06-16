package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.PaginationBean;
import in.techware.lataxidriverapp.model.RequestDetailsBean;

/**
 * Created by Jemsheer K D on 08 June, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class RequestDetailsParser {

    private static final String TAG = "RequestDetailsParser";

    public RequestDetailsBean parseRequestDetailsResponse(String wsResponseString) {

        RequestDetailsBean requestDetailsBean = new RequestDetailsBean();
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
                            requestDetailsBean.setError(errorJSObj.optString("error"));
                            requestDetailsBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requestDetailsBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                requestDetailsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        requestDetailsBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        requestDetailsBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        requestDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        requestDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    requestDetailsBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    requestDetailsBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    requestDetailsBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                requestDetailsBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                requestDetailsBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                requestDetailsBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                requestDetailsBean.setLocationStatus("success");
            }*/


            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {

                    if (dataObj.has("request_id")) {
                        requestDetailsBean.setRequestID(dataObj.optString("request_id"));
                    }
                    if (dataObj.has("car_type")) {
                        requestDetailsBean.setCarType(dataObj.optString("car_type"));
                    }
                    if (dataObj.has("distance")) {
                        requestDetailsBean.setDistance(dataObj.optString("distance"));
                    }
                    if (dataObj.has("time")) {
                        requestDetailsBean.setEta(dataObj.optString("time"));
                    }
                    if (dataObj.has("car_type_image")) {
                        requestDetailsBean.setCarTypeImage(App.getImagePath(dataObj.optString("car_type_image")));
                    }
                    if (dataObj.has("customer_id")) {
                        requestDetailsBean.setCustomerID(dataObj.optString("customer_id"));
                    }
                    if (dataObj.has("customer_name")) {
                        requestDetailsBean.setCustomerName(dataObj.optString("customer_name"));
                    }
                    if (dataObj.has("customer_photo")) {
                        requestDetailsBean.setCustomerPhoto(App.getImagePath(dataObj.optString("customer_photo")));
                    }
                    if (dataObj.has("customer_location")) {
                        requestDetailsBean.setCustomerLocation(dataObj.optString("customer_location"));
                    }
                    if (dataObj.has("customer_latitude")) {
                        requestDetailsBean.setCustomerLatitude(dataObj.optString("customer_latitude"));
                    }
                    if (dataObj.has("customer_longitude")) {
                        requestDetailsBean.setCustomerLongitude(dataObj.optString("customer_longitude"));
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return requestDetailsBean;
    }


}
