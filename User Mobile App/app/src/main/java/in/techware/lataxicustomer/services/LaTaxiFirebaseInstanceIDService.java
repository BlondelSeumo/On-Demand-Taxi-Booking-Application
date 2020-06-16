package in.techware.lataxicustomer.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import in.techware.lataxicustomer.config.Config;
import in.techware.lataxicustomer.listeners.BasicListener;
import in.techware.lataxicustomer.model.BasicBean;
import in.techware.lataxicustomer.net.DataManager;

public class LaTaxiFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "LTFIService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance id token to your app server.

        if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase("")) {
            JSONObject postData = getUpdateFCMTokenJSObj(refreshedToken);

            DataManager.performUpdateFCMToken(postData, new BasicListener() {
                @Override
                public void onLoadCompleted(BasicBean basicBean) {

                }

                @Override
                public void onLoadFailed(String error) {

                }
            });
        }


    }

    private JSONObject getUpdateFCMTokenJSObj(String fcmToken) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("fcm_token", fcmToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

}
