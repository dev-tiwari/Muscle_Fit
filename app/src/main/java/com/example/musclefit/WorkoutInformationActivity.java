package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclefit.databinding.ActivityWorkoutInformationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkoutInformationActivity extends AppCompatActivity {

    ActivityWorkoutInformationBinding binding;
    String exId, size;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutInformationBinding.inflate(getLayoutInflater());
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

        ArrayList<ExerciseModel> warmUp = new ArrayList<>();
        DoingExerciseAdapter warmUpAdapter = new DoingExerciseAdapter(getApplicationContext(), warmUp);

        database.collection("exercises")
                .document(exId)
                .collection("list")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        warmUp.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                            model.setExerciseId(snapshot.getId());
                            if (model.getExerciseType().contains("warm up")) {
                                warmUp.add(model);
                            }
                        }
                        warmUpAdapter.notifyDataSetChanged();
                    }
                });

//        size = String.valueOf(warmUp.size());
//
//        binding.exercisesCount.setText(size);


        binding.warmUpExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.warmUpExercises.setAdapter(warmUpAdapter);
    }
}