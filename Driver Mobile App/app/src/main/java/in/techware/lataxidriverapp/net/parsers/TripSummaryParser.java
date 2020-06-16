package in.techware.lataxidriverapp.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.model.TripSummaryBean;

public class TripSummaryParser {

    public TripSummaryBean parseTripSummaryResponse(String wsResponseString) {

        TripSummaryBean tripSummaryBean = new TripSummaryBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            tripSummaryBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tripSummaryBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                tripSummaryBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        tripSummaryBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        tripSummaryBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripSummaryBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        tripSummaryBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    tripSummaryBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    tripSummaryBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    tripSummaryBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                tripSummaryBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    tripSummaryBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    tripSummaryBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripSummaryBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                tripSummaryBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                tripSummaryBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONObject dataObj = null;
                dataObj = jsonObj.optJSONObject("data");

                if (dataObj != null) {
                    try {

                        if (dataObj.has("trip_status")) {
                            tripSummaryBean.setTripStatus(dataObj.optInt("trip_status"));
                        }
                        if (dataObj.has("estimated_payout")) {
                            tripSummaryBean.setBaseFare(dataObj.optString("estimated_payout"));
                        }
                        if (dataObj.has("fee")) {
                            tripSummaryBean.setLaTaxiFee(dataObj.optString("fee"));
                        }
                        if (dataObj.has("tax")) {
                            tripSummaryBean.setTax(dataObj.optString("tax"));
                        }
                        if (dataObj.has("fare")) {
                            tripSummaryBean.setTotalFare(dataObj.optString("fare"));
                        }
                        if (dataObj.has("discount")) {
                            tripSummaryBean.setDiscount(dataObj.optString("discount"));
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
        return tripSummaryBean;
    }
}

