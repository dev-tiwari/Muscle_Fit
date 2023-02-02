package com.example.musclefit.Adapters;

import android.app.Activity;
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
import com.example.musclefit.User_Helper_Classes.ExerciseModel;
import com.example.musclefit.User_Helper_Classes.FavouriteHelper;
import com.example.musclefit.Activities.Favourites;
import com.example.musclefit.R;
import com.example.musclefit.Activities.WorkoutInformationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

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
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ExerciseModel mode = documentSnapshot.toObject(ExerciseModel.class);
                        holder.name.setText(mode.getExerciseName());
                        if (mode.getState().contains("K1")) {
                            holder.state.setText(mode.getState() + " Beginner");
                        } else if (mode.getState().contains("K2")) {
                            holder.state.setText(mode.getState() + " Intermediate");
                        } else if (mode.getState().contains("K3")) {
                            holder.state.setText(mode.getState() + " Advanced");
                        }
                        holder.time.setText(mode.getTimeTaken());
                        Glide.with(context).load(mode.getExerciseImage()).into(holder.image);
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkoutInformationActivity.class);
                intent.putExtra("exId", model.getExId());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Favourites")
                        .setMessage("Delete from favourites. The workout will be deleted from the favourites.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.show();
                                database.collection("users")
                                        .document(auth.getCurrentUser().getUid())
                                        .collection("favourites")
                                        .document(model.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                dialog.dismiss();
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(context, Favourites.class);
                                                    context.startActivity(intent);
                                                    ((Activity)context).finish();
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
        return favouriteHelpers.size();
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder {

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
