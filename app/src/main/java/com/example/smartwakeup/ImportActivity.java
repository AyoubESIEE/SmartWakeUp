package com.example.smartwakeup;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ImportActivity extends AppCompatActivity {
    private Button Popol,pipil, TimePick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        Popol = findViewById(R.id.boutton_import_google);
        pipil = findViewById(R.id.boutton_import_url);
        TimePick = findViewById(R.id.boutton_timepicker);
        Popol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_pres);
                Intent ShowActivity = new Intent(ImportActivity.this, ShowActivity.class);
                startActivity(ShowActivity);

            }
        });
        pipil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_pres);
                Intent AlarmListActivity = new Intent(ImportActivity.this, AlarmListActivity.class);
                startActivity(AlarmListActivity);

            }
        });

        TimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_pres);
                Intent AlarmConfig = new Intent(ImportActivity.this, AlarmConfig.class);
                startActivity(AlarmConfig);

            }
        });

    }

}