package com.example.smartwakeup;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;
    private LiveData<List<Alarm>> alarmsLiveData;
    private int alarmExistCount;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public void insert(Alarm alarm) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {

            alarmDao.insert(alarm);
        });
    }

    public void update(Alarm alarm) {
        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.update(alarm);
        });
    }

    public void deleteAlarm(Alarm alarm)  {
        new deleteAlarmAsyncTask(alarmDao).execute(alarm);
    }

    public int CheckAlarmExist(Alarm alarm){

        AlarmDatabase.databaseWriteExecutor.execute(() -> {
            alarmExistCount = alarmDao.CheckAlarmExist(alarm.getYear(),alarm.getMonth(),alarm.getDay(),alarm.getHour());
        });
        return alarmExistCount;
    }




    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
    private static class deleteAlarmAsyncTask extends AsyncTask<Alarm,Void,Void> {
        private AlarmDao mAsyncTaskDao;
        deleteAlarmAsyncTask(AlarmDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... alarms) {
            mAsyncTaskDao.deleteAlarm(alarms[0]);
            return null;
        }
    }
}
