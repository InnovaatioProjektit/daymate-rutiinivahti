package com.team9.daymate.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team9.daymate.NavActivity;
import com.team9.daymate.R;
import com.team9.daymate.core.AppDataLogic;
import com.team9.daymate.core.Presenter;
import com.team9.daymate.core.UIView;
import com.team9.daymate.elements.CircularImageView;
import com.team9.daymate.example.TestFragment;
import com.team9.daymate.example.TestViewModel;
import com.team9.daymate.routines.RoutineActivity;
import com.team9.daymate.routines.RoutineDailyFragment;
import com.team9.daymate.shop.ShopActivity;

import java.util.Calendar;


public class NotificationActivity extends Presenter {

    NotificationCompat.Builder builder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.setViewModel(TestViewModel.class);

        if (savedInstanceState == null) {
            this.loadView(R.id.fragment_container, TestFragment.class, this.getIntent().getExtras());

            createNotificationChannel();
            NotificationCode();

        }

        BottomNavigationView menu = (BottomNavigationView)this.findViewById(R.id.navigation_bar);
        FloatingActionButton fab = (FloatingActionButton)this.findViewById(R.id.navigation_fab);


        Context that = this;

        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AlarmNotification();



                //TODO LUO RUTIINI (KATEGORIA -> RUTIINIVALIKKO -> UUSI RUTIINI -> PÄIVÄN LOKI) Fragmentit [UIVIEW]
            }
        });

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle saveInstance = new Bundle();

                switch(item.getItemId()) {
                    case R.id.navigation_tab_progress: break;
                    case R.id.navigation_tab_routines: break;
                    case R.id.navigation_tab_something:
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(that);
                        notificationManager.notify(54183812, builder.build());
                        return true;

                    case R.id.navigation_tab_shop:
                        loadActivity(ShopActivity.class);
                        return true;
                    default: return false;
                }

                saveInstance.putInt("SELECTED", item.getItemId());
                loadActivity(NavActivity.class, saveInstance);

                return false;
            }
        });

        fab.setImageResource(R.drawable.tab_add_56dp);
        menu.setSelectedItemId(R.id.placeholder);
    }


    public void NotificationCode() {
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Hello Worl");
        bigText.setBigContentTitle("title");

        Intent intent = new Intent(this, RoutineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        builder = new NotificationCompat.Builder(this, AppDataLogic.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_not))
                .setContentTitle("Muistutus")
                .setContentText("Muista tehdä päivän suoritukset")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(Color.rgb(245, 133, 59));





    }

    private void AlarmNotification (){
        Intent i = new Intent();

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, getIntent(), 0);

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 13);

        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() / 1000, AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = AppDataLogic.CHANNEL_ID;
            String description = AppDataLogic.CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(AppDataLogic.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            channel.setVibrationPattern(new long[]{1000,500,1000,50});
            channel.enableVibration(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}