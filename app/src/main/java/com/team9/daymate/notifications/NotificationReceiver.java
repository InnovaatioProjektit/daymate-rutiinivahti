package com.team9.daymate.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Vastaanottaa ja käynnistää muistutuksen, kun se saapuu hälytin managerista
 *
 * @author Jaakko Buchelnikov
 */
public class NotificationReceiver extends BroadcastReceiver {
    private final String NOTIFICATION_ID = "NOTIFY_2345678";
    private final String NOTIFICATION = "Noctural-123";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }
}