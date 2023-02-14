package com.example.musclefit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;

    public WorkoutAdapter(Context context, ArrayList<ExerciseModel> exerciseModels) {
        this.context = context;
        this.exerciseModels = exerciseModels;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_showing, null);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        ExerciseModel model = exerciseModels.get(position);

        holder.exName.setText(model.getExerciseName());
        holder.time.setText(model.getTimeTaken());
        Glide.with(context).load(model.getExerciseImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView exName;
        TextView time;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.wkImage);
            exName = itemView.findViewById(R.id.wkName);
            time = itemView.findViewById(R.id.wkTimeTaken);
        }
    }

}
