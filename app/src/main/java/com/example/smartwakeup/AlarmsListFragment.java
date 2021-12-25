package com.example.smartwakeup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

/***
 * Fichier correspondant à la gestion des items dans la RecycleView qui contient la liste des alarmes
 */
public class AlarmsListFragment extends Fragment implements OnToggleAlarmListener {
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private AlarmsListViewModel alarmsListViewModel;
    private RecyclerView alarmsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this);
        alarmsListViewModel = new ViewModelProvider(this).get(AlarmsListViewModel.class);
        alarmsListViewModel.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null) {
                    alarmRecyclerViewAdapter.setAlarms(alarms);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listalarms, container, false);

        alarmsRecyclerView = view.findViewById(R.id.fragment_listalarms_recylerView);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);
        /***
         * Fonction qui lance une requête de suppression à la base de données lorsque l'item est swiped
         */
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Alarm myAlarm = alarmRecyclerViewAdapter.getAlarmAtPosition(position);
                Toast.makeText(getContext(),"Deleting " + myAlarm.getTitle(),Toast.LENGTH_SHORT).show();
                alarmsListViewModel.deleteAlarm(myAlarm);
            }
        });
        helper.attachToRecyclerView(alarmsRecyclerView);

        return view;
    }

    /***
     *Fonction permetant d'inhiber une alarme ou non
     */
    @Override
    public void onToggle(Alarm alarm) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(getContext());
            alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(getContext());
            alarmsListViewModel.update(alarm);
        }
    }
}