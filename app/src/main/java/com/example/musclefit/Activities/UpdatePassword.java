package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.databinding.ActivityUpdatePasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UpdatePassword extends AppCompatActivity {

    ActivityUpdatePasswordBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());

        binding.updatePasswordButton.setOnClickListener(view -> {
            dialog.show();
            newPassword = Objects.requireNonNull(binding.newPassword.getEditText()).getText().toString().trim();
            if (newPassword.isEmpty()){
                dialog.dismiss();
                binding.newPassword.getEditText().setError("This field cannot be empty.");
            }
            Objects.requireNonNull(auth.getCurrentUser()).updatePassword(newPassword).addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(UpdatePassword.this, "Password has been changed successfully.", Toast.LENGTH_SHORT).show();
                    auth.signOut();
                    startActivity(new Intent(getApplicationContext(), SignIn.class));
                    finish();
                } else {
                    Toast.makeText(UpdatePassword.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}