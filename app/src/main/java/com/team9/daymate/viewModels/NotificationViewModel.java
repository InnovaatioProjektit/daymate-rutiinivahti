package com.team9.daymate.viewModels;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModel;

import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.routines.RoutineActivity;

import java.util.Calendar;

/**
 * Luo muistutuksen jota voi lähetä ilmoituskanavien kautta.
 *
 * @author Jaakko Buchelnikov
 */
public class NotificationViewModel extends ViewModel {

    private NotificationCompat.Builder builder;


    /**
     * Luo muistutus
     * @param context Activityn konteksti
     */
    public void buildNotification(Context context) {
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Hello World");
        bigText.setBigContentTitle("title");

        Intent intent = new Intent(context, RoutineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        builder = new NotificationCompat.Builder(context, AppDataLogic.CHANNEL_ID)
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

    private void AlarmNotification (Context context){
        Intent i = new Intent();

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, ((Presenter)context).getIntent(), 0);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 10);

        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() / 1000, AlarmManager.INTERVAL_DAY, alarmIntent);
    }


    /**
     * Luo ilmoituskanava jonka kautta sovellus lähettää muistukset
     * @param context
     */
    public void createNotificationChannel(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = AppDataLogic.CHANNEL_ID;
            String description = AppDataLogic.CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(AppDataLogic.CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    /**
     * Lähetä muistutus
     * @param context
     */
    public void send(Context context){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(54183812, builder.build());
    }

}
