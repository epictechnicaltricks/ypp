package com.cakiweb.easyscholar;

import android.app.Notification;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String txt = remoteMessage.getNotification().getBody();
        getFirebaseMessage(title,txt);

    }

    public void getFirebaseMessage(String title,String msg){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"myFirebaseChannel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(false);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        manager.notify(101,builder.build());
    }

}
