package com.example.musclefit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.musclefit.Adapters.FavouritesAdapter;
import com.example.musclefit.User_Helper_Classes.FavouriteHelper;
import com.example.musclefit.databinding.ActivityFavouritesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    ActivityFavouritesBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

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

        ArrayList<FavouriteHelper> favouriteHelpers = new ArrayList<>();
        FavouritesAdapter adapter = new FavouritesAdapter(this, favouriteHelpers);

        database.collection("users")
                .document(auth.getCurrentUser().getUid())
                .collection("favourites")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int i = 0;
                        dialog.dismiss();
                        favouriteHelpers.clear();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            FavouriteHelper model = snapshot.toObject(FavouriteHelper.class);
                            model.setId(snapshot.getId());
                            favouriteHelpers.add(model);
                            i += 1;
                        }adapter.notifyDataSetChanged();
                        if (i == 0) {
                            binding.dataNot.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(Favourites.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.cont.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.cont.setAdapter(adapter);
    }
}