package com.example.subscrazy;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, FirstFragment.class);
        String[] subInfo = i.getStringArrayExtra("sub");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Subscription");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Subscrazy");
        String text = subInfo[0] +" is renewing in "+ subInfo[2]+ " days";
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.PRIORITY_HIGH);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(context, nm);
        nm.notify(Integer.parseInt(subInfo[1]), builder.build());
    }

    private void createNotificationChannel(Context context, NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SubscrazyChannel";
            String Description = "Channel for Alarm Manager";
            NotificationChannel channel = new NotificationChannel("Subscription", name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(Description);
            notificationManager.createNotificationChannel(channel);
        }
    }
}