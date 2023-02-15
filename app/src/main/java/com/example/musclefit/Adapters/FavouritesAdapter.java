package com.example.musclefit.Adapters;

import android.app.Activity;
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
import com.example.musclefit.Activities.Favourites;
import com.example.musclefit.Activities.WorkoutInformationActivity;
import com.example.musclefit.R;
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.FavouriteHelper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>{

    Context context;
    ArrayList<FavouriteHelper> favouriteHelpers;
    FirebaseFirestore database;
    FirebaseAuth auth;
    ProgressDialog dialog;

    public FavouritesAdapter(Context context, ArrayList<FavouriteHelper> favouriteHelpers) {
        this.context = context;
        this.favouriteHelpers = favouriteHelpers;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doing_exercise_show, parent, false);
        return new FavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Just a minute...");
        FavouriteHelper model = favouriteHelpers.get(position);
        String id = model.getExId();

        database.collection("exercises")
                .document(id)
                .get().addOnSuccessListener(documentSnapshot -> {
                    ExerciseModel mode = documentSnapshot.toObject(ExerciseModel.class);
                    assert mode != null;
                    holder.name.setText(mode.getExerciseName());
                    if (mode.getState().contains("K1")) {
                        holder.state.setText(R.string.k1_beg);
                    } else if (mode.getState().contains("K2")) {
                        holder.state.setText(R.string.k2_int);
                    } else if (mode.getState().contains("K3")) {
                        holder.state.setText(R.string.k3_adv);
                    }
                    holder.time.setText(mode.getTimeTaken());
                    Glide.with(context).load(mode.getExerciseImage()).into(holder.image);
                });

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, WorkoutInformationActivity.class);
            intent.putExtra("exId", model.getExId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(view -> {
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Favourites")
                    .setMessage("Delete from favourites. The workout will be deleted from the favourites.")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        dialog.show();
                        database.collection("users")
                                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                .collection("favourites")
                                .document(model.getId()).delete().addOnCompleteListener(task -> {
                                    dialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, Favourites.class);
                                        context.startActivity(intent);
                                        ((Activity)context).finish();
                                    } else {
                                        Toast.makeText(context, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }).setNegativeButton("No", (dialogInterface, i) -> {
                    }).setIcon(R.drawable.favourites).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return favouriteHelpers.size();
    }

    public static class FavouritesViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView time;
        TextView state;

        public FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.exerImage);
            name = itemView.findViewById(R.id.exerciseName);
            time = itemView.findViewById(R.id.timeExercise);
            state = itemView.findViewById(R.id.equipExerciseUsed);

        }
    }
}
