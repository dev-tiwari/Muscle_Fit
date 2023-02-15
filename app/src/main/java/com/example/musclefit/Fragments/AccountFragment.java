package com.example.musclefit.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.musclefit.Activities.EditProfile;
import com.example.musclefit.Activities.Favourites;
import com.example.musclefit.Activities.MyInformation;
import com.example.musclefit.Activities.Settings;
import com.example.musclefit.Activities.SignIn;
import com.example.musclefit.Activities.StartPanel;
import com.example.musclefit.R;
import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AccountFragment extends Fragment {
    public AccountFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     }

    FragmentAccountBinding binding;
    ProgressDialog dialog;
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Just a Minute...");

        dialog.show();

        boolean check = Objects.requireNonNull(auth.getCurrentUser()).isEmailVerified();
        if (check) {
            binding.cardView7.setVisibility(View.INVISIBLE);
        }

        database.collection("users")
                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
                    dialog.dismiss();
                    User user = documentSnapshot.toObject(User.class);
                    assert user != null;
                    binding.profileName.setText(user.getName());
                    binding.profileGmail.setText(user.getEmail());
                    if(check){
                        binding.emailVerifyBack.setCardBackgroundColor(Color.parseColor("#00FF00"));
                        binding.verifyEmail.setText(R.string.verified);
                    } else {
                        binding.emailVerifyBack.setCardBackgroundColor(Color.parseColor("#FF0000"));
                        binding.verifyEmail.setText(R.string.not_verified);
                    }

                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });

        binding.editProfile.setOnClickListener(view -> startActivity(new Intent(getContext(), EditProfile.class)));

        binding.settingsButton.setOnClickListener(view -> startActivity(new Intent(getContext(), Settings.class)));

        binding.logoutAccount.setOnClickListener(view -> {
            dialog.show();
            new Handler().postDelayed(() -> {
                dialog.dismiss();
                auth.signOut();
                startActivity(new Intent(getContext(), StartPanel.class));
                requireActivity().finish();
            }, 1000);
        });

        binding.myInfo.setOnClickListener(view -> startActivity(new Intent(getContext(), MyInformation.class)));

//        binding.reminders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), Reminders.class));
//            }
//        });

        binding.favourites.setOnClickListener(view -> startActivity(new Intent(getContext(), Favourites.class)));

        binding.verifyMail.setOnClickListener(view -> {
            dialog.show();
            Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnSuccessListener(unused -> {
                dialog.dismiss();
                Toast.makeText(getContext(), "Verification Mail has been Sent to your Registered Email Address. Please Verify.", Toast.LENGTH_SHORT).show();
                auth.getCurrentUser().reload();
                auth.signOut();
                startActivity(new Intent(getContext(), SignIn.class));
                requireActivity().finish();
            }).addOnFailureListener(e -> {
                dialog.dismiss();
                Toast.makeText(getContext(), "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
        });


        return binding.getRoot();
    }
}