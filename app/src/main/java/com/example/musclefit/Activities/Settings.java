package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        database = FirebaseFirestore.getInstance();

        setContentView(binding.getRoot());

        binding.materialToolbar.setTitle("Settings");

        binding.deleteAccount.setOnClickListener(view -> {
            dialog.show();
            String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
            Objects.requireNonNull(auth.getCurrentUser()).delete().addOnCompleteListener(task -> {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    database.collection("users")
                            .document(uid)
                            .delete()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User Deleted!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    startActivity(new Intent(getApplicationContext(), StartPanel.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.editProfile.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EditProfile.class)));

        binding.resetPassword.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
            intent.putExtra("reset", 1);
            startActivity(intent);
        });

        binding.updatePassword.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), UpdatePassword.class)));

    }
}