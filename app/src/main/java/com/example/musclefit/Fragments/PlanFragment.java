package com.example.musclefit.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musclefit.Adapters.ExerciseAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.WorkoutAdapter;
import com.example.musclefit.databinding.FragmentPlanBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PlanFragment extends Fragment {

    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentPlanBinding binding;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlanBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Just a Minute...");
        dialog.show();

        ArrayList<ExerciseModel> k17day = new ArrayList<>();

        WorkoutAdapter k17dayAdapter = new WorkoutAdapter(getContext(), k17day);

        database.collection("plans")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        k17day.clear();
                        dialog.dismiss();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                            model.setExerciseId(snapshot.getId());
                            if (model.getExerciseType().contains("7 day k1")) {
                                k17day.add(model);
                            }
                        }
                        k17dayAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.reck17.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck17.setAdapter(k17dayAdapter);

        return binding.getRoot();
    }
}