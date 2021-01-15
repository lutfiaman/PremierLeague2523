package com.example.premierleague2523.ui.schedules;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.premierleague2523.R;
import com.example.premierleague2523.api.ApiInstance;
import com.example.premierleague2523.api.ApiService;
import com.example.premierleague2523.data.ListScheduleAdapter;
import com.example.premierleague2523.data.ResponseSchedule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedules, container, false);

        ApiService apiService = ApiInstance.getRetrofitInstance().create(ApiService.class);

        Call<ResponseSchedule> call = apiService.getSchedules();

        call.enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(Call<ResponseSchedule> call, Response<ResponseSchedule> response) {

                ListView scheduleListView = root.findViewById(R.id.schedule_list_view);
                ListScheduleAdapter listScheduleAdapter = new ListScheduleAdapter(getContext(), response.body().getSchedules());

                scheduleListView.setAdapter(listScheduleAdapter);
            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable throwable) {

                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}