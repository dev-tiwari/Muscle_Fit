package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.musclefit.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        binding.forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
            }
        });

        binding.createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;

                email = binding.signInEmailAddress.getText().toString().trim();
                pass = binding.signInPassword.getText().toString();

                if (email.isEmpty()) {
                    binding.signInEmailAddress.setError("This field cannot be empty.");
                } else if (pass.isEmpty() || pass.length() < 6) {
                    binding.signInPassword.setError("Password Length Should Exceed 6 Characters!");
                } else {

                    dialog.show();

//                    if (auth.getCurrentUser().isEmailVerified()) {
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}