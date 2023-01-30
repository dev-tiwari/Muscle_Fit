package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclefit.databinding.ActivityExerciseInformationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ExerciseInformationActivity extends AppCompatActivity {

    ActivityExerciseInformationBinding binding;
    String exId;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseInformationBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());

        dialog.show();
        exId = getIntent().getStringExtra("exId");

        database.collection("exercises")
                .document(exId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        dialog.dismiss();
                        ExerciseModel model = documentSnapshot.toObject(ExerciseModel.class);
                        binding.exInformationName.setText(model.getExerciseName());
                        Glide.with(getApplicationContext()).load(model.getExerciseImage()).into(binding.exInformationImage);
                        binding.calorieCount.setText(model.getCalorieCount());
                        binding.exTimeTaken.setText(model.getTimeTaken());
                        binding.exLevel.setText(model.getState());
                        binding.exDescription.setText(model.getDescription());
                        binding.equipmentsUsed.setText(model.getEquipmentsUsed());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}