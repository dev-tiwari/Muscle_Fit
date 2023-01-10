package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.musclefit.databinding.ActivitySetGoalsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SetGoals extends AppCompatActivity {

    ActivitySetGoalsBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetGoalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        database = FirebaseFirestore.getInstance();

        binding.loseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Personalize personalize = new Personalize("To Lose Weight");
                database.collection("users")
                        .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .collection("personalize")
                        .add(personalize)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                dialog.dismiss();
                                if (task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(), SomeMoreInformation.class));
                                    finish();
                                } else {
                                    Toast.makeText(SetGoals.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.moreActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Personalize personalize = new Personalize("To be More Active");
                database.collection("users")
                        .document(auth.getCurrentUser().getUid())
                        .collection("personalize")
                        .add(personalize)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                dialog.dismiss();
                                if (task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(), SomeMoreInformation.class));
                                    finish();
                                } else {
                                    Toast.makeText(SetGoals.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.buildMuscles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Personalize personalize = new Personalize("Build Muscles");
                database.collection("users")
                        .document(auth.getCurrentUser().getUid())
                        .collection("personalize")
                        .add(personalize)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                dialog.dismiss();
                                if (task.isSuccessful()){
                                    startActivity(new Intent(getApplicationContext(), SomeMoreInformation.class));
                                    finish();
                                } else {
                                    Toast.makeText(SetGoals.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

    }
}