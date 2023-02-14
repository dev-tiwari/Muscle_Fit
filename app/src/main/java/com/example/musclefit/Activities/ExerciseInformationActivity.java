package com.example.musclefit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musclefit.Adapters.ShowingExerciseStepsAdapter;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.ShowingExerciseStepsModel;
import com.example.musclefit.databinding.ActivityExerciseInformationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExerciseInformationActivity extends AppCompatActivity {

    String inId, exId;
    ActivityExerciseInformationBinding binding;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseInformationBinding.inflate(getLayoutInflater());
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a minute...");
        dialog.show();
        setContentView(binding.getRoot());

        inId = getIntent().getStringExtra("id");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        exId = prefs.getString("exerciseId", null);

        database.collection("workoutsList")
                .document(inId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        dialog.dismiss();
                        ExerciseModel model = documentSnapshot.toObject(ExerciseModel.class);
                        binding.infoName.setText(model.getExerciseName());
                        Glide.with(getApplicationContext()).load(model.getExerciseImage()).into(binding.informationImage);
                        binding.equipInformation.setText(model.getEquipmentsUsed());
                        binding.des.setText(model.getDescription());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(ExerciseInformationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        ArrayList<ShowingExerciseStepsModel> list = new ArrayList<>();

        ShowingExerciseStepsAdapter adapter = new ShowingExerciseStepsAdapter(this, list);

        database.collection("workoutsList")
                .document(inId)
                .collection("steps")
                .orderBy("index")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list.clear();
                        dialog.dismiss();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            ShowingExerciseStepsModel model = snapshot.toObject(ShowingExerciseStepsModel.class);
                            model.setId(snapshot.getId());
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(ExerciseInformationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.stepsTo.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.stepsTo.setAdapter(adapter);

    }
}