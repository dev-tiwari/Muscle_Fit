package com.example.musclefit.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclefit.Adapters.DoingExerciseAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.WorkoutListHelper;
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
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        ArrayList<WorkoutListHelper> warmUpExercises = new ArrayList<>();
        ArrayList<WorkoutListHelper> circuitExercises = new ArrayList<>();
        ArrayList<WorkoutListHelper> coolDownExercises = new ArrayList<>();

        DoingExerciseAdapter warmUpAdapter = new DoingExerciseAdapter(getApplicationContext(), warmUpExercises);
        DoingExerciseAdapter circuitAdapter = new DoingExerciseAdapter(getApplicationContext(), circuitExercises);
        DoingExerciseAdapter coolDownAdapter = new DoingExerciseAdapter(getApplicationContext(), coolDownExercises);

        database.collection("exercises")
                .document(exId)
                .collection("list")
                .orderBy("index")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        warmUpExercises.clear();
                        circuitExercises.clear();
                        coolDownExercises.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            WorkoutListHelper model = snapshot.toObject(WorkoutListHelper.class);
                            model.setId(snapshot.getId());
                                if (model.getExerciseType().contains("warm up")) {
                                    warmUpExercises.add(model);
                                } else if (model.getExerciseType().contains("cool down")) {
                                    coolDownExercises.add(model);
                                } else {
                                    circuitExercises.add(model);
                                }
                        }
                        binding.exercisesCount.setText(String.valueOf(warmUpExercises.size() + circuitExercises.size() + coolDownExercises.size()));
                        warmUpAdapter.notifyDataSetChanged();
                        circuitAdapter.notifyDataSetChanged();
                        coolDownAdapter.notifyDataSetChanged();
                    }
                });

        binding.warmUpExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.warmUpExercises.setAdapter(warmUpAdapter);

        binding.circuitExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.circuitExercises.setAdapter(circuitAdapter);

        binding.coolDownExercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.coolDownExercises.setAdapter(coolDownAdapter);
    }
}