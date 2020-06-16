package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.SuccessBean;

public class SuccessDetailsParser {

    public SuccessBean parseSuccessDetailsResponse(String wsResponseString) {

        SuccessBean successBean = new SuccessBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            successBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                successBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                successBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        successBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        successBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        successBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        successBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    successBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    successBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    successBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                successBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    successBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    successBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        successBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                successBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                successBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("driver_photo")) {
                            successBean.setDriverPhoto(App.getImagePath(dataJSObject.optString("driver_photo")));
                        }
                        if (dataJSObject.has("driver_name")) {
                            successBean.setDriverName(dataJSObject.optString("driver_name"));
                        }
                        if (dataJSObject.has("time")) {
                            successBean.setTime(dataJSObject.optLong("time"));
                        }
                        if (dataJSObject.has("fare")) {
                            successBean.setFare(dataJSObject.optString("fare"));
                        }
                        if (dataJSObject.has("Source")) {
                            successBean.setSource(dataJSObject.optString("Source"));
                        }
                        if (dataJSObject.has("destination")) {
                            successBean.setDestination(dataJSObject.optString("destination"));
                        }
                        if (dataJSObject.has("source_latitude")) {
                            successBean.setSourceLatitude(dataJSObject.optString("source_latitude"));
                        }
                        if (dataJSObject.has("source_longitude")) {
                            successBean.setSourceLongitude(dataJSObject.optString("source_longitude"));
                        }
                        if (dataJSObject.has("destination_latitude")) {
                            successBean.setDestinationLatitude(dataJSObject.optString("destination_latitude"));
                        }
                        if (dataJSObject.has("destination_longitude")) {
                            successBean.setDestinationLongitude(dataJSObject.optString("destination_longitude"));
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
        return successBean;
    }
}
