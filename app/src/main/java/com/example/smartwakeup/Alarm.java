package com.example.smartwakeup;

import static com.example.smartwakeup.MyAlarmReceiver.DAY;
import static com.example.smartwakeup.MyAlarmReceiver.MONTH;
import static com.example.smartwakeup.MyAlarmReceiver.YEAR;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
/***
 * Class qui Récupère les informations de la date et des horaires pour les alarmes et les planifient
 */
@Entity(tableName = "alarm_table")
public class Alarm {
    @PrimaryKey
    @NonNull
    private int alarmId;

    private int hour, minute;
    private boolean started;
    private int year;
    private int month, day;
    private String title;
    private long created;
    // Crée un Objet de type alarme qui contient toutes les informations pour la configuration d'alarme
    public Alarm(int alarmId, int hour, int minute, String title,long created, boolean started,int year,int month,int day) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.started = started;

        this.year = year;

        this.month = month;
        this.day = day;
        this.title = title;
        this.created = created;
    }
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isStarted() {
        return started;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /***
     * Fonction qui planifi les alarmes dans une instance calendrier
     */

    public boolean schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, MyAlarmReceiver.class);
        intent.putExtra(YEAR, year);
        intent.putExtra(MONTH, month);
        intent.putExtra(DAY, day);

        intent.putExtra("TITLE", title);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //Toast.makeText(context, "Calendar Time : " + calendar.getTimeInMillis() + "\nSystem Time : " + System.currentTimeMillis(), Toast.LENGTH_LONG).show();
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            return false;
        }




//        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("L'alarme %s est prévue pour %s à %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );

        this.started = true;
        return true;
    }

    /***
     * Fonction permettant l'annulation des alarmes dans le pending intent
     * @param context
     */
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }


    public String getTitle() {
        return title;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}