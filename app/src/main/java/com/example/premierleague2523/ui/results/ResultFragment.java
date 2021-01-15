package com.example.premierleague2523.ui.results;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierleague2523.R;
import com.example.premierleague2523.api.ApiInstance;
import com.example.premierleague2523.api.ApiService;
import com.example.premierleague2523.data.ResponseResult;
import com.example.premierleague2523.data.Result;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultFragment extends Fragment {

    public static Fragment resultFragmentInstance(Bundle bundle) {
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);

        return resultFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result,container,false);
        ApiService apiService = ApiInstance.getRetrofitInstance().create(ApiService.class);

        Integer id = getArguments().getInt("ID");
        Call<ResponseResult> call = apiService.getResult(id);

        call.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseResult> call, Response<ResponseResult> response) {
                List<Result> result = response.body().getResults();
                Result resultSingle = result.get(0);

                TextView txtResultDetailLeague = root.findViewById(R.id.tv_result_detail_league);
                TextView txtResultDetailDate = root.findViewById(R.id.tv_result_detail_date);
                TextView txtResultDetailHome = root.findViewById(R.id.tv_result_detail_home);
                TextView txtResultDetailAway = root.findViewById(R.id.tv_result_detail_away);
                TextView txtResultDetailHomeScore = root.findViewById(R.id.tv_result_detail_home_score);
                TextView txtResultDetailAwayScore = root.findViewById(R.id.tv_result_detail_away_score);
                TextView txtResultDetailStadium = root.findViewById(R.id.tv_result_detail_stadium);

                txtResultDetailLeague.setText("Hasil " + resultSingle.getStrLeague());
                txtResultDetailHome.setText(resultSingle.getTeamHome());
                txtResultDetailAway.setText(resultSingle.getTeamAway());
                txtResultDetailHomeScore.setText(String.valueOf(resultSingle.getHomeScore()));
                txtResultDetailAwayScore.setText(String.valueOf(resultSingle.getAwayScore()));
                txtResultDetailStadium.setText("di Stadion " + resultSingle.getStrVenue());
                DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                DateFormat output = new SimpleDateFormat("dd MM, yyyy - HH:mm");
                try {
                    Date resultDate = input.parse(resultSingle.getResultDate());
                    String formattedResultDate = output.format(resultDate);

                    txtResultDetailDate.setText(formattedResultDate);
                } catch (ParseException e) {
                    txtResultDetailDate.setText(resultSingle.getResultDate());
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}