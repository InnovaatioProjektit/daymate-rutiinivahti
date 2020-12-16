package com.team9.daymate.viewModels;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.notifications.NotificationReceiver;
import com.team9.daymate.routines.RoutineActivity;

import java.util.Calendar;

/**
 * Luo muistutuksen jota voi lähetä ilmoituskanavien kautta.
 *
 * @author Jaakko Buchelnikov
 */
public class NotificationViewModel extends ViewModel {

    private NotificationCompat.Builder builder;
    private final String NOTIFICATION_ID = "NOTIFY_2345678";
    private final String NOTIFICATION = "Noctural-123";
    private final String CHANNEL_ID = "Daymate_messages";

    /**
     * Luo muistutusolio
     *
     * @author Jaakko Buchelnikov
     *
     * @param context Activityn konteksti
     */
    public void buildNotification(Context context) {
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Hello World");
        bigText.setBigContentTitle("title");

        Intent intent = new Intent(context, RoutineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_not))
                .setContentTitle("Muistutus")
                .setContentText("Muista tehdä päivän suoritukset")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.rgb(245, 133, 59));
    }

    /**
     * Luo toistuvan muistuksen kerran päivässä. Ottaa parametrikseen Arrayn jossa ensimmäinen
     * alkio vastaa tunteja ja toinen vastaa minutteja
     *
     * @author Jaakko Buchelnikov
     *
     * @param context Käyttöliittymän
     *
     */
    public void scheduleDailyNotification (int[] time, Context context, String NOTIFICATION_TAG){
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(NOTIFICATION_ID, 1);
        intent.putExtra(NOTIFICATION, builder.build());
        intent.putExtra(NOTIFICATION_TAG, 1);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, time[0]);
        calendar.set(Calendar.MINUTE, time[1]);

        // INTERVAL_DAY = Päivän päästä
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() / 1000, AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    /**
     * Estä muistuksen saapumista
     *
     * @author Jaakko Buchelnikov
     *
     * @param context Käyttöliittymän
     * @param NOTIFICATION_TAG muistutusta erottava tagi
     */
    public void scheduleNotificationCancel(Context context, String NOTIFICATION_TAG){
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(NOTIFICATION_ID, 1);
        intent.putExtra(NOTIFICATION, builder.build());
        intent.putExtra(NOTIFICATION_TAG, 1);

        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarm.cancel(pendingIntent);
    }


    /**
     * Luo muistutus ajan hetken päästä
     *
     * @author Jaakko Buchelnikov
     *
     * @param context Käyttöliittymän
     */
    public void scheduleNotification(int delay, Context context){
        Intent intent = new Intent(context, NotificationReceiver.class);

        intent.putExtra(NOTIFICATION_ID, 1);
        intent.putExtra(NOTIFICATION, builder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long millis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, millis, pendingIntent);
    }


    /**
     * Luo ilmoituskanava jonka kautta sovellus lähettää muistukset
     *
     * @author Jaakko Buchelnikov
     *
     * @param context Käyttöliittymän
     */
    public void createNotificationChannel(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.setDescription(CHANNEL_ID);
            channel.enableVibration(true);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    /**
     * Lähetä muistutus heti
     *
     * @author Jaakko Buchelnikov
     *
     * @param context Käyttöliittymän
     */
    public void sendImmediate(Context context){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(54183812, builder.build());
    }

}
