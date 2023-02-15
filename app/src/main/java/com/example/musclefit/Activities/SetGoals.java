package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.ActivitySetGoalsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SetGoals extends AppCompatActivity {

    ActivitySetGoalsBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseFirestore database;
    String name, email, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetGoalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        database = FirebaseFirestore.getInstance();

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        binding.loseWeight.setOnClickListener(view -> {
            dialog.show();
            binding.buildMuscles.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            binding.loseWeight.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            binding.moreActive.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            User user = new User(name, email, phoneNumber, "To Lose Weight");
            database.collection("users")
                    .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                    .set(user)
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(), SomeMoreInformation.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("personalize", "To Lose Weight");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SetGoals.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.moreActive.setOnClickListener(view -> {
            dialog.show();
            binding.buildMuscles.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            binding.loseWeight.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            binding.moreActive.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            User user = new User(name, email, phoneNumber, "To be More Active");
            database.collection("users")
                    .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                    .set(user)
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), SomeMoreInformation.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("personalize", "To Be More Active");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SetGoals.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        binding.buildMuscles.setOnClickListener(view -> {
            dialog.show();
            binding.buildMuscles.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            binding.loseWeight.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            binding.moreActive.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            User user = new User(name, email, phoneNumber, "Build Muscles");
            database.collection("users")
                    .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                    .set(user)
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), SomeMoreInformation.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("personalize", "Build Muscles");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SetGoals.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}