package com.example.smartwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int requestCode = intent.getIntExtra("REQUEST_CODE",-1);

        Toast.makeText(context,"Alarm fired with request code : "+requestCode, Toast.LENGTH_LONG).show();

    }
}
