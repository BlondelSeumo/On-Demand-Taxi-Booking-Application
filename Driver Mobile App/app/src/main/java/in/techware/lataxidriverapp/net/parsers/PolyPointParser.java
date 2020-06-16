package in.techware.lataxidriverapp.net.parsers;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.techware.lataxidriverapp.model.PolyPointBean;


public class PolyPointParser {

    public PolyPointBean parsePolyPointResponse(String wsResponseString) {

        PolyPointBean polyPointBean = new PolyPointBean();

        ArrayList<List<HashMap<String, String>>> routes = new ArrayList<>();

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
                                polyPointBean.setErrorMsg(errorJSObj.optString("message"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    polyPointBean.setStatus("error");
                }
                if (jsonObj.has("status")) {
                    polyPointBean.setStatus(jsonObj.optString("status"));
                    if (jsonObj.optString("status").equals("error")) {
                        if (jsonObj.has("message")) {
                            polyPointBean.setErrorMsg(jsonObj.optString("message"));
                        } else {
                            polyPointBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                        }
                    }
                    if (jsonObj.optString("status").equals("500")) {
                        if (jsonObj.has("error")) {
                            polyPointBean.setErrorMsg(jsonObj.optString("error"));
                        }
                    }
                    if (jsonObj.optString("status").equals("404")) {
                        if (jsonObj.has("error")) {
                            polyPointBean.setErrorMsg(jsonObj.optString("error"));
                        }
                    }
                    if (jsonObj.has("message")) {
                        polyPointBean.setErrorMsg(jsonObj.optString("message"));
                    }
                    if (jsonObj.optString("status").equalsIgnoreCase("OK")) {
                        polyPointBean.setStatus("success");
                    }
                }
                try {
                    if (jsonObj.has("message")) {
                        polyPointBean.setWebMessage(jsonObj.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jsonObj.has("error")) {
                    polyPointBean.setErrorMsg(jsonObj.optString("error"));
                }
                if (jsonObj.has("response")) {
                    polyPointBean.setErrorMsg(jsonObj.optString("response"));
                }


                if (jsonObj.has("routes")) {

                    JSONArray jRoutes = jsonObj.getJSONArray("routes");

//            Traversing all routes */
                    for (int i = 0; i < jRoutes.length(); i++) {

                        JSONArray legsArray = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                        ArrayList<HashMap<String, String>> path = new ArrayList<>();

                        //                Traversing all legs */
                        for (int j = 0; j < legsArray.length(); j++) {

                            JSONObject legJSObj = legsArray.getJSONObject(j);

                            if (legJSObj != null) {

                                if (legJSObj.has("distance")) {
                                    polyPointBean.setDistance(legJSObj.optJSONObject("distance").optInt("value"));
                                    polyPointBean.setDistanceText(legJSObj.optJSONObject("distance").optString("text"));
                                }
                                if (legJSObj.has("duration")) {
                                    polyPointBean.setTime(legJSObj.optJSONObject("duration").optInt("value"));
                                    polyPointBean.setTimeText(legJSObj.optJSONObject("duration").optString("text"));
                                }


                                JSONArray jSteps = legJSObj.getJSONArray("steps");

                                if (jSteps != null) {
                                    /** Traversing all steps */
                                    for (int k = 0; k < jSteps.length(); k++) {
                                        String polyline = "";
                                        polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                                .get(k)).get("polyline")).get("points");
                                        ArrayList<LatLng> list = decodePoly(polyline);

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

                    polyPointBean.setRoutes(routes);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return polyPointBean;
    }

    /**
     * Method Courtesy :
     * jeffreysambells.com/2010/05/27
     * /decoding-polylines-from-google-maps-direction-api-with-java
     */
    private ArrayList<LatLng> decodePoly(String encoded) {

        ArrayList<LatLng> poly = new ArrayList<>();
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

