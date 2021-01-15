package com.example.premierleague2523.data;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.premierleague2523.R;
import com.example.premierleague2523.ui.schedules.ScheduleFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListScheduleAdapter extends BaseAdapter {
    private final List<Schedule> schedules;
    private final Context context;

    public ListScheduleAdapter(Context context, List<Schedule> schedules){
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public int getCount() {
        return this.schedules.size();
    }

    @Override
    public Object getItem(int i) {
        return this.schedules.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.schedule_row,viewGroup,false);
        }

        TextView txtTeamHome = view.findViewById(R.id.tv_schedule_home);
        TextView txtTeamAway = view.findViewById(R.id.tv_schedule_away);
        TextView txtScheduleDate = view.findViewById(R.id.tv_schedule_date);

        final Schedule schedule = schedules.get(i);

        txtTeamHome.setText(schedule.getTeamHome());
        txtTeamAway.setText(schedule.getTeamAway());

        DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat output = new SimpleDateFormat("dd MMM, yyyy - HH:mm");
        try {
            Date scheduleDate = input.parse(schedule.getScheduleDate());
            String formattedScheduleDate = output.format(scheduleDate);

            txtScheduleDate.setText(formattedScheduleDate);
        } catch (ParseException e) {
            txtScheduleDate.setText(schedule.getScheduleDate());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putInt("ID", schedule.getId());

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Fragment scheduleFragment = ScheduleFragment.scheduleFragmentInstance(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,scheduleFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}