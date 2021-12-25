package com.example.smartwakeup;



import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/***
 * Création d'un channel pour les notifications et l'accés à l'alarme
 */
public class App extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL_TEST";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannnel();
    }

    private void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
