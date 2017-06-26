package shark_tips.com.sharktips;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import android.util.Log;
import android.widget.RemoteViews;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by liranelyadumi on 5/8/17.
 */

public class FcmMessaginServiceHandle extends FirebaseMessagingService {

    private RemoteViews remoteViews;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("TAG", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotifications(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    private void sendNotifications(final String body, String title) {

        String result = "";
        if (title.equals("New Signal") || title.equals("Update Signal")){
            try {
                JSONObject object = new JSONObject(body);
                result += "currency - " + object.getString("currency")+"\n";
                result += "buy stop - " + convertValues(object.getDouble("sellStop"))+"\n";
                result += "SL - " + convertValues(object.getDouble("sl"))+"\n";
                result += "TP1 - " + convertValues(object.getDouble("tp1"))+"\n";
                result += "TP2 - " + convertValues(object.getDouble("tp2"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            result = body;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("goto",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                 .setSmallIcon(R.drawable.sharklogo)
                 .setContentTitle(title + "!")
                 .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationCompat.BigTextStyle big  = new NotificationCompat.BigTextStyle();
        big.bigText(body);
        big.setSummaryText("For your success.");
        notificationBuilder.setStyle(big);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private String convertValues(double value){
        if (value == -1){
            return "none";
        }
        return String.valueOf(value);
    }
}
