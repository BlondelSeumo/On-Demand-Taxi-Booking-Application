package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.CarBean;

public class CarInfoParser {

    public CarBean parseCarInfoResponse(String wsResponseString) {

        CarBean carBean = new CarBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            carBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                carBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                carBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        carBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        carBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        carBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        carBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    carBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    carBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    carBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                carBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    carBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    carBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        carBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                carBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                carBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("id")) {
                            carBean.setCarID(dataJSObject.optString("id"));
                        }
                        if (dataJSObject.has("cars_available")) {
                            carBean.setCarsAvailable(dataJSObject.optString("cars_available"));
                        }
                        if (dataJSObject.has("min_fare")) {
                            carBean.setMinFare(dataJSObject.optString("min_fare"));
                        }
                        if (dataJSObject.has("eta_time")) {
                            carBean.setMinTime(dataJSObject.optString("eta_time"));
                        }
                        if (dataJSObject.has("max_size")) {
                            carBean.setMaxSize(dataJSObject.optString("max_size"));
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
        return carBean;
    }
}




