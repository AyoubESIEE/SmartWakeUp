package com.example.smartwakeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PresActivity extends AppCompatActivity {

    private Button mNextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pres);
        mNextButton = findViewById(R.id.boutton_suivant);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent impActivityIntent = new Intent(PresActivity.this, ImportActivity.class);
                startActivity(impActivityIntent);

            }
        });

    }
}