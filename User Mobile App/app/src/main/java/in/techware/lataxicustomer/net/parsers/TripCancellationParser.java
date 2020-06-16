package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.TripCancellationBean;

public class TripCancellationParser {

    public TripCancellationBean parseTripCancellation(String wsResponseString) {

        TripCancellationBean tripCancellationBean = new TripCancellationBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            tripCancellationBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tripCancellationBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                tripCancellationBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        tripCancellationBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        tripCancellationBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripCancellationBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        tripCancellationBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    tripCancellationBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    tripCancellationBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    tripCancellationBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                tripCancellationBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    tripCancellationBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    tripCancellationBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripCancellationBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                tripCancellationBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                tripCancellationBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("trip_ID")) {
//                            tripCancellationBean.setFeedBackType(dataJSObject.optString("feedback_type"));
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
        return tripCancellationBean;
    }
}
