package in.techware.lataxicustomer.net.parsers;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxicustomer.model.PolyPointsBean;

public class PolyPointsParser {

    public PolyPointsBean parsePolyPointsResponse(String wsResponseString) {

        PolyPointsBean polyPointsBean = new PolyPointsBean();

        List<List<HashMap<String, String>>> routes = new ArrayList<>();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj != null) {


                if (jsonObj.has("error")) {
                    JSONObject errorJSObj;
                    try {
                        errorJSObj = jsonObj.getJSONObject("error");
                        if (errorJSObj != null) {
                            if (errorJSObj.has("message")) {
                                polyPointsBean.setErrorMsg(errorJSObj.optString("message"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    polyPointsBean.setStatus("error");
                }
                if (jsonObj.has("status")) {
                    polyPointsBean.setStatus(jsonObj.optString("status"));
                    if (jsonObj.optString("status").equals("error")) {
                        if (jsonObj.has("message")) {
                            polyPointsBean.setErrorMsg(jsonObj.optString("message"));
                        } else {
                            polyPointsBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                        }
                    }
                    if (jsonObj.optString("status").equals("500")) {
                        if (jsonObj.has("error")) {
                            polyPointsBean.setErrorMsg(jsonObj.optString("error"));
                        }
                    }
                    if (jsonObj.optString("status").equals("404")) {
                        if (jsonObj.has("error")) {
                            polyPointsBean.setErrorMsg(jsonObj.optString("error"));
                        }
                    }
                    if (jsonObj.has("message")) {
                        polyPointsBean.setErrorMsg(jsonObj.optString("message"));
                    }
                    if (jsonObj.optString("status").equalsIgnoreCase("OK")) {
                        polyPointsBean.setStatus("success");
                    }
                }
                try {
                    if (jsonObj.has("message")) {
                        polyPointsBean.setWebMessage(jsonObj.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jsonObj.has("error")) {
                    polyPointsBean.setErrorMsg(jsonObj.optString("error"));
                }
                if (jsonObj.has("response")) {
                    polyPointsBean.setErrorMsg(jsonObj.optString("response"));
                }


                if (jsonObj.has("routes")) {

                    JSONArray jRoutes = jsonObj.getJSONArray("routes");

//            Traversing all routes */
                    for (int i = 0; i < jRoutes.length(); i++) {

                        JSONArray legsArray = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                        List<HashMap<String, String>> path = new ArrayList<>();

                        //                Traversing all legs */
                        for (int j = 0; j < legsArray.length(); j++) {

                            JSONObject legJSObj = legsArray.getJSONObject(j);

                            if (legJSObj != null) {

                                if (legJSObj.has("distance")) {
                                    polyPointsBean.setDistance(legJSObj.optJSONObject("distance").optInt("value"));
                                    polyPointsBean.setDistanceText(legJSObj.optJSONObject("distance").optString("text"));
                                }
                                if (legJSObj.has("duration")) {
                                    polyPointsBean.setTime(legJSObj.optJSONObject("duration").optInt("value"));
                                    polyPointsBean.setTimeText(legJSObj.optJSONObject("duration").optString("text"));
                                }


                                JSONArray jSteps = legJSObj.getJSONArray("steps");

                                if (jSteps != null) {
                                    /** Traversing all steps */
                                    for (int k = 0; k < jSteps.length(); k++) {
                                        String polyline = "";
                                        polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                                .get(k)).get("polyline")).get("points");
                                        List<LatLng> list = decodePoly(polyline);

                                        /** Traversing all points */
                                        for (int l = 0; l < list.size(); l++) {
                                            HashMap<String, String> hm = new HashMap<>();
                                            hm.put("lat",
                                                    Double.toString((list.get(l)).latitude));
                                            hm.put("lng",
                                                    Double.toString((list.get(l)).longitude));
                                            path.add(hm);
                                        }
                                    }
                                    routes.add(path);
                                }
                            }
                        }
                    }

                    polyPointsBean.setRoutes(routes);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return polyPointsBean;
    }

    /**
     * Method Courtesy :
     * jeffreysambells.com/2010/05/27
     * /decoding-polylines-from-google-maps-direction-api-with-java
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;

    }
}

