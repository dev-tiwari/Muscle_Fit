package com.example.musclefit.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musclefit.Adapters.ExerciseAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.Activities.ExploreAllWorkoutsActivity;
import com.example.musclefit.Activities.Favourites;
import com.example.musclefit.databinding.FragmentWorkoutBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkoutFragment extends Fragment {

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentWorkoutBinding binding;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Just a Minute...");
        dialog.show();

        ArrayList<ExerciseModel> topExercises = new ArrayList<>();
        ArrayList<ExerciseModel> absExercises = new ArrayList<>();
        ArrayList<ExerciseModel> legsExercises = new ArrayList<>();
        ArrayList<ExerciseModel> fatExercises = new ArrayList<>();

        ExerciseAdapter absAdapter = new ExerciseAdapter(getContext(), absExercises);
        ExerciseAdapter legsAdapter = new ExerciseAdapter(getContext(), legsExercises);
        ExerciseAdapter topAdapter = new ExerciseAdapter(getContext(), topExercises);
        ExerciseAdapter fatAdapter = new ExerciseAdapter(getContext(), fatExercises);

        database.collection("exercises")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        topExercises.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                            model.setExerciseId(snapshot.getId());
                            topExercises.add(model);
                        }
                        topAdapter.notifyDataSetChanged();
                    }
                });

        database.collection("exercises")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        dialog.dismiss();
                        legsExercises.clear();
                        absExercises.clear();
                        fatExercises.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                            model.setExerciseId(snapshot.getId());
                            if (model.getExerciseType().contains("abs")) {
                                absExercises.add(model);
                            } else if (model.getExerciseType().contains("legs")) {
                                legsExercises.add(model);
                            } else if (model.getExerciseType().contains("fat burning")) {
                                fatExercises.add(model);
                            }
                        }
                        absAdapter.notifyDataSetChanged();
                        legsAdapter.notifyDataSetChanged();
                        fatAdapter.notifyDataSetChanged();
                    }
                });

        binding.contentTopPicks.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        binding.contentTopPicks.setAdapter(topAdapter);

        binding.absRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.absRecyclerView.setAdapter(absAdapter);

        binding.exercisesLegs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.exercisesLegs.setAdapter(legsAdapter);

        binding.exercisesFat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.exercisesFat.setAdapter(fatAdapter);

        binding.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Favourites.class));
            }
        });

        binding.exploreWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ExploreAllWorkoutsActivity.class);
                intent.putExtra("run", 0);
                startActivity(intent);

            }
        });

        binding.exerciseLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ExploreAllWorkoutsActivity.class);
                intent.putExtra("run", 1);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}