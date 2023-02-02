package com.example.musclefit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.musclefit.Fragments.AccountFragment;
import com.example.musclefit.Fragments.DashboardFragment;
import com.example.musclefit.Fragments.PlanFragment;
import com.example.musclefit.Fragments.WorkoutFragment;
import com.example.musclefit.R;
import com.example.musclefit.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class Main extends AppCompatActivity {

    ActivityMainBinding binding;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.content.getId(), new DashboardFragment());
        transaction.commit();

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.homeMain:
                        transaction.replace(binding.content.getId(), new DashboardFragment());
                        transaction.commit();
                        break;
                    case R.id.plan:
                        transaction.replace(binding.content.getId(), new PlanFragment());
                        transaction.commit();
                        break;
                    case R.id.workout:
                        transaction.replace(binding.content.getId(), new WorkoutFragment());
                        transaction.commit();
                        break;
                    case R.id.account:
                        transaction.replace(binding.content.getId(), new AccountFragment());
                        transaction.commit();
                        break;
                }
                return true;
            }
        });

        binding.bottomNavigation.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.homeMain:
                        transaction.replace(binding.content.getId(), new DashboardFragment());
                        transaction.commit();
                                // To draw notification badge
//                                BadgeDrawable badge = binding.bottomNavigation.getOrCreateBadge(R.id.homeMain);
//                                badge.setVisible(true);
//                                badge.setNumber(99);
                        break;
                    case R.id.plan:
                        transaction.replace(binding.content.getId(), new PlanFragment());
                        transaction.commit();
                        break;
                    case R.id.workout:
                        transaction.replace(binding.content.getId(), new WorkoutFragment());
                        transaction.commit();
                        break;
                    case R.id.account:
                        transaction.replace(binding.content.getId(), new AccountFragment());
                        transaction.commit();
                        break;
                }
            }
        });
    }

}