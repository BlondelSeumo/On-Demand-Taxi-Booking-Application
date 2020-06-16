package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.model.RatingDetailsBean;

/**
 * Created by Jemsheer K D on 18 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class RatingDetailsParser {

    public RatingDetailsBean parseRatingDetailsResponse(String wsResponseString) {

        RatingDetailsBean ratingDetailsBean = new RatingDetailsBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            ratingDetailsBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                }
                ratingDetailsBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                ratingDetailsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        ratingDetailsBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        ratingDetailsBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        ratingDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        ratingDetailsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    ratingDetailsBean.setErrorMsg(jsonObj.optString("message"));
                }
            }


            if (jsonObj.has("message")) {
                ratingDetailsBean.setWebMessage(jsonObj.optString("message"));
            }

            if (jsonObj.has("error")) {
                ratingDetailsBean.setError(jsonObj.optString("error"));
            }

            if (jsonObj.has("message")) {
                ratingDetailsBean.setErrorMsg(jsonObj.optString("message"));
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {
                        if (dataObj.has("total_ratings")) {
                            ratingDetailsBean.setTotalRating(dataObj.optInt("total_ratings"));
                        }
                        if (dataObj.has("average_rating")) {
                            if (dataObj.optString("average_rating").equalsIgnoreCase("null"))
                                ratingDetailsBean.setAverageRatings(0f);
                            else
                                ratingDetailsBean.setAverageRatings((float) dataObj.optDouble("average_rating"));
                        }
                        if (dataObj.has("total_requests")) {
                            ratingDetailsBean.setTotalRequests(dataObj.optInt("total_requests"));
                        }
                        if (dataObj.has("requests_accepted")) {
                            ratingDetailsBean.setRequestsAccepted(dataObj.optInt("requests_accepted"));
                        }
                        if (dataObj.has("total_trips")) {
                            ratingDetailsBean.setTotalTrips(dataObj.optInt("total_trips"));
                        }
                        if (dataObj.has("trips_cancelled")) {
                            ratingDetailsBean.setTripsCancelled(dataObj.optInt("trips_cancelled"));
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
        return ratingDetailsBean;
    }

}
