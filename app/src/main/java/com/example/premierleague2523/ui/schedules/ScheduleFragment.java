package com.example.premierleague2523.ui.schedules;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.premierleague2523.MainActivity;
import com.example.premierleague2523.NotificationUtils;
import com.example.premierleague2523.R;
import com.example.premierleague2523.api.ApiInstance;
import com.example.premierleague2523.api.ApiService;
import com.example.premierleague2523.api.DbHelper;
import com.example.premierleague2523.data.ResponseSchedule;
import com.example.premierleague2523.data.Schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleFragment extends Fragment {

    private Boolean isFavourite;

    public static final int NOTIF_ID = 1;
    PendingIntent pendingIntent;

    public static Fragment scheduleFragmentInstance(Bundle bundle) {
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(bundle);

        return scheduleFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        ApiService apiService = ApiInstance.getRetrofitInstance().create(ApiService.class);

        Integer id = getArguments().getInt("ID");
        Call<ResponseSchedule> call = apiService.getSchedule(id);

        call.enqueue(new Callback<ResponseSchedule>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseSchedule> call, Response<ResponseSchedule> response) {
                List<Schedule> schedule = response.body().getSchedules();
                Schedule scheduleSingle = schedule.get(0);

                TextView txtDetailLeague = root.findViewById(R.id.tv_detail_league);
                TextView txtDetailDate = root.findViewById(R.id.tv_detail_date);
                TextView txtDetailHome = root.findViewById(R.id.tv_detail_home);
                TextView txtDetailAway = root.findViewById(R.id.tv_detail_away);
                TextView txtDetailStadium = root.findViewById(R.id.tv_detail_stadium);
                ImageButton btnFavourite = root.findViewById(R.id.btn_favourite);

                txtDetailLeague.setText("Jadwal " + scheduleSingle.getStrLeague());
                txtDetailHome.setText(scheduleSingle.getTeamHome());
                txtDetailAway.setText(scheduleSingle.getTeamAway());
                txtDetailStadium.setText("di Stadion " + scheduleSingle.getStrVenue());
                DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                DateFormat output = new SimpleDateFormat("dd MMM, yyyy - HH:mm");
                try {
                    Date scheduleDate = input.parse(scheduleSingle.getScheduleDate());
                    String formattedScheduleDate = output.format(scheduleDate);

                    txtDetailDate.setText(formattedScheduleDate);
                } catch (ParseException e) {
                    txtDetailDate.setText(scheduleSingle.getScheduleDate());
                }

                DbHelper dbHelper = new DbHelper(root.getContext());

                isFavourite = dbHelper.isFavourite(scheduleSingle.getId());

                btnFavourite.setSelected(isFavourite);
                btnFavourite.setOnClickListener(v -> {
                    if (! isFavourite) {
                        dbHelper.addFavMatch(scheduleSingle);

                        isFavourite = true;
                        btnFavourite.setSelected(isFavourite);

                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.putExtra("preloadFragment", "FavouriteFragment");
                        pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            showNotifOreo(scheduleSingle);
                        } else {
                            showNotifDefault(scheduleSingle);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseSchedule> call, Throwable throwable) {
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    public void showNotifDefault(Schedule schedule){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_star_on)
                .setContentIntent(pendingIntent)
                .setContentTitle("Favourite Team")
                .setContentText(schedule.getId() + " successfully added to the Favourite List")
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(NOTIF_ID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotifOreo(Schedule schedule){
        Notification.Builder notifBuilder = new Notification.Builder(getActivity(), NotificationUtils.ANDROID_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_star_on)
                .setContentIntent(pendingIntent)
                .setContentTitle("Favourite Team")
                .setContentText(schedule.getId() + " successfully added to the Favourite List")
                .setAutoCancel(true);

        NotificationUtils utils = new NotificationUtils(getActivity());
        utils.getManager().notify(NOTIF_ID, notifBuilder.build());
    }
}