package com.example.smartwakeup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ShowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("/vcalendar/0");
    private MyAdapter adapter;
    private ArrayList<Model> list;
    private CreateAlarmViewModel createAlarmViewModel;
    String TAG = "ShowActivity";
    String[] Htime = new String[3];
    String[] date = new String[3];
    String[] Htime_2 = new String[3];
    String[] date_2 = new String[3];



    protected void editListe(){
        for (int i=0;i<list.size();i++) {

            String[] out = list.get(i).getDtstart().split("T");

            date[0] = out[0].substring(0, 4); //année evenement actuel
            date[1] = out[0].substring(4, 6); //mois evenement actuel
            date[2] = out[0].substring(6, 8); //jour evenement actuel

            Htime[0] = out[1].substring(0, 2);
            int Hours = Integer.parseInt(Htime[0]) + 1;
            Htime[0] = String.valueOf(Hours);
            Htime[1] = out[1].substring(2, 4);
            int Minutes = Integer.parseInt(Htime[1]);
            Htime[2] = out[1].substring(4, 6);


            list.get(i).dtstart = date[2] + "/" + date[1] + "/" + date[0];
            list.get(i).time = Htime[0] + ":" + Htime[1] + ":" + Htime[2];
        }
    }

    protected void setAlarm(){
        int earliest_Hour;
        String Current_day;
        String Current_month;
        Calendar calendar = Calendar.getInstance();
        Collections.sort(list, new Comparator<Model>() {
            @Override
            public int compare(Model model, Model t1) {
                return model.getDtstart().compareTo(t1.getDtstart());
            }
        });
        Current_month = list.get(0).getDtstart().substring(4,6);
        Current_day = list.get(0).getDtstart().substring(6,8);
        earliest_Hour = Integer.parseInt(list.get(0).getDtstart().substring(9,11));
        String alarm_summary = list.get(0).getSummary();



         for(int i=1;i<list.size();i++){
            String[] out = list.get(i).getDtstart().split("T");
            String[] out2 = list.get(i-1).getDtstart().split("T");

            date[0] = out[0].substring(0, 4); //année evenement actuel
            date[1] = out[0].substring(4, 6); //mois evenement actuel
            date[2] = out[0].substring(6, 8); //jour evenement actuel

            Htime[0] = out[1].substring(0, 2);
            int Hours = Integer.parseInt(Htime[0]) + 1;
            Htime[0] = String.valueOf(Hours);
            Htime[1] = out[1].substring(2, 4);
            int Minutes = Integer.parseInt(Htime[1]);
            Htime[2] = out[1].substring(4, 6);

            date_2[1] = out2[0].substring(4, 6); // mois evenement précédent
            date_2[2] = out2[0].substring(6, 8); // jour evenement précédent

            Htime_2[0] = out2[1].substring(0, 2);
            int Hours2 = Integer.parseInt(Htime_2[0]) + 1;
            Htime_2[0] = String.valueOf(Hours2);

            int compareDay = calendar.get(Calendar.DAY_OF_MONTH);
            int compareMonth = calendar.get(Calendar.MONTH);
            int compareYear = calendar.get(Calendar.YEAR);
            if ( !(compareYear >= Integer.parseInt(date[0]) && compareMonth >= Integer.parseInt(date[1]))){
                if (!date_2[1].equals(date[1])){

                    int alarmId = new Random().nextInt(Integer.MAX_VALUE);
                    Alarm alarm = new Alarm(
                            alarmId,
                            earliest_Hour,
                            Minutes,
                            alarm_summary,
                            System.currentTimeMillis(),
                            true,
                            2021,
                            Integer.parseInt(Current_month),
                            Integer.parseInt(Current_day)

                    );

                    if (createAlarmViewModel.CheckAlarmExist(alarm) >= 1){
                    }
                    else{

                        if(alarm.schedule(ShowActivity.this)) createAlarmViewModel.insert(alarm);
                    }

                    Current_month = date[1];
                    Current_day = date[2];
                    earliest_Hour = Hours;
                    alarm_summary = list.get(i).getSummary();

                    if (i== list.size()-1){

                        int lastalarmId = new Random().nextInt(Integer.MAX_VALUE);
                        Alarm lastalarm = new Alarm(
                                lastalarmId,
                                earliest_Hour,
                                Minutes,
                                list.get(i).getSummary(),
                                System.currentTimeMillis(),
                                true,
                                2021,
                                Integer.parseInt(Current_month),
                                Integer.parseInt(Current_day)

                        );
                        if (createAlarmViewModel.CheckAlarmExist(lastalarm) >= 1){
                        }
                        else{
                            if(lastalarm.schedule(ShowActivity.this)) createAlarmViewModel.insert(lastalarm);
                        }

                    }


                }
                else {
                    if (!date_2[2].equals(date[2])){
                        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
                        Alarm alarm = new Alarm(
                                alarmId,
                                earliest_Hour,
                                Minutes,
                                alarm_summary,
                                System.currentTimeMillis(),
                                true,
                                2021,
                                Integer.parseInt(Current_month),
                                Integer.parseInt(Current_day)

                        );
                        if (createAlarmViewModel.CheckAlarmExist(alarm) >= 1){

                        }
                        else{
                            if(alarm.schedule(ShowActivity.this)) createAlarmViewModel.insert(alarm);
                        }
                        Current_month = date_2[1];
                        Current_day = date[2];
                        earliest_Hour = Hours;
                        alarm_summary = list.get(i).getSummary();

                        if (i== list.size()-1){

                            int lastalarmId_HOUR = new Random().nextInt(Integer.MAX_VALUE);
                            Alarm lastalarm = new Alarm(
                                    lastalarmId_HOUR,
                                    earliest_Hour,
                                    Minutes,
                                    alarm_summary,
                                    System.currentTimeMillis(),
                                    true,
                                    2021,
                                    Integer.parseInt(Current_month),
                                    Integer.parseInt(Current_day)

                            );
                            if (createAlarmViewModel.CheckAlarmExist(lastalarm) >= 1){
                            }
                            else{
                                if(lastalarm.schedule(ShowActivity.this)) createAlarmViewModel.insert(lastalarm);
                            }

                        }

                    }else {
                        Current_month = date_2[1];
                        Current_day = date_2[2];
                        if (Hours > Hours2){
                            if (earliest_Hour > Hours2) {
                                earliest_Hour = Hours2;
                                alarm_summary = list.get(i-1).getSummary();
                            }

                        }
                        else{
                            if (earliest_Hour > Hours){
                                alarm_summary = list.get(i).getSummary();
                                earliest_Hour = Hours;
                            }


                        }

                    }

                }

            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);
        Log.e(TAG,"///////////////////////////////////////////////////////////DATA/////////////////////////////////////////////////////////////");
        Log.e(TAG,"Child ID :" + root.child("vevent").toString());
        createAlarmViewModel = new ViewModelProvider(this).get(CreateAlarmViewModel.class);

        root.child("vevent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                Log.e(TAG,
                        "Children count = " + String.valueOf(count));
                for (int i = 0; i < count; i++) {

                    Model model = snapshot.child(String.valueOf(i)).getValue(Model.class);
                    list.add(model);


//                    if (Hours < 12) {
//
//
////                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
////                        intent.putExtra(AlarmClock.EXTRA_HOUR,Hours-1);
////                        intent.putExtra(AlarmClock.EXTRA_MINUTES,Minutes);
////                        startActivity(intent);
//                        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
//
//                        Alarm alarm = new Alarm(
//                                alarmId,
//                                Hours,
//                                Minutes,
//                                model.getSummary(),
//                                System.currentTimeMillis(),
//                                true,
//                                false,
//                                false,
//                                false,
//                                false,
//                                false,
//                                false,
//                                false,
//                                false
//                        );
//
//                        createAlarmViewModel.insert(alarm);
//
//                        alarm.schedule(ShowActivity.this);
//
//
//                    }

                }
                setAlarm();
                editListe();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}