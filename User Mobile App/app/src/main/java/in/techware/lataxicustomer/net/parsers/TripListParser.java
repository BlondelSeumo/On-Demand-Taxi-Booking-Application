package in.techware.lataxicustomer.net.parsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxicustomer.app.App;
import in.techware.lataxicustomer.model.PaginationBean;
import in.techware.lataxicustomer.model.TripBean;
import in.techware.lataxicustomer.model.TripListBean;

public class TripListParser {

    private TripListBean tripListBean;
    private PaginationBean paginationBean;

    public TripListBean parseTripListResponse(String wsResponseString) {

        tripListBean = new TripListBean();
        paginationBean = new PaginationBean();

        try {
            JSONObject jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
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
            } else {
                tripListBean.setStatus("success");
            }
            try {
                if (jsonObj.has("message")) {
                    tripListBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (jsonObj.has("data")) {
                JSONArray dataArray = null;
                dataArray = jsonObj.optJSONArray("data");

                if (dataArray != null) {

                    JSONObject tripObj;
                    ArrayList<TripBean> tripList = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        tripObj = dataArray.optJSONObject(i);

                        if (tripObj != null) {

                            TripBean tripBean = new TripBean();

                            if (tripObj.has("id")) {
                                tripBean.setId(tripObj.optString("id"));
                            }
                            if (tripObj.has("trip_status")) {
                                tripBean.setTripStatus(tripObj.optString("trip_status"));
                            }
                            if (tripObj.has("trip_id")) {
                                tripBean.setId(tripObj.optString("trip_id"));
                            }
                            if (tripObj.has("car_name")) {
                                tripBean.setCarName(tripObj.optString("car_name"));
                            }
                            if (tripObj.has("time")) {
                                tripBean.setTime(tripObj.optLong("time"));
                            }
                            if (tripObj.has("rate")) {
                                tripBean.setRate(tripObj.optString("rate"));
                            }
                            if (tripObj.has("source_latitude")) {
                                tripBean.setSourceLatitude(tripObj.optString("source_latitude"));
                            }
                            if (tripObj.has("source_longitude")) {
                                tripBean.setSourceLongitude(tripObj.optString("source_longitude"));
                            }
                            if (tripObj.has("destination_latitude")) {
                                tripBean.setDestinationLatitude(tripObj.optString("destination_latitude"));
                            }
                            if (tripObj.has("destination_longitude")) {
                                tripBean.setDestinationLongitude(tripObj.optString("destination_longitude"));
                            }
                            if (tripObj.has("driver_photo")) {
                                tripBean.setDriverPhoto(App.getImagePath(tripObj.optString("driver_photo")));
                            }

                            tripList.add(tripBean);
                        }

                    }
                    tripListBean.setTrips(tripList);
                }
            }

            if (jsonObj.has("meta")) {
                JSONObject metaObj = jsonObj.optJSONObject("meta");

                if (metaObj != null) {
                    if (metaObj.has("total_pages")) {
                        paginationBean.setTotalPages(metaObj.optInt("total_pages"));
                    }
                    if (metaObj.has("total")) {
                        paginationBean.setTotal(metaObj.optInt("total"));
                    }
                    if (metaObj.has("current_page")) {
                        paginationBean.setCurrentPage(metaObj.optInt("current_page"));
                    }
                    if (metaObj.has("per_page")) {
                        paginationBean.setPerPage(metaObj.optInt("per_page"));
                    }
                }
                tripListBean.setPagination(paginationBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return tripListBean;

    }
}
