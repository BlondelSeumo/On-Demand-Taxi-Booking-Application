package in.techware.lataxidriverapp.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxidriverapp.app.App;
import in.techware.lataxidriverapp.model.HelpBean;

/**
 * Created by Jemsheer K D on 20 May, 2017.
 * Package in.techware.lataxidriver.net.parsers
 * Project LaTaxiDriver
 */

public class HelpParser {

    private static final String TAG = "HelpParser";

    public HelpBean parseHelpResponse(String wsResponseString) {

        HelpBean helpBean = new HelpBean();


        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            helpBean.setError(errorJSObj.optString("error"));
                            helpBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                helpBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                helpBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        helpBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        helpBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        helpBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        helpBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    helpBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    helpBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    helpBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                helpBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                helpBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                helpBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                helpBean.setLocationStatus("success");
            }*/

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    if (dataObj.has("id")) {
                        helpBean.setId(dataObj.optString("id"));
                    }
                    if (dataObj.has("icon")) {
                        helpBean.setIcon(App.getImagePath(dataObj.optString("icon")));
                    }
                    if (dataObj.has("title")) {
                        helpBean.setTitle(dataObj.optString("title"));
                    }
                    if (dataObj.has("content")) {
                        helpBean.setContent(dataObj.optString("content"));
                    }
                    if (dataObj.has("is_helpful")) {
                        helpBean.setHelpful(dataObj.optBoolean("is_helpful"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return helpBean;
    }
}
