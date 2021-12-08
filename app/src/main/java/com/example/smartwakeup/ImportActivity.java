package com.example.smartwakeup;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ImportActivity extends AppCompatActivity {
    private Button Popol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        Popol = findViewById(R.id.boutton_import_google);
        Popol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_pres);
                Intent ShowActivity = new Intent(ImportActivity.this, ShowActivity.class);
                startActivity(ShowActivity);

            }
        });

    }

}