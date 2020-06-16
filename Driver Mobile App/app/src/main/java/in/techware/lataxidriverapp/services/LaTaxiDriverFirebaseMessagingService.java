package in.techware.lataxidriverapp.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import in.techware.lataxidriverapp.activity.RequestConfirmationActivity;
import in.techware.lataxidriverapp.activity.TripDetailsActivity;
import in.techware.lataxidriverapp.model.BasicBean;
import in.techware.lataxidriverapp.net.parsers.RequestParser;

public class LaTaxiDriverFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "LFMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
            Log.i(TAG, "Response: " + remoteMessage.getData().get("response"));


            String body = remoteMessage.getData().get("response");
            RequestParser requestParser = new RequestParser();
            BasicBean basicBean = requestParser.parseBasicResponse(body);

            if (basicBean == null)
                stopSelf();
            else {
                if (basicBean.getStatus().equalsIgnoreCase("Success")) {
//                    initiateDriverRatingService(basicBean.getId());
                    if (basicBean.getRequestID() != null && !basicBean.getRequestID().equalsIgnoreCase("")) {
                        initiateDriverRatingService(basicBean.getRequestID());
                    } else if (basicBean.getTripID() != null && !basicBean.getTripID().equalsIgnoreCase("")){
                        startActivity(new Intent(this, TripDetailsActivity.class)
                                .putExtra("trip_id", basicBean.getTripID())
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else{
                        stopSelf();
                    }
                } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                    stopSelf();
                } else {
                    stopSelf();
                }
            }

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
//                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String body = remoteMessage.getNotification().getBody();

            RequestParser requestParser = new RequestParser();
            BasicBean basicBean = requestParser.parseBasicResponse(body);

            if (basicBean == null)
                stopSelf();
            else {
                if (basicBean.getStatus().equalsIgnoreCase("Success")) {
//                    initiateDriverRatingService(basicBean.getId());
                    initiateDriverRatingService(basicBean.getRequestID());
                } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                    stopSelf();
                } else {
                    stopSelf();
                }
            }


        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    public void initiateDriverRatingService(String requestID) {

        Log.i(TAG, "initiateDriverRatingService: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SERVICE STARTED>>>>>>>>>>>>>>>>>>>>>");

        Intent intent = new Intent(this, RequestConfirmationActivity.class);
        intent.putExtra("request_id", requestID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
