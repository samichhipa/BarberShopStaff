package com.example.barbershopstaff.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.example.barbershopstaff.BranchActivity;
import com.example.barbershopstaff.Model.Common;
import com.example.barbershopstaff.NotificationActivity;
import com.example.barbershopstaff.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sent_id = remoteMessage.getData().get("sent_id");
        String user_id = remoteMessage.getData().get("user_id");

        if (Common.currenBarber.getBarber_id()!=null && sent_id.equals(Common.currenBarber.getBarber_id())){

            if (!Common.currenBarber.getBarber_id().equals(user_id)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    OreoAndAboveNotification(remoteMessage);

                } else {

                    sendNotification(remoteMessage);
                }


            }


        }






    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void OreoAndAboveNotification(RemoteMessage remoteMessage) {


        String sent_id = remoteMessage.getData().get("sent_id");
        String user_id = remoteMessage.getData().get("user_id");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

      /*  Intent in=new Intent(this, NotificationActivity.class);
        in.putExtra("title",title);
        in.putExtra("body",body);
        in.putExtra("notification_type",notification_type);
        in.putExtra("user_id",user_id);
        in.putExtra("sent_id",sent_id);

        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);

       */




        Intent intent=new Intent(this, BranchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationHelper notificationHelper=new NotificationHelper(this);

        NotificationCompat.Builder builder=null;

         builder=notificationHelper.getEatItChannelNotification(title,body,pendingIntent,defaultSoundUri);



        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

    private void sendNotification(RemoteMessage remoteMessage) {


        String sent_id = remoteMessage.getData().get("sent_id");
        String user_id = remoteMessage.getData().get("user_id");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");



        Intent in=new Intent(this, NotificationActivity.class);
       /* in.putExtra("title",title);
        in.putExtra("body",body);
        in.putExtra("user_id",user_id);
        in.putExtra("sent_id",sent_id);


        */
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);




        //Intent intent=new Intent(this, BranchActivity.class);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,in,PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=null;

            builder= new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);


        



        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
