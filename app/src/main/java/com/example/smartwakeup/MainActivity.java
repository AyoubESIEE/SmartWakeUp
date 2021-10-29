package com.example.smartwakeup;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView myText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent presActivityIntent = new Intent(MainActivity.this, PresActivity.class);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                startActivity(presActivityIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 3000);




    }
}