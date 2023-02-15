package com.example.musclefit.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musclefit.Adapters.ExerciseAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.databinding.FragmentPlanBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlanBinding.inflate(inflater, container, false);
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Just a Minute...");
        dialog.show();

        ArrayList<ExerciseModel> k17day = new ArrayList<>();
        ArrayList<ExerciseModel> k114day = new ArrayList<>();
        ArrayList<ExerciseModel> k130day = new ArrayList<>();
        ArrayList<ExerciseModel> k27day = new ArrayList<>();
        ArrayList<ExerciseModel> k214day = new ArrayList<>();
        ArrayList<ExerciseModel> k230day = new ArrayList<>();

        ExerciseAdapter k17dayAdapter = new ExerciseAdapter(getContext(), k17day, 1);
        ExerciseAdapter k114dayAdapter = new ExerciseAdapter(getContext(), k114day, 1);
        ExerciseAdapter k130dayAdapter = new ExerciseAdapter(getContext(), k130day, 1);
        ExerciseAdapter k27dayAdapter = new ExerciseAdapter(getContext(), k27day, 1);
        ExerciseAdapter k214dayAdapter = new ExerciseAdapter(getContext(), k214day, 1);
        ExerciseAdapter k230dayAdapter = new ExerciseAdapter(getContext(), k230day, 1);

        database.collection("plans")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    k17day.clear();
                    k114day.clear();
                    k130day.clear();
                    k27day.clear();
                    k214day.clear();
                    k230day.clear();
                    dialog.dismiss();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                        assert model != null;
                        model.setExerciseId(snapshot.getId());
                        if (model.getExerciseType().contains("7 day k1")) {
                            k17day.add(model);
                        } else if (model.getExerciseType().contains("14 day k1")) {
                            k114day.add(model);
                        } else if (model.getExerciseType().contains("30 day k1")) {
                            k130day.add(model);
                        } else if (model.getExerciseType().contains("7 day k2")) {
                            k27day.add(model);
                        } else if (model.getExerciseType().contains("14 day k2")) {
                            k214day.add(model);
                        } else if (model.getExerciseType().contains("30 day k2")) {
                            k230day.add(model);
                        }
                    }
                    k17dayAdapter.notifyDataSetChanged();
                    k114dayAdapter.notifyDataSetChanged();
                    k130dayAdapter.notifyDataSetChanged();
                    k27dayAdapter.notifyDataSetChanged();
                    k214dayAdapter.notifyDataSetChanged();
                    k230dayAdapter.notifyDataSetChanged();
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });

        binding.reck17.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck17.setAdapter(k17dayAdapter);

        binding.reck114.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck114.setAdapter(k114dayAdapter);

        binding.reck130.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck130.setAdapter(k130dayAdapter);

        binding.reck27.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck27.setAdapter(k27dayAdapter);

        binding.reck214.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck214.setAdapter(k214dayAdapter);

        binding.reck230.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.reck230.setAdapter(k230dayAdapter);

        return binding.getRoot();
    }
}