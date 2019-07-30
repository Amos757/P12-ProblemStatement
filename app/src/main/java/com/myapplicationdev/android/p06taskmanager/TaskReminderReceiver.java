package com.myapplicationdev.android.p06taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;

	@Override
	public void onReceive(Context context, Intent i) {

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		//Intent first
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, notifReqCode,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //Notification manager
		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);

		//Action
        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "This is an Action",
                pIntent).build();

        //Where to add the action in to watch , extender to action (Send notification to watch)
        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);


        // Build notification and set diaplay
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, "default");
        builder.setContentText(name);
        builder.setContentTitle(desc);
        builder.setSmallIcon(android.R.drawable.btn_star_big_off);

        // Attach the action for Wear notification created above
        builder.extend(extender);

        //Set the notification to call
        Notification notification = builder.build();
        notificationManager.notify(id, notification);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new
					NotificationChannel("default", "Default Channel",
					NotificationManager.IMPORTANCE_DEFAULT);

			channel.setDescription("This is for default notification");
			notificationManager.createNotificationChannel(channel);
		}

	}

}
