package com.example.musclefit.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.databinding.ActivityStartPanelBinding;
import com.google.firebase.auth.FirebaseAuth;

public class StartPanel extends AppCompatActivity {

    ActivityStartPanelBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Main.class));
            finish();
            }

        binding.signinbutton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignIn.class);
            startActivity(i);
            finish();
        });

        binding.signupbutton.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignUp.class);
            startActivity(i);
            finish();
        });
    }
}