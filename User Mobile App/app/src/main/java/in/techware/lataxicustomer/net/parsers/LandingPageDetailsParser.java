package in.techware.lataxicustomer.net.parsers;


import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.CarBean;
import in.techware.lataxicustomer.model.LandingPageBean;

public class LandingPageDetailsParser {

    private static final String TAG = "LPDParser";

    public LandingPageBean parseLandingPageDetailsResponse(String wsResponseString) {

        LandingPageBean landingPageBean = new LandingPageBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            landingPageBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                landingPageBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                landingPageBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        landingPageBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    landingPageBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    landingPageBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    landingPageBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                landingPageBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    landingPageBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    landingPageBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                landingPageBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                landingPageBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONArray dataArray = null;
                dataArray = jsonObj.optJSONArray("data");
                ArrayList<CarBean> carList = new ArrayList<>();

                if (dataArray != null) {

                    JSONObject carObj;
                    for (int i = 0; i < dataArray.length(); i++) {
                        carObj = dataArray.optJSONObject(i);

                        if (carObj != null) {

                            CarBean carBean = new CarBean();

                            if (carObj.has("car_ID")) {
                                carBean.setCarID(carObj.optString("car_ID"));
                            }
                            if (carObj.has("car_name")) {
                                carBean.setCarName(carObj.optString("car_name"));
                            }
                            if (carObj.has("car_image")) {
                                carBean.setCarImage(App.getImagePath(carObj.optString("car_image")));
                            }
                            if (carObj.has("eta_time")) {
                                carBean.setMinTime(carObj.optString("eta_time"));
                            }
                            if (carObj.has("min_fare")) {
                                carBean.setMinFare(carObj.optString("min_fare"));
                            }
                            if (carObj.has("max_size")) {
                                carBean.setMaxSize(carObj.optString("max_size"));
                            }

                            Log.i(TAG, "parseLandingPageDetailsResponse: " + new Gson().toJson(carBean));

                            carList.add(carBean);

                        }
                    }
                }
                landingPageBean.setCars(carList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return landingPageBean;
    }
}
