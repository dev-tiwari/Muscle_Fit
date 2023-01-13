package com.example.musclefit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.musclefit.databinding.ActivityEditProfileBinding;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.editTextTextPersonName.setFocusable(false);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editTextTextPersonName.setText("Enabled");
                binding.editTextTextPersonName.setFocusableInTouchMode(true);
            }
        });

    }
}