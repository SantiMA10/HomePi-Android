package xyz.santima.homepi.business.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import xyz.santima.homepi.ui.MainActivity;
import xyz.santima.homepi.R;

public class OwnFirebaseMessagingService extends FirebaseMessagingService {

    private static String TAG = "OwnMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setAutoCancel(true);

            Intent resultIntent = new Intent(this, MainActivity.class);

            PendingIntent pIntent = PendingIntent.getActivity(this, 0, resultIntent,0);

            mBuilder.setContentIntent(pIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

            mNotificationManager.notify((int)System.currentTimeMillis(), mBuilder.build());

        }
    }

}
