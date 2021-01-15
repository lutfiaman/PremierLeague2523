package com.example.premierleague2523;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.premierleague2523.ui.favourite.FavouriteFragment;
import com.example.premierleague2523.ui.results.ResultsFragment;
import com.example.premierleague2523.ui.schedules.SchedulesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String preLoadFragment = getIntent().getStringExtra("preloadFragment");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);

        if (preLoadFragment != null) {
            if (preLoadFragment.equals("FavouriteFragment")) {
                loadFragment(new FavouriteFragment());
                navigation.setSelectedItemId(R.id.navigation_favourite);
            } else {
                loadFragment(new SchedulesFragment());
                navigation.setSelectedItemId(R.id.navigation_schedule);
            }
        } else {
            loadFragment(new SchedulesFragment());
            navigation.setSelectedItemId(R.id.navigation_schedule);
        }
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_schedule:
                fragment = new SchedulesFragment();
                break;

            case R.id.navigation_teams:
                fragment = new ResultsFragment();
                break;

            case R.id.navigation_favourite:
                fragment = new FavouriteFragment();
                break;
        }

        return loadFragment(fragment);
    }

}