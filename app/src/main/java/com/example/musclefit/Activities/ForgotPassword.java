package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    int reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");

        reset = getIntent().getIntExtra("reset", 0);

        setContentView(binding.getRoot());

        if (reset == 1) {
            binding.signIn.setVisibility(View.INVISIBLE);
            binding.already.setVisibility(View.INVISIBLE);
            binding.textView5.setVisibility(View.INVISIBLE);
            binding.textView22.setVisibility(View.VISIBLE);
        }

        binding.already.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
            finish();
        });

        binding.signIn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SignIn.class));
            finish();
        });

        binding.forgotPasswordButton.setOnClickListener(view -> {
            dialog.show();
            String email;
            email = Objects.requireNonNull(binding.emailForgot.getEditText()).getText().toString().trim();

            if (email.isEmpty()) {
                dialog.dismiss();
                binding.emailForgot.setError("This field cannot be blank.");
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Password Reset email has been sent.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), SignIn.class));
                        finish();
                    } else {
                        Toast.makeText(ForgotPassword.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}