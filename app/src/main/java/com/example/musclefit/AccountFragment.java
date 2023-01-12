package com.example.musclefit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musclefit.databinding.FragmentAccountBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AccountFragment extends Fragment {
    public AccountFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    FragmentAccountBinding binding;
    ProgressDialog dialog;
    FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Just a Minute...");

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(binding.navigationContent.getId(), new ProfileFragment());
        transaction.commit();

        binding.navigationRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.profile:
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                transaction.replace(binding.navigationContent.getId(), new ProfileFragment());
                                transaction.commit();
                            }
                        }, 1000);
                        break;
                    case R.id.settings:
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                transaction.replace(binding.navigationContent.getId(), new SettingsFragment());
                                transaction.commit();
                            }
                        }, 1000);
                        break;
                    case R.id.logout:
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                auth.signOut();
                                startActivity(new Intent(getContext(), StartPanel.class));
                                requireActivity().finish();
                            }
                        },1000);
                        break;
                }
                return true;
            }
        });

        binding.navigationRail.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.profile:
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                transaction.replace(binding.navigationContent.getId(), new ProfileFragment());
                                transaction.commit();
                            }
                        }, 1000);
                        break;
                    case R.id.settings:
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                transaction.replace(binding.navigationContent.getId(), new SettingsFragment());
                                transaction.commit();
                            }
                        }, 1000);
                        break;
                }
            }
        });

        return binding.getRoot();
    }
}