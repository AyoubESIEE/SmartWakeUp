package com.example.smartwakeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.media.MediaPlayer;



public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int requestCode = intent.getIntExtra("REQUEST_CODE",-1);
//        Toast.makeText(context,"Alarm fired with request code : "+requestCode, Toast.LENGTH_LONG).show();
//        MediaPlayer  mp;
//        mp = MediaPlayer.create(context, R.raw.alarm);
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
//            @Override
//            public void onCompletion(MediaPlayer mp){
//                mp.reset();
//                mp.release();
//                mp=null;
//            }
//        });
//    mp.start();

    }
}
