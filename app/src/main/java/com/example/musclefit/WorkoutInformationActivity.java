package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
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
    String exId;
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("exerciseId", exId);
        editor.apply();

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
        ArrayList<ExerciseModel> coolDown = new ArrayList<>();
        ArrayList<ExerciseModel> circuit1 = new ArrayList<>();
        ArrayList<ExerciseModel> circuit2 = new ArrayList<>();
        ArrayList<ExerciseModel> circuit3 = new ArrayList<>();

        DoingExerciseAdapter warmUpAdapter = new DoingExerciseAdapter(getApplicationContext(), warmUp);
        DoingExerciseAdapter coolDownAdapter = new DoingExerciseAdapter(getApplicationContext(), coolDown);
        DoingExerciseAdapter circuit1Adapter = new DoingExerciseAdapter(getApplicationContext(), circuit1);
        DoingExerciseAdapter circuit2Adapter = new DoingExerciseAdapter(getApplicationContext(), circuit2);
        DoingExerciseAdapter circuit3Adapter = new DoingExerciseAdapter(getApplicationContext(), circuit3);

        database.collection("exercises")
                .document(exId)
                .collection("list")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        warmUp.clear();
                        coolDown.clear();
                        circuit1.clear();
                        circuit2.clear();
                        circuit3.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                            model.setExerciseId(snapshot.getId());
                            if (model.getExerciseType() != null) {
                                if (model.getExerciseType().toLowerCase().contains("warm up")) {
                                    warmUp.add(model);
                                    binding.warmUpExercises.setVisibility(View.VISIBLE);
                                    binding.textView28.setVisibility(View.VISIBLE);
                                } else if (model.getExerciseType().toLowerCase().contains("cool down")) {
                                    coolDown.add(model);
                                    binding.coolDownExercises.setVisibility(View.VISIBLE);
                                    binding.textView36.setVisibility(View.VISIBLE);
                                } else if (model.getExerciseType().toLowerCase().contains("circuit1")) {
                                    circuit1.add(model);
                                    binding.circuit1Exercises.setVisibility(View.VISIBLE);
                                    binding.textView32.setVisibility(View.VISIBLE);
                                } else if (model.getExerciseType().toLowerCase().contains("circuit2")) {
                                    circuit2.add(model);
                                    binding.circuit2Exercises.setVisibility(View.VISIBLE);
                                    binding.textView33.setVisibility(View.VISIBLE);
                                } else if (model.getExerciseType().toLowerCase().contains("circuit3")) {
                                    circuit3.add(model);
                                    binding.circuit3Exercises.setVisibility(View.VISIBLE);
                                    binding.textView35.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        warmUpAdapter.notifyDataSetChanged();
                        coolDownAdapter.notifyDataSetChanged();
                        circuit1Adapter.notifyDataSetChanged();
                        circuit2Adapter.notifyDataSetChanged();
                        circuit3Adapter.notifyDataSetChanged();
                    }
                });

//        binding.exercisesCount.setText(s);

        binding.warmUpExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.warmUpExercises.setAdapter(warmUpAdapter);


        binding.circuit1Exercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.circuit1Exercises.setAdapter(circuit1Adapter);

        binding.circuit2Exercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.circuit2Exercises.setAdapter(circuit2Adapter);

        binding.circuit3Exercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.circuit3Exercises.setAdapter(circuit3Adapter);

        binding.coolDownExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.coolDownExercises.setAdapter(coolDownAdapter);
    }
}