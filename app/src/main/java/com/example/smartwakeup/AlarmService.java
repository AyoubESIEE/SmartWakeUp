package com.example.smartwakeup;

import static com.example.smartwakeup.App.CHANNEL_ID;
import static com.example.smartwakeup.MyAlarmReceiver.DAY;
import static com.example.smartwakeup.MyAlarmReceiver.MONTH;
import static com.example.smartwakeup.MyAlarmReceiver.TITLE;
import static com.example.smartwakeup.MyAlarmReceiver.YEAR;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/***
 * Fichier Service permettant l'apparation de l'alarme et de la notification (Son, Vibration, Activity)
 */
public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        @SuppressLint("DefaultLocale") String alarmTitle = String.format("%s %d/%d/%d Alarm", intent.getStringExtra(TITLE), intent.getIntExtra(DAY,0), intent.getIntExtra(MONTH,0), intent.getIntExtra(YEAR,0));

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        mediaPlayer.start();

        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
