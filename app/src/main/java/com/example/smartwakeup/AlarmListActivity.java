package com.example.smartwakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/**
 *
 *Création de l'Activity qui affiche les alarmes prévues
 */

public class AlarmListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }
}