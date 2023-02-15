package com.example.musclefit.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.musclefit.Adapters.FavouritesAdapter;
import com.example.musclefit.User_Helper_Classes.FavouriteHelper;
import com.example.musclefit.databinding.ActivityFavouritesBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class Favourites extends AppCompatActivity {

    ActivityFavouritesBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouritesBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("loading...");
        dialog.show();
        setContentView(binding.getRoot());

        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Long Press to delete a Favourite.", Snackbar.LENGTH_LONG).setAction("OK", view -> {
        });snackbar.show();

        ArrayList<FavouriteHelper> favouriteHelpers = new ArrayList<>();
        FavouritesAdapter adapter = new FavouritesAdapter(this, favouriteHelpers);

        database.collection("users")
                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .collection("favourites")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    int i = 0;
                    dialog.dismiss();
                    favouriteHelpers.clear();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        FavouriteHelper model = snapshot.toObject(FavouriteHelper.class);
                        assert model != null;
                        model.setId(snapshot.getId());
                        favouriteHelpers.add(model);
                        i += 1;
                    }adapter.notifyDataSetChanged();
                    if (i == 0) {
                        binding.dataNot.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(Favourites.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });

        binding.cont.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.cont.setAdapter(adapter);
    }
}