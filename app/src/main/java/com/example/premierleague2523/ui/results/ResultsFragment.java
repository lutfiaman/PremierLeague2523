package com.example.premierleague2523.ui.results;

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
import com.example.premierleague2523.data.ListResultAdapter;
import com.example.premierleague2523.data.ResponseResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsFragment extends Fragment {

        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_results, container,false);

            ApiService apiService = ApiInstance.getRetrofitInstance().create(ApiService.class);

            Call<ResponseResult> call = apiService.getResults();

            call.enqueue(new Callback<ResponseResult>() {
                @Override
                public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {
                    ListView resultListView = root.findViewById(R.id.result_list_view);
                    ListResultAdapter listResultAdapter = new ListResultAdapter(getContext(), response.body().getResults());

                    resultListView.setAdapter(listResultAdapter);
                }

                @Override
                public void onFailure(Call<ResponseResult> call, Throwable throwable) {

                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        return root;
    }
}