package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.PaginationBean;
import in.techware.lataxidriverapp.model.TripBean;
import in.techware.lataxidriverapp.model.TripListBean;

/**
 * Created by Jemsheer K D on 08 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class TripListParser {

    private static final String TAG = "TripListParser";

    public TripListBean parseTripListResponse(String wsResponseString) {

        TripListBean tripListBean = new TripListBean();
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
                            tripListBean.setError(errorJSObj.optString("error"));
                            tripListBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tripListBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                tripListBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        tripListBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        tripListBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        tripListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        tripListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    tripListBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    tripListBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    tripListBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                tripListBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                tripListBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                tripListBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                tripListBean.setLocationStatus("success");
            }*/

            if (jsonObj.has("meta")) {
                JSONObject metaObj = jsonObj.optJSONObject("meta");

                if (metaObj != null) {
                    if (metaObj.has("total_pages")) {
                        paginationBean.setTotalPages(metaObj.optInt("total_pages") == 0 ? 1 : metaObj.optInt("total_pages"));
                    }
                    if (metaObj.has("total")) {
                        paginationBean.setTotal(metaObj.optInt("total"));
                        paginationBean.setTotalCount(metaObj.optInt("total"));
                    }
                    if (metaObj.has("current_page")) {
                        paginationBean.setCurrentPage(metaObj.optInt("current_page") == 0 ? 1 : metaObj.optInt("current_page"));
                    }
                    if (metaObj.has("per_page")) {
                        paginationBean.setPerPage(metaObj.optInt("per_page"));
                    }
                }
                tripListBean.setPagination(paginationBean);
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {

                    if (dataObj.has("total_fare")) {
                        tripListBean.setTotalFare(dataObj.optString("total_fare"));
                    }
                    if (dataObj.has("total_online_time")) {
                        tripListBean.setTotalTimeOnline(dataObj.optString("total_online_time"));
                    }
                    if (dataObj.has("total_rides_taken")) {
                        tripListBean.setTotalRidesTaken(dataObj.optInt("total_rides_taken"));
                    }


                    if (dataObj.has("trips")) {

                        JSONArray tripsArray = dataObj.optJSONArray("trips");
                        ArrayList<TripBean> tripList = new ArrayList<>();

                        if (tripsArray != null) {
                            try {
                                TripBean tripBean = null;

                                for (int i = 0; i < tripsArray.length(); i++) {
                                    JSONObject tripObj = tripsArray.optJSONObject(i);
                                    if (tripObj != null) {
                                        tripBean = new TripBean();

                                        if (tripObj.has("id")) {
                                            tripBean.setId(tripObj.optString("id"));
                                        }
                                        if (tripObj.has("trip_status")) {
                                            tripBean.setTripStatus(tripObj.optString("trip_status"));
                                        }
                                        if (tripObj.has("driver_id")) {
                                            tripBean.setDriverID(tripObj.optString("driver_id"));
                                        }
                                        if (tripObj.has("driver_name")) {
                                            tripBean.setDriverName(tripObj.optString("driver_name"));
                                        }
                                        if (tripObj.has("driver_photo")) {
                                            tripBean.setDriverPhoto(App.getImagePath(tripObj.optString("driver_photo")));
                                        }
                                        if (tripObj.has("customer_id")) {
                                            tripBean.setCustomerID(tripObj.optString("customer_id"));
                                        }
                                        if (tripObj.has("customer_name")) {
                                            tripBean.setCustomerName(tripObj.optString("customer_name"));
                                        }
                                        if (tripObj.has("customer_photo")) {
                                            tripBean.setCustomerPhoto(App.getImagePath(tripObj.optString("customer_photo")));
                                        }
                                        if (tripObj.has("source_location")) {
                                            tripBean.setSourceLocation(tripObj.optString("source_location"));
                                        }
                                        if (tripObj.has("source_latitude")) {
                                            tripBean.setSourceLatitude(tripObj.optString("source_latitude"));
                                        }
                                        if (tripObj.has("source_longitude")) {
                                            tripBean.setSourceLongitude(tripObj.optString("source_longitude"));
                                        }
                                        if (tripObj.has("destination_location")) {
                                            tripBean.setDestinationLocation(tripObj.optString("destination_location"));
                                        }
                                        if (tripObj.has("destination_latitude")) {
                                            tripBean.setDestinationLatitude(tripObj.optString("destination_latitude"));
                                        }
                                        if (tripObj.has("destination_longitude")) {
                                            tripBean.setDestinationLongitude(tripObj.optString("destination_longitude"));
                                        }
                                        if (tripObj.has("start_time")) {
                                            tripBean.setStartTime(tripObj.optLong("start_time") * 1000);
                                        }
                                        if (tripObj.has("end_time")) {
                                            tripBean.setEndTime(tripObj.optLong("end_time") * 1000);
                                        }
                                        if (tripObj.has("fare")) {
                                            tripBean.setFare(tripObj.optString("fare"));
                                        }
                                        if (tripObj.has("fee")) {
                                            tripBean.setFee(tripObj.optString("fee"));
                                        }
                                        if (tripObj.has("tax")) {
                                            tripBean.setTax(tripObj.optString("tax"));
                                        }
                                        if (tripObj.has("estimated_payout")) {
                                            tripBean.setEstimatedPayout(tripObj.optString("estimated_payout"));
                                        }
                                        if (tripObj.has("duration")) {
                                            tripBean.setDuration(tripObj.optString("duration"));
                                        }
                                        if (tripObj.has("distance")) {
                                            tripBean.setDistance(tripObj.optString("distance"));
                                        }
                                        if (tripObj.has("rating")) {
                                            tripBean.setRating((float) tripObj.optDouble("rating"));
                                        }
                                        tripList.add(tripBean);
                                    }
                                }
                                tripListBean.setTrips(tripList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tripListBean;
    }

}
