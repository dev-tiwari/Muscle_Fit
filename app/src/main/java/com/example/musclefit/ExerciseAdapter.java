package com.example.musclefit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;

    public ExerciseAdapter(Context context, ArrayList<ExerciseModel>exerciseModels) {
        this.context = context;
        this.exerciseModels = exerciseModels;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_showing_layout, null);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseModel model = exerciseModels.get(position);

        holder.exName.setText(model.getExerciseName());
        holder.timeTaken.setText(model.getTimeTaken());
        holder.state.setText(model.getState());
        Glide.with(context).load(model.getExerciseImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoShowingActivity.class);
                intent.putExtra("exId", model.getExerciseId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView exName;
        TextView timeTaken;
        TextView state;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exImage);
            exName = itemView.findViewById(R.id.exName);
            timeTaken = itemView.findViewById(R.id.timeTaken);
            state = itemView.findViewById(R.id.exState);
        }
    }
}

