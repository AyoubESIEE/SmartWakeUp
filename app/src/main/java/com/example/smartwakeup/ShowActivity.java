package com.example.smartwakeup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("/vcalendar/0");
    private MyAdapter adapter;
    private ArrayList<Model> list;
    String TAG = "ShowActivity";
    String[] Htime = new String[3];
    String[] date = new String[3];

    protected void setAlarm(String[] date, String[] time){
        AlarmManager am =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        int requestCode = (int) calendar.getTimeInMillis() / 1000;
//        calendar.clear();
//        calendar.set(calendar.YEAR, Integer.parseInt(date[0]));
//        calendar.set(calendar.MONTH, Integer.parseInt(date[1]));
//        calendar.set(calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));
//        calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(time[2]));
//        calendar.set(calendar.MINUTE, Integer.parseInt(time[1]));
        calendar.add(Calendar.SECOND,15);
        Intent AlarmIntent = new Intent(this,MyAlarmReceiver.class);
        AlarmIntent.putExtra("REQUEST_CODE", requestCode);
        AlarmIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        AlarmIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent pi = PendingIntent.getBroadcast(this,requestCode, AlarmIntent, 0);
        //am.setAlarmClock(new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis()),pi);
        //Log.e(TAG, am.get);
        Toast.makeText(this,"Alarm Launched",Toast.LENGTH_SHORT).show();
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

        root.child("vevent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                Log.e(TAG,
                        "Children count = " + String.valueOf(count));
                for (int i = 0; i < count; i++) {
                    Log.e(TAG,
                            snapshot.child(String.valueOf(i)).child("summary").toString());

                    Log.e(TAG,
                            snapshot.child(String.valueOf(i)).child("summary").getValue().toString());
                    Model model = snapshot.child(String.valueOf(i)).getValue(Model.class);
                    String[] out = model.getDtstart().split("T");

                    date[0] = out[0].substring(0, 4);
                    date[1] = out[0].substring(4, 6);
                    date[2] = out[0].substring(6, 8);

                    Htime[0] = out[1].substring(0, 2);
                    int Hours = Integer.parseInt(Htime[0]) + 1;
                    Htime[0] = String.valueOf(Hours);
                    Htime[1] = out[1].substring(2, 4);
                    int Minutes = Integer.parseInt(Htime[1]);
                    Htime[2] = out[1].substring(4, 6);
                    model.dtstart = date[2] + "/" + date[1] + "/" + date[0];
                    model.time = Htime[0] + ":" + Htime[1] + ":" + Htime[2];
                    list.add(model);

                    if (Hours < 12) {

//                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//                        intent.putExtra(AlarmClock.EXTRA_HOUR,Hours-1);
//                        intent.putExtra(AlarmClock.EXTRA_MINUTES,Minutes);
//                        startActivity(intent);




                    }



                }
                adapter.notifyDataSetChanged();
                setAlarm(date,Htime);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShowActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}