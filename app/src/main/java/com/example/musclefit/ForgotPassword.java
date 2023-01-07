package com.example.musclefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.musclefit.databinding.ActivityForgotPasswordBinding;

public class ForgotPassword extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}