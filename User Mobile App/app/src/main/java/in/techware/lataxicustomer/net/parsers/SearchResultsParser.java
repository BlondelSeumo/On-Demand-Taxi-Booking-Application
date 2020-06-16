package in.techware.lataxicustomer.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.model.SearchResultsBean;

public class SearchResultsParser {

    private SearchResultsBean searchResultsBean;

    public SearchResultsBean parseSearchResultsResponse(String wsResponseString) {

        searchResultsBean = new SearchResultsBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            searchResultsBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchResultsBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                searchResultsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        searchResultsBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        searchResultsBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        searchResultsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        searchResultsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    searchResultsBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    searchResultsBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    searchResultsBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                searchResultsBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    searchResultsBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    searchResultsBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        searchResultsBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                searchResultsBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                searchResultsBean.setErrorMsg(jsonObj.optString("response"));
            }

            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        /*if (dataJSObject.has("id")) {
                            searchResultsBean.setId(dataJSObject.optString("id"));
                        }
                        if (dataJSObject.has("plcae")) {
                            searchResultsBean.setPlace(dataJSObject.optString("plcae"));
                        }
                        if (dataJSObject.has("address")) {
                            searchResultsBean.setAddress(dataJSObject.optString("address"));
                        }
                        if (dataJSObject.has("latitude")) {
                            searchResultsBean.setLatitude(dataJSObject.optString("latitude"));
                        }
                        if (dataJSObject.has("longitude")) {
                            searchResultsBean.setLongitude(dataJSObject.optString("longitude"));
                        }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return searchResultsBean;
    }
}