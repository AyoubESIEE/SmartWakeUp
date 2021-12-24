package com.example.smartwakeup;


import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView myText;
    /* On set le contenu en layout du mainactivty puis un basculement temporisé vers le layout de PresActivity
     */
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
        }, 1500);




    }
}