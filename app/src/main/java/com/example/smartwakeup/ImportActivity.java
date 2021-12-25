package com.example.smartwakeup;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ImportActivity extends AppCompatActivity {
    private Button urlImportBtn, TimePick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        urlImportBtn = findViewById(R.id.boutton_import_url);
        TimePick = findViewById(R.id.boutton_timepicker);
        urlImportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent AlarmListActivity = new Intent(ImportActivity.this, AlarmListActivity.class);
                startActivity(AlarmListActivity);

            }
        });

        TimePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AlarmConfig = new Intent(ImportActivity.this, AlarmConfig.class);
                startActivity(AlarmConfig);

            }
        });

    }

}