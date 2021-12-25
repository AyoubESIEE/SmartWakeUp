package com.example.smartwakeup;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.summary.setText(model.getSummary());
        holder.dtstart.setText(model.getDtstart());
        holder.time.setText(model.getTime());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView summary, dtstart, time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            summary = itemView.findViewById(R.id.summary_text);
            dtstart = itemView.findViewById(R.id.date_text);
            time = itemView.findViewById(R.id.time_text);
        }
    }
}
