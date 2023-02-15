package com.example.musclefit.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.musclefit.Fragments.AccountFragment;
import com.example.musclefit.Fragments.DashboardFragment;
import com.example.musclefit.Fragments.PlanFragment;
import com.example.musclefit.Fragments.WorkoutFragment;
import com.example.musclefit.R;
import com.example.musclefit.databinding.ActivityMainBinding;

public class Main extends AppCompatActivity {

    ActivityMainBinding binding;
    ProgressDialog dialog;

    @SuppressLint("NonConstantResourceId")
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

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.homeMain:
                    transaction1.replace(binding.content.getId(), new DashboardFragment());
                    transaction1.commit();
                    break;
                case R.id.plan:
                    transaction1.replace(binding.content.getId(), new PlanFragment());
                    transaction1.commit();
                    break;
                case R.id.workout:
                    transaction1.replace(binding.content.getId(), new WorkoutFragment());
                    transaction1.commit();
                    break;
                case R.id.account:
                    transaction1.replace(binding.content.getId(), new AccountFragment());
                    transaction1.commit();
                    break;
            }
            return true;
        });

        binding.bottomNavigation.setOnItemReselectedListener(item -> {
            FragmentTransaction transaction12 = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.homeMain:
                    transaction12.replace(binding.content.getId(), new DashboardFragment());
                    transaction12.commit();
                            /* To draw notification badge
                                  BadgeDrawable badge = binding.bottomNavigation.getOrCreateBadge(R.id.homeMain);
                                  badge.setVisible(true);
                                  badge.setNumber(99);*/
                    break;
                case R.id.plan:
                    transaction12.replace(binding.content.getId(), new PlanFragment());
                    transaction12.commit();
                    break;
                case R.id.workout:
                    transaction12.replace(binding.content.getId(), new WorkoutFragment());
                    transaction12.commit();
                    break;
                case R.id.account:
                    transaction12.replace(binding.content.getId(), new AccountFragment());
                    transaction12.commit();
                    break;
            }
        });
    }

}