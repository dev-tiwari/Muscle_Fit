package com.example.musclefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.musclefit.databinding.ActivitySomeMoreInformationBinding;

public class SomeMoreInformation extends AppCompatActivity {

    ActivitySomeMoreInformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySomeMoreInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}