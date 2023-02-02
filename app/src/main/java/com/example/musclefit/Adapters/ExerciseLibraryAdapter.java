package com.example.musclefit.Adapters;

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
import com.example.musclefit.Activities.ExerciseInformationActivity;
import com.example.musclefit.R;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;

import java.util.ArrayList;

public class ExerciseLibraryAdapter extends RecyclerView.Adapter<ExerciseLibraryAdapter.ExerciseLibraryViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;

    public ExerciseLibraryAdapter(Context context, ArrayList<ExerciseModel> exerciseModels) {
        this.context = context;
        this.exerciseModels = exerciseModels;
    }

    @NonNull
    @Override
    public ExerciseLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doing_exercise_show, parent, false);
        return new ExerciseLibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseLibraryViewHolder holder, int position) {
        ExerciseModel model = exerciseModels.get(position);
        holder.name.setText(model.getExerciseName());
        holder.state.setText(model.getEquipmentsUsed());
        holder.time.setText(model.getTimeTaken());
        Glide.with(context).load(model.getExerciseImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseInformationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", model.getExerciseId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public class ExerciseLibraryViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView time;
        TextView state;

        public ExerciseLibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exerImage);
            name = itemView.findViewById(R.id.exerciseName);
            time = itemView.findViewById(R.id.timeExercise);
            state = itemView.findViewById(R.id.equipExerciseUsed);
        }
    }
}
