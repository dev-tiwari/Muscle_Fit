package com.example.musclefit.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.example.musclefit.Activities.WorkoutInformationActivity;
import com.example.musclefit.R;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.FavHelper;
import com.example.musclefit.User_Helper_Classes.FavouriteHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    Context context;
    ArrayList<ExerciseModel> exerciseModels;
    int viewing;
    ProgressDialog dialog;
    FirebaseFirestore database;
    FirebaseAuth auth;

    public ExerciseAdapter(Context context, ArrayList<ExerciseModel>exerciseModels) {
        this.context = context;
        this.exerciseModels = exerciseModels;
    }

    public ExerciseAdapter(Context context, ArrayList<ExerciseModel> exerciseModels, int viewing) {
        this.context = context;
        this.exerciseModels = exerciseModels;
        this.viewing = viewing;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.exercise_showing_layout, null);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseModel model = exerciseModels.get(position);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Just a Minute...");

        if (viewing == 1){
            holder.exName.setText(model.getExerciseName());
            holder.timeTaken.setText(model.getTimeTaken());

            if (model.getExerciseType() != null) {
                if (model.getExerciseType().contains("k1")) {
                    holder.state.setText(R.string.k1_beg);
                } else if (model.getExerciseType().contains("k2")) {
                    holder.state.setText(R.string.k2_int);
                } else if (model.getExerciseType().contains("k3")) {
                    holder.state.setText(R.string.k3_adv);
                }
            } else {
                holder.state.setVisibility(View.INVISIBLE);
                holder.space.setVisibility(View.INVISIBLE);
            }

            Glide.with(context).load(model.getExerciseImage()).into(holder.image);

        } else {
            holder.exName.setText(model.getExerciseName());
            holder.timeTaken.setText(model.getTimeTaken());

            if (model.getState() != null) {
                switch (model.getState()) {
                    case "K1":
                        holder.state.setText(R.string.k1_beg);
                        break;
                    case "K2":
                        holder.state.setText(R.string.k2_int);
                        break;
                    case "K3":
                        holder.state.setText(R.string.k3_adv);
                        break;
                }
            } else {
                holder.state.setVisibility(View.INVISIBLE);
                holder.space.setVisibility(View.INVISIBLE);
            }

            Glide.with(context).load(model.getExerciseImage()).into(holder.image);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, WorkoutInformationActivity.class);
                intent.putExtra("exId", model.getExerciseId());
                context.startActivity(intent);
            });

            holder.itemView.setOnLongClickListener(view -> {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Favourites")
                        .setMessage("Add to Favourites. The workout will be added to favourites.")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            final boolean[] flag = {true};
                            dialog.show();
                            database.collection("users")
                                    .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                    .collection("favourites")
                                    .get().addOnSuccessListener(queryDocumentSnapshots -> {
                                        dialog.dismiss();
                                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                            FavouriteHelper help = snapshot.toObject(FavouriteHelper.class);
                                            assert help != null;
                                            if (model.getExerciseId().equals(help.getExId())) {
                                                Toast.makeText(context, "Already in Favourites.", Toast.LENGTH_SHORT).show();
                                                flag[0] = false;
                                                break;
                                            }
                                        }
                                        if (flag[0]) {
                                            FavHelper helper = new FavHelper(model.getExerciseId());
                                            database.collection("users")
                                                    .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                                    .collection("favourites")
                                                    .add(helper).addOnCompleteListener(task -> {
                                                        dialog.dismiss();
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(context, "Successfully Added to Favourites.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(context, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }).addOnFailureListener(e -> {
                                        dialog.dismiss();
                                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }).setNegativeButton("No", (dialogInterface, i) -> {
                        }).setIcon(R.drawable.favourites).show();
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView exName;
        TextView timeTaken;
        TextView state;
        TextView space;


        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exerImage);
            exName = itemView.findViewById(R.id.exName);
            timeTaken = itemView.findViewById(R.id.timeTaken);
            state = itemView.findViewById(R.id.exState);
            space = itemView.findViewById(R.id.textView29);
        }
    }

}

