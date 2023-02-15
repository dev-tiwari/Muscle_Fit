package com.example.musclefit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musclefit.R;
import com.example.musclefit.User_Helper_Classes.ShowingExerciseStepsModel;

import java.util.ArrayList;

public class ShowingExerciseStepsAdapter extends RecyclerView.Adapter<ShowingExerciseStepsAdapter.ShowingExerciseStepsViewHolder> {

    Context context;
    ArrayList<ShowingExerciseStepsModel> showingExerciseStepsModels;

    public ShowingExerciseStepsAdapter(Context context, ArrayList<ShowingExerciseStepsModel> showingExerciseStepsModels) {
        this.context = context;
        this.showingExerciseStepsModels = showingExerciseStepsModels;
    }

    @NonNull
    @Override
    public ShowingExerciseStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.steps_showing, parent, false);
        return new ShowingExerciseStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowingExerciseStepsViewHolder holder, int position) {
        ShowingExerciseStepsModel model = showingExerciseStepsModels.get(position);
        holder.textView.setText(model.getSteps());
    }

    @Override
    public int getItemCount() {
        return showingExerciseStepsModels.size();
    }

    public static class ShowingExerciseStepsViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ShowingExerciseStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView37);
        }
    }
}
