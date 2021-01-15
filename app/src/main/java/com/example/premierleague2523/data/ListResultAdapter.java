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
import com.example.premierleague2523.ui.results.ResultFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListResultAdapter extends BaseAdapter {
    private final List<Result> results;
    private final Context context;

    public ListResultAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return this.results.size();
    }

    @Override
    public Object getItem(int i) {
        return this.results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.result_row,viewGroup,false);
        }

        TextView txtTeamHome = view.findViewById(R.id.tv_result_home);
        TextView txtTeamAway = view.findViewById(R.id.tv_result_away);
        TextView txtHomeScore = view.findViewById(R.id.tv_result_home_score);
        TextView txtAwayScore = view.findViewById(R.id.tv_result_away_score);
        TextView txtResultDate = view.findViewById(R.id.tv_result_date);

        final Result result = results.get(i);

        txtTeamHome.setText(result.getTeamHome());
        txtTeamAway.setText(result.getTeamAway());
        txtHomeScore.setText(String.valueOf(result.getHomeScore()));
        txtAwayScore.setText(String.valueOf(result.getAwayScore()));

        DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat output = new SimpleDateFormat("dd MMM, yyyy - HH:mm");
        try {
            Date resultDate = input.parse(result.getResultDate());
            String formattedResultDate = output.format(resultDate);

            txtResultDate.setText(formattedResultDate);
        }
        catch (ParseException e) {
            txtResultDate.setText(result.getResultDate());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putInt("ID", result.getId());

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Fragment resultFragment = ResultFragment.resultFragmentInstance(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment,resultFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
