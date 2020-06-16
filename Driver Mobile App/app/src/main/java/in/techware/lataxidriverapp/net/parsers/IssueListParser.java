package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.model.IssueBean;
import in.techware.lataxidriverapp.model.IssueListBean;
import in.techware.lataxidriverapp.model.PaginationBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class IssueListParser {

    private static final String TAG = "IssueListParser";

    public IssueListBean parseIssueListResponse(String wsResponseString) {

        IssueListBean issueListBean = new IssueListBean();
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
                            issueListBean.setError(errorJSObj.optString("error"));
                            issueListBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                issueListBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                issueListBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        issueListBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        issueListBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        issueListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        issueListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    issueListBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    issueListBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    issueListBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                issueListBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                issueListBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                issueListBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                issueListBean.setLocationStatus("success");
            }*/

            if (jsonObj.has("meta")) {
                JSONObject metaObj = jsonObj.optJSONObject("meta");

                if (metaObj != null) {
                    if (metaObj.has("total_pages")) {
                        paginationBean.setTotalPages(metaObj.optInt("total_pages"));
                    }
                    if (metaObj.has("total")) {
                        paginationBean.setTotal(metaObj.optInt("total"));
                        paginationBean.setTotalCount(metaObj.optInt("total"));
                    }
                    if (metaObj.has("current_page")) {
                        paginationBean.setCurrentPage(metaObj.optInt("current_page"));
                    }
                    if (metaObj.has("per_page")) {
                        paginationBean.setPerPage(metaObj.optInt("per_page"));
                    }
                }
                issueListBean.setPagination(paginationBean);
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {

/*                    if (dataObj.has("total_fare")) {
                        issueListBean.setTotalFare(dataObj.optString("total_fare"));
                    }*/

                    if (dataObj.has("issues")) {

                        JSONArray issuesArray = dataObj.optJSONArray("issues");
                        ArrayList<IssueBean> issueList = new ArrayList<>();

                        if (issuesArray != null) {
                            try {
                                IssueBean issueBean = null;

                                for (int i = 0; i < issuesArray.length(); i++) {
                                    JSONObject issueObj = issuesArray.optJSONObject(i);
                                    if (issueObj != null) {
                                        issueBean = new IssueBean();

                                        if (issueObj.has("id")) {
                                            issueBean.setId(issueObj.optString("id"));
                                        }
                                        if (issueObj.has("issue")) {
                                            issueBean.setIssue(issueObj.optString("issue"));
                                        }
                                        if (issueObj.has("customer_comment")) {
                                            issueBean.setCustomerComment(issueObj.optString("customer_comment"));
                                        }
                                        if (issueObj.has("customer_id")) {
                                            issueBean.setCustomerID(issueObj.optString("customer_id"));
                                        }
                                        if (issueObj.has("trip_id")) {
                                            issueBean.setTripID(issueObj.optString("trip_id"));
                                        }
                                        issueList.add(issueBean);
                                    }
                                }
                                issueListBean.setIssues(issueList);
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
        return issueListBean;
    }
}
