package com.example.musclefit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;
    ProgressDialog dialog;
    FirebaseFirestore database;
    FirebaseAuth auth;

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
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Just a Minute...");

        holder.exName.setText(model.getExerciseName());
        holder.timeTaken.setText(model.getTimeTaken());

        switch (model.getState()) {
            case "K1":
                holder.state.setText("K1 Beginner");
                break;
            case "K2":
                holder.state.setText("K2 Intermediate");
                break;
            case "K3":
                holder.state.setText("K3 Advanced");
                break;
        }
        Glide.with(context).load(model.getExerciseImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkoutInformationActivity.class);
                intent.putExtra("exId", model.getExerciseId());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Favourites")
                        .setMessage("Add to Favourites")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.show();

                                FavouriteHelper helper = new FavouriteHelper(model.getExerciseId());
                                database.collection("users")
                                        .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                        .collection("favourites")
                                        .add(helper).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                dialog.dismiss();
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Successfully Added to Favourites.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).setIcon(R.drawable.favourites).show();
                return true;
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
            image = itemView.findViewById(R.id.exerImage);
            exName = itemView.findViewById(R.id.exName);
            timeTaken = itemView.findViewById(R.id.timeTaken);
            state = itemView.findViewById(R.id.exState);
        }
    }
}
