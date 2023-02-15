package com.example.musclefit.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musclefit.Activities.ExploreAllWorkoutsActivity;
import com.example.musclefit.Activities.Favourites;
import com.example.musclefit.Adapters.ExerciseAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.FragmentWorkoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

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
    FirebaseAuth auth;
    ProgressDialog dialog;
    String personalize;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Just a Minute...");
        dialog.show();

        ArrayList<ExerciseModel> topExercises = new ArrayList<>();
        ArrayList<ExerciseModel> absExercises = new ArrayList<>();
        ArrayList<ExerciseModel> legsExercises = new ArrayList<>();
        ArrayList<ExerciseModel> fatExercises = new ArrayList<>();
        ArrayList<ExerciseModel> arms = new ArrayList<>();
        ArrayList<ExerciseModel> chest = new ArrayList<>();

        ExerciseAdapter absAdapter = new ExerciseAdapter(getContext(), absExercises);
        ExerciseAdapter legsAdapter = new ExerciseAdapter(getContext(), legsExercises);
        ExerciseAdapter topAdapter = new ExerciseAdapter(getContext(), topExercises);
        ExerciseAdapter fatAdapter = new ExerciseAdapter(getContext(), fatExercises);
        ExerciseAdapter armsAdapter = new ExerciseAdapter(getContext(), arms);
        ExerciseAdapter chestAdapter = new ExerciseAdapter(getContext(), chest);

        database.collection("users")
                        .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                .get().addOnSuccessListener(documentSnapshot -> {
                                    User user = documentSnapshot.toObject(User.class);
                                    if (Objects.requireNonNull(user).getPersonalize() != null) {
                                        personalize = user.getPersonalize();
                                    } else {
                                        personalize = "Build Muscles";
                                    }
                                    if (!personalize.equals("")) {
                                        if (personalize.equalsIgnoreCase("To be More Active")) {
                                            database.collection("exercises")
                                                    .addSnapshotListener((value, error) -> {
                                                        dialog.dismiss();
                                                        topExercises.clear();
                                                        assert value != null;
                                                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                                                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                                            assert model != null;
                                                            model.setExerciseId(snapshot.getId());
                                                            if (model.getExerciseType().contains("fat burning") && model.getState().equalsIgnoreCase("K1")) {
                                                                topExercises.add(model);
                                                            }
                                                        }
                                                        topAdapter.notifyDataSetChanged();
                                                    });
                                        } else if (personalize.equalsIgnoreCase("Build Muscles")) {
                                            database.collection("exercises")
                                                    .addSnapshotListener((value, error) -> {
                                                        dialog.dismiss();
                                                        topExercises.clear();
                                                        assert value != null;
                                                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                                                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                                            assert model != null;
                                                            model.setExerciseId(snapshot.getId());
                                                            if (model.getState().contains("K2")) {
                                                                topExercises.add(model);
                                                            }
                                                        }
                                                        topAdapter.notifyDataSetChanged();
                                                    });
                                        } else if (personalize.equalsIgnoreCase("To Lose Weight")) {
                                            database.collection("exercises")
                                                    .addSnapshotListener((value, error) -> {
                                                        dialog.dismiss();
                                                        topExercises.clear();
                                                        assert value != null;
                                                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                                                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                                            assert model != null;
                                                            model.setExerciseId(snapshot.getId());
                                                            if (model.getExerciseType().contains("fat burning") && model.getState().contains("K3")) {
                                                                topExercises.add(model);
                                                            }
                                                        }
                                                        topAdapter.notifyDataSetChanged();
                                                    });
                                        } else {
                                            database.collection("exercises")
                                                    .addSnapshotListener((value, error) -> {
                                                        dialog.dismiss();
                                                        topExercises.clear();
                                                        assert value != null;
                                                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                                                            ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                                            assert model != null;
                                                            model.setExerciseId(snapshot.getId());
                                                            topExercises.add(model);
                                                        }
                                                        topAdapter.notifyDataSetChanged();
                                                    });
                                        }
                                    } else {
                                        database.collection("exercises")
                                                .addSnapshotListener((value, error) -> {
                                                    dialog.dismiss();
                                                    topExercises.clear();
                                                    assert value != null;
                                                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                                                        ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                                                        assert model != null;
                                                        model.setExerciseId(snapshot.getId());
                                                        topExercises.add(model);
                                                    }
                                                    topAdapter.notifyDataSetChanged();
                                                });
                                    }
                                    }).addOnFailureListener(e -> Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());

        database.collection("exercises")
                .addSnapshotListener((value, error) -> {
//                        dialog.dismiss();
                    legsExercises.clear();
                    absExercises.clear();
                    fatExercises.clear();
                    chest.clear();
                    arms.clear();
                    assert value != null;
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        ExerciseModel model = snapshot.toObject(ExerciseModel.class);
                        assert model != null;
                        model.setExerciseId(snapshot.getId());
                        if (model.getExerciseType().contains("abs")) {
                            absExercises.add(model);
                        } else if (model.getExerciseType().contains("legs")) {
                            legsExercises.add(model);
                        } else if (model.getExerciseType().contains("fat burning")) {
                            fatExercises.add(model);
                        } else if (model.getExerciseType().contains("chest")) {
                            chest.add(model);
                        } else if (model.getExerciseType().contains("arms")) {
                            arms.add(model);
                        }
                    }
                    absAdapter.notifyDataSetChanged();
                    legsAdapter.notifyDataSetChanged();
                    fatAdapter.notifyDataSetChanged();
                    chestAdapter.notifyDataSetChanged();
                    armsAdapter.notifyDataSetChanged();
                });

        binding.contentTopPicks.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        binding.contentTopPicks.setAdapter(topAdapter);

        binding.absRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.absRecyclerView.setAdapter(absAdapter);

        binding.exercisesLegs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.exercisesLegs.setAdapter(legsAdapter);

        binding.exercisesFat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.exercisesFat.setAdapter(fatAdapter);

        binding.chestRecycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.chestRecycle.setAdapter(chestAdapter);

        binding.armsRecycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.armsRecycle.setAdapter(armsAdapter);


        binding.fav.setOnClickListener(view -> startActivity(new Intent(getContext(), Favourites.class)));

        binding.exploreWorkouts.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ExploreAllWorkoutsActivity.class);
            intent.putExtra("run", 0);
            startActivity(intent);
        });

        binding.exerciseLibrary.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ExploreAllWorkoutsActivity.class);
            intent.putExtra("run", 1);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}