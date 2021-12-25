package com.example.smartwakeup;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
/***
* Interface permettant la formation de "Query" pour la base de données ROOM
* */
@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    LiveData<List<Alarm>> getAlarms();

    @Update
    void update(Alarm alarm);

    @Query("SELECT COUNT() FROM alarm_table WHERE year = :year AND month = :month AND day = :day AND hour = :hour")
    int CheckAlarmExist(int year,int month,int day,int hour);

    @Delete
    void deleteAlarm(Alarm alarm);

}
