package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");

        binding.forgot.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(i);
            finish();
        });

        binding.createaccount.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignUp.class);
            startActivity(i);
            finish();
        });

        binding.signin.setOnClickListener(view -> {
            String email, pass;

            email = Objects.requireNonNull(binding.signInEmailAddress.getEditText()).getText().toString().trim();
            pass = Objects.requireNonNull(binding.signInPassword.getEditText()).getText().toString();

            if (email.isEmpty()) {
                binding.signInEmailAddress.setError("This field cannot be empty.");
            } else if (pass.isEmpty() || pass.length() < 6) {
                binding.signInPassword.setError("Password Length Should Exceed 6 Characters!");
            } else {
                dialog.show();
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), Main.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}