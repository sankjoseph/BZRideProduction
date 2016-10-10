package bzride.com.bzride;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Santhosh.Joseph on 31-08-2016.
 */
public class BZRideGcmListenerService extends GcmListenerService {
    private static final String TAG = "BZRideGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("m");


        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);


        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        if (message != null) {
            sendNotification(message);
        }
        // [END_EXCLUDE]
    }
    private void sendNotification(String message) {

        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
               // PendingIntent.FLAG_ONE_SHOT);*/

        if (BZAppManager.getInstance().isDriver == true){
            Intent intent = new Intent(this, RideRequestNotifiedDetails.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("message",message);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            //ic_stat_ic_notification todo fix icon
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.drawable.ic_stat_ic_notification);
            } else {
                notificationBuilder.setSmallIcon(R.drawable.ic_stat_ic_notification);
            }

            notificationBuilder.setContentTitle("BZ Ride");
            notificationBuilder.setContentText(message);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
        else{// if rider just update home
            Intent intent = new Intent("request_accepted");
            intent.putExtra("message", message);
            BZAppManager.getInstance().currentRideRequestMessage = message;
            //add data you wnat to pass in intent
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

            if (message.contains("charged by")) {//ending ride
                BZAppManager.getInstance().selectedPickUpLocation = new LatLng(0.0,0.0);
                BZAppManager.getInstance().selectedDropLocation = new LatLng(0.0,0.0);
                //driver uses currentRideRequestId to be cleared;
                BZAppManager.getInstance().currentRideRequestId = "";
                BZAppManager.getInstance().currentRideRequestUserPhone = "";
                BZAppManager.getInstance().currentRideRequestUserName = "";
            }
        }

    }
}
