package com.example.smartwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.Calendar;


public class MyAlarmReceiver extends BroadcastReceiver {
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DAY = "DAY";
    public static final String TITLE = "TITLE";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            String toastText = String.format("Alarm Reboot");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            startRescheduleAlarmsService(context);
        }
        else {
            String toastText = String.format("Alarm Received");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            startAlarmService(context, intent);
        }
    }


    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE));
        intentService.putExtra(DAY, intent.getIntExtra(DAY,0));
        intentService.putExtra(MONTH, intent.getIntExtra(MONTH,0));
        intentService.putExtra(YEAR, intent.getIntExtra(YEAR,0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }

    private void startRescheduleAlarmsService(Context context) {
        Intent intentService = new Intent(context, RescheduleAlarmsService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}
