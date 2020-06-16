package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.HelpBean;
import in.techware.lataxidriverapp.model.HelpListBean;

/**
 * Created by Jemsheer K D on 20 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class HelpListParser {

    private static final String TAG = "HelpListParser";

    public HelpListBean parseHelpListResponse(String wsResponseString) {

        HelpListBean helpListBean = new HelpListBean();


        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            helpListBean.setError(errorJSObj.optString("error"));
                            helpListBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                helpListBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                helpListBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        helpListBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        helpListBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        helpListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        helpListBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    helpListBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    helpListBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    helpListBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                helpListBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                helpListBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                helpListBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                helpListBean.setLocationStatus("success");
            }*/

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {


                    if (dataObj.has("help")) {

                        JSONArray helpsArray = dataObj.optJSONArray("help");
                        ArrayList<HelpBean> helpList = new ArrayList<>();

                        if (helpsArray != null) {
                            try {
                                HelpBean helpBean = null;

                                for (int i = 0; i < helpsArray.length(); i++) {
                                    JSONObject helpObj = helpsArray.optJSONObject(i);
                                    if (helpObj != null) {
                                        helpBean = new HelpBean();

                                        if (helpObj.has("id")) {
                                            helpBean.setId(helpObj.optString("id"));
                                        }
                                        if (helpObj.has("icon")) {
                                            helpBean.setIcon(App.getImagePath(helpObj.optString("icon")));
                                        }
                                        if (helpObj.has("title")) {
                                            helpBean.setTitle(helpObj.optString("title"));
                                        }
                                        if (helpObj.has("content")) {
                                            helpBean.setContent(helpObj.optString("content"));
                                        }
                                        if (helpObj.has("is_helpful")) {
                                            helpBean.setHelpful(helpObj.optBoolean("is_helpful"));
                                        }
                                        helpList.add(helpBean);
                                    }
                                }
                                helpListBean.setHelpList(helpList);
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
        return helpListBean;
    }

}
