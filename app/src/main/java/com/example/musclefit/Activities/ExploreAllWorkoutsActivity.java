package com.example.musclefit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.musclefit.Adapters.ExerciseAdapter;
import com.example.musclefit.Adapters.ExerciseLibraryAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.databinding.ActivityExploreAllWorkoutsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExploreAllWorkoutsActivity extends AppCompatActivity {

    ActivityExploreAllWorkoutsBinding binding;
    FirebaseFirestore database;
    ProgressDialog dialog;
    int run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreAllWorkoutsBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        dialog.show();

        run = getIntent().getIntExtra("run", 2);

        setContentView(binding.getRoot());

        if (run == 0) {

            binding.materialToolbar5.setTitle("Workouts Library");

            ArrayList<ExerciseModel> fatBurning = new ArrayList<>();
            ArrayList<ExerciseModel> abs = new ArrayList<>();
            ArrayList<ExerciseModel> chest = new ArrayList<>();
            ArrayList<ExerciseModel> legs = new ArrayList<>();
            ArrayList<ExerciseModel> arms = new ArrayList<>();
            ArrayList<ExerciseModel> back = new ArrayList<>();
            ArrayList<ExerciseModel> shoulder = new ArrayList<>();
            ArrayList<ExerciseModel> other = new ArrayList<>();

            ExerciseAdapter fatAdapter = new ExerciseAdapter(this, fatBurning);
            ExerciseAdapter absAdapter = new ExerciseAdapter(this, abs);
            ExerciseAdapter chestAdapter = new ExerciseAdapter(this, chest);
            ExerciseAdapter legsAdapter = new ExerciseAdapter(this, legs);
            ExerciseAdapter armsAdapter = new ExerciseAdapter(this, arms);
            ExerciseAdapter backAdapter = new ExerciseAdapter(this, back);
            ExerciseAdapter shoulderAdapter = new ExerciseAdapter(this, shoulder);
            ExerciseAdapter otherAdapter = new ExerciseAdapter(this, other);

            database.collection("exercises")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            dialog.dismiss();
                            fatBurning.clear();
                            abs.clear();
                            chest.clear();
                            legs.clear();
                            arms.clear();
                            back.clear();
                            shoulder.clear();
                            other.clear();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                model.setExerciseId(snapshot.getId());
                                if (model.getExerciseType().contains("fat burning")) {
                                    fatBurning.add(model);
                                } else if (model.getExerciseType().contains("abs")) {
                                    abs.add(model);
                                } else if (model.getExerciseType().contains("chest")) {
                                    chest.add(model);
                                } else if (model.getExerciseType().contains("legs")) {
                                    legs.add(model);
                                } else if (model.getExerciseType().contains("arms")) {
                                    arms.add(model);
                                } else if (model.getExerciseType().contains("back")) {
                                    back.add(model);
                                } else if (model.getExerciseType().contains("shoulder")) {
                                    shoulder.add(model);
                                } else {
                                    other.add(model);
                                }
                                fatAdapter.notifyDataSetChanged();
                                absAdapter.notifyDataSetChanged();
                                chestAdapter.notifyDataSetChanged();
                                legsAdapter.notifyDataSetChanged();
                                armsAdapter.notifyDataSetChanged();
                                backAdapter.notifyDataSetChanged();
                                shoulderAdapter.notifyDataSetChanged();
                                otherAdapter.notifyDataSetChanged();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(ExploreAllWorkoutsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            binding.fatWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.fatWorkouts.setAdapter(fatAdapter);

            binding.absWorkout.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.absWorkout.setAdapter(absAdapter);

            binding.chestWorkout.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.chestWorkout.setAdapter(chestAdapter);

            binding.legsWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.legsWorkouts.setAdapter(legsAdapter);

            binding.armsWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.armsWorkouts.setAdapter(armsAdapter);

            binding.backWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.backWorkouts.setAdapter(backAdapter);

            binding.shoulderWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.shoulderWorkouts.setAdapter(shoulderAdapter);

            binding.otherWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.otherWorkouts.setAdapter(otherAdapter);

        } else {

            binding.materialToolbar5.setTitle("Exercises Library");

            ArrayList<ExerciseModel> fatBurning = new ArrayList<>();
            ArrayList<ExerciseModel> abs = new ArrayList<>();
            ArrayList<ExerciseModel> chest = new ArrayList<>();
            ArrayList<ExerciseModel> legs = new ArrayList<>();
            ArrayList<ExerciseModel> arms = new ArrayList<>();
            ArrayList<ExerciseModel> back = new ArrayList<>();
            ArrayList<ExerciseModel> shoulder = new ArrayList<>();
            ArrayList<ExerciseModel> other = new ArrayList<>();

            ExerciseLibraryAdapter fatAdapter = new ExerciseLibraryAdapter(this, fatBurning);
            ExerciseLibraryAdapter absAdapter = new ExerciseLibraryAdapter(this, abs);
            ExerciseLibraryAdapter chestAdapter = new ExerciseLibraryAdapter(this, chest);
            ExerciseLibraryAdapter legsAdapter = new ExerciseLibraryAdapter(this, legs);
            ExerciseLibraryAdapter armsAdapter = new ExerciseLibraryAdapter(this, arms);
            ExerciseLibraryAdapter backAdapter = new ExerciseLibraryAdapter(this, back);
            ExerciseLibraryAdapter shoulderAdapter = new ExerciseLibraryAdapter(this, shoulder);
            ExerciseLibraryAdapter otherAdapter = new ExerciseLibraryAdapter(this, other);

            database.collection("workoutsList")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            dialog.dismiss();
                            fatBurning.clear();
                            abs.clear();
                            chest.clear();
                            legs.clear();
                            arms.clear();
                            back.clear();
                            shoulder.clear();
                            other.clear();
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                model.setExerciseId(snapshot.getId());
                                if (model.getExerciseType().contains("fat burning")) {
                                    fatBurning.add(model);
                                } else if (model.getExerciseType().contains("abs")) {
                                    abs.add(model);
                                } else if (model.getExerciseType().contains("chest")) {
                                    chest.add(model);
                                } else if (model.getExerciseType().contains("legs")) {
                                    legs.add(model);
                                } else if (model.getExerciseType().contains("arms")) {
                                    arms.add(model);
                                } else if (model.getExerciseType().contains("back")) {
                                    back.add(model);
                                } else if (model.getExerciseType().contains("shoulder")) {
                                    shoulder.add(model);
                                } else {
                                    other.add(model);
                                }
                                fatAdapter.notifyDataSetChanged();
                                absAdapter.notifyDataSetChanged();
                                chestAdapter.notifyDataSetChanged();
                                legsAdapter.notifyDataSetChanged();
                                armsAdapter.notifyDataSetChanged();
                                backAdapter.notifyDataSetChanged();
                                shoulderAdapter.notifyDataSetChanged();
                                otherAdapter.notifyDataSetChanged();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(ExploreAllWorkoutsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            binding.fatWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.fatWorkouts.setAdapter(fatAdapter);

            binding.absWorkout.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.absWorkout.setAdapter(absAdapter);

            binding.chestWorkout.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.chestWorkout.setAdapter(chestAdapter);

            binding.legsWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.legsWorkouts.setAdapter(legsAdapter);

            binding.armsWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.armsWorkouts.setAdapter(armsAdapter);

            binding.backWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.backWorkouts.setAdapter(backAdapter);

            binding.shoulderWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.shoulderWorkouts.setAdapter(shoulderAdapter);

            binding.otherWorkouts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            binding.otherWorkouts.setAdapter(otherAdapter);

        }
    }
}