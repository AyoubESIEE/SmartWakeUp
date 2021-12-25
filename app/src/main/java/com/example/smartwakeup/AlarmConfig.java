package com.example.smartwakeup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Locale;

public class AlarmConfig extends AppCompatActivity
{
    Button timeButton;
    int hour, minute;
    private Button importButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /* On relie le contenu du layout à l'activité courante puis on lie les bouttons des layouts
        au variables que nous avons crées */
        setContentView(R.layout.activity_alarm_config);
        timeButton = findViewById(R.id.timeButton);
        importButton = findViewById(R.id.boutton_import_google);

        /* La fonction onClick permet d'effectuer le passage à l'activité ShowActivity lorsqu'il
        y a un click sur le boutton */
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ShowActivity = new Intent(AlarmConfig.this, ShowActivity.class);
                startActivity(ShowActivity);

            }
        });
    }
    /* On creer une fonction pour utiliser le TimePicker dans notre application. Le TimePicker est
    une fonctionnalité importable directement depuis les librairies android studio
     */
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                /* On récupère les choix d'heures et minutes de l'utilisateur dans des variables */
                hour = selectedHour;
                minute = selectedMinute;
                /* On affiche sur le bouton, les choix de l'utilisateur dans le format indiqué en argument*/
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };
        // On utilise un thème présent dans une librairie java afficher le timerpicker sur un fond noir
        int style = AlertDialog.THEME_HOLO_DARK;

        /* On instancie le selecteur de temps avec TimePickerDialog, ce dernier est présent dans une librairie Android studio.
        On creer un objet avec les argument permettant de paramétrer le style, les variable ou le format 12h ou 24h */
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, false);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}