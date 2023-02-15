package com.example.musclefit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclefit.Activities.ExerciseInformationActivity;
import com.example.musclefit.R;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.WorkoutListHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DoingExerciseAdapter extends RecyclerView.Adapter<DoingExerciseAdapter.DoingExerciseViewHolder> {

    Context context;
    ArrayList<WorkoutListHelper> workoutModels;
    FirebaseFirestore database;

    public DoingExerciseAdapter(Context context, ArrayList<WorkoutListHelper> workoutModels) {
        this.context = context;
        this.workoutModels = workoutModels;
    }

    @NonNull
    @Override
    public DoingExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doing_exercise_show, parent, false);
        return new DoingExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoingExerciseViewHolder holder, int position) {
        database = FirebaseFirestore.getInstance();
        WorkoutListHelper model = workoutModels.get(position);

        String id = model.getExId();

        database.collection("workoutsList")
                        .document(id)
                                .get().addOnSuccessListener(documentSnapshot -> {
                                    ExerciseModel mode = documentSnapshot.toObject(ExerciseModel.class);
                                    assert mode != null;
                                    holder.exName.setText(mode.getExerciseName());
                                    holder.time.setText(mode.getTimeTaken());
                                    holder.equip.setText(mode.getEquipmentsUsed());
                                    Glide.with(context).load(mode.getExerciseImage()).into(holder.image);
                                }).addOnFailureListener(e -> Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ExerciseInformationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workoutModels.size();
    }

    public static class DoingExerciseViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView exName;
        TextView time;
        TextView equip;

        public DoingExerciseViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.exerImage);
            exName = itemView.findViewById(R.id.exerciseName);
            time = itemView.findViewById(R.id.timeExercise);
            equip = itemView.findViewById(R.id.equipExerciseUsed);
        }
    }
}
