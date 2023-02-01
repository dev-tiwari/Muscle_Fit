package com.example.musclefit;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DoingExerciseAdapter extends RecyclerView.Adapter<DoingExerciseAdapter.DoingExerciseViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;
    ProgressDialog dialog;
    FirebaseFirestore database;
    FirebaseAuth auth;

    public DoingExerciseAdapter(Context context, ArrayList<ExerciseModel> exerciseModels) {
        this.context = context;
        this.exerciseModels = exerciseModels;
    }

    @NonNull
    @Override
    public DoingExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doing_exercise_show, parent, false);
        return new DoingExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoingExerciseViewHolder holder, int position) {
        ExerciseModel model = exerciseModels.get(position);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Just a Minute...");

        holder.exName.setText(model.getExerciseName());
        holder.time.setText(model.getTimeTaken());
        holder.equip.setText(model.getEquipmentsUsed());
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

    public class DoingExerciseViewHolder extends RecyclerView.ViewHolder {

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
