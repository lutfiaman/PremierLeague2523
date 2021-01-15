package com.example.premierleague2523.ui.favourite;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.premierleague2523.R;
import com.example.premierleague2523.api.DbHelper;
import com.example.premierleague2523.data.ListScheduleAdapter;
import com.example.premierleague2523.data.Schedule;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {

    private List<Schedule> scheduleList = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        DbHelper dbHelper = new DbHelper(root.getContext());
        List<Schedule> schedules = dbHelper.getAllFavMatch();

        if (schedules.size() > 0) {
            this.scheduleList = schedules;
            ListView matchListView = root.findViewById(R.id.matches_list_view);
            ListScheduleAdapter listScheduleAdapter = new ListScheduleAdapter(getContext(), schedules);

            matchListView.setAdapter(listScheduleAdapter);
            matchListView.setLongClickable(true);
            matchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                    showAddDeleteDialog(pos);
                    return true;
                }
            });
        } else {
            Toast.makeText(getContext(), "Belum Punya Jadwal Favorit", Toast.LENGTH_LONG).show();
        }

        return root;
    }

    public void showAddDeleteDialog(int pos){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setCancelable(true);
        dialog.setTitle("Hapus");
        dialog.setMessage("Yakin ingin menghapus dari favorit?" );
        dialog
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DbHelper dbHelper = new DbHelper(getContext());
                        dbHelper.deleteFavMatch(scheduleList.get(pos).getIdDb());

                        AppCompatActivity activity = (AppCompatActivity) getContext();

                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new FavouriteFragment())
                                .addToBackStack(null)
                                .commit();

                        Toast.makeText(getContext(), "Jadwal dihapus dari favorit", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.show();
    }
}