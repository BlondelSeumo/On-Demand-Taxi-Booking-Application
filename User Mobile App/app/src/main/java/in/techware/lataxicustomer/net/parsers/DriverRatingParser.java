package in.techware.lataxicustomer.net.parsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.DriverRatingBean;

public class DriverRatingParser {

    public DriverRatingBean parseDriverRatingResponse(String wsResponseString) {

        DriverRatingBean driverRatingBean = new DriverRatingBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            driverRatingBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                driverRatingBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                driverRatingBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        driverRatingBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        driverRatingBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        driverRatingBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        driverRatingBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    driverRatingBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("notfound"))
                    driverRatingBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    driverRatingBean.setErrorMsg("Password Is Incorrect");
            }
            try {
                if (jsonObj.has("message")) {
                    driverRatingBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                JSONArray dataArray = jsonObj.optJSONArray("data");

                if (dataObj != null) {
                    try {

                        if (dataObj.has("rating")) {
                            driverRatingBean.setRating(dataObj.optString("rating"));
                        }
                        if (dataObj.has("feedback")) {
                            driverRatingBean.setRating(dataObj.optString("feedback"));
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
        return driverRatingBean;
    }
}
