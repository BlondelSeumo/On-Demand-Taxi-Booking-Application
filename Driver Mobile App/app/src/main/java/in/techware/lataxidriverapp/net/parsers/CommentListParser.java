package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.model.CommentBean;
import in.techware.lataxidriverapp.model.CommentListBean;
import in.techware.lataxidriverapp.model.PaginationBean;

/**
 * Created by Jemsheer K D on 19 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class CommentListParser {

    private static final String TAG = "CommentListParser";

    public CommentListBean parseCommentListResponse(String wsResponseString) {

        CommentListBean commentListBean = new CommentListBean();
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
                            commentListBean.setError(errorJSObj.optString("error"));
                            commentListBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                commentListBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                commentListBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        commentListBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        commentListBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        commentListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        commentListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    commentListBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    commentListBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    commentListBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                commentListBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                commentListBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                commentListBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                commentListBean.setLocationStatus("success");
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
                commentListBean.setPagination(paginationBean);
            }

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {

/*                    if (dataObj.has("total_fare")) {
                        commentListBean.setTotalFare(dataObj.optString("total_fare"));
                    }*/

                    if (dataObj.has("comments")) {

                        JSONArray commentsArray = dataObj.optJSONArray("comments");
                        ArrayList<CommentBean> commentList = new ArrayList<>();

                        if (commentsArray != null) {
                            try {
                                CommentBean commentBean = null;

                                for (int i = 0; i < commentsArray.length(); i++) {
                                    JSONObject commentObj = commentsArray.optJSONObject(i);
                                    if (commentObj != null) {
                                        commentBean = new CommentBean();

                                        if (commentObj.has("id")) {
                                            commentBean.setId(commentObj.optString("id"));
                                        }
                                        if (commentObj.has("customer_comment")) {
                                            commentBean.setCustomerComment(commentObj.optString("customer_comment"));
                                        }
                                        if (commentObj.has("customer_id")) {
                                            commentBean.setCustomerID(commentObj.optString("customer_id"));
                                        }
                                        if (commentObj.has("trip_id")) {
                                            commentBean.setTripID(commentObj.optString("trip_id"));
                                        }
                                        if (commentObj.has("rating")) {
                                            commentBean.setRating((float) commentObj.optDouble("rating"));
                                        }
                                        if (commentObj.has("time")) {
                                            commentBean.setTime(commentObj.optLong("time") * 1000);
                                        }
                                        commentList.add(commentBean);
                                    }
                                }
                                commentListBean.setComments(commentList);
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
        return commentListBean;
    }
}
