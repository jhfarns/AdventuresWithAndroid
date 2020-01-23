package com.example.jamesfarnsworthc196;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class CreateCatcher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        if (Build.VERSION.SDK_INT < 26) {

            Intent intent1 = new Intent(context, Courses.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setContentTitle("Course Notification")
                    .setContentText(intent.getStringExtra("createBroadcastAlarm"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            int _id = (int) System.currentTimeMillis();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(_id, builder.build());

        } else {

            Intent intent1 = new Intent(context, Courses.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent1,0);
            NotificationChannel channel = new NotificationChannel("default","Channel1", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setContentTitle("Course Notification")
                    .setContentText(intent.getStringExtra("createBroadcastAlarm"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            int _id = (int) System.currentTimeMillis();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(_id, builder.build());

        }

    }
}
