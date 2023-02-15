package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.ActivityMyInformationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MyInformation extends AppCompatActivity {

    ActivityMyInformationBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyInformationBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());
        dialog.show();

        binding.materialToolbar2.setTitle("My Information");

        Objects.requireNonNull(binding.nameTextField.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.phoneNumberTextField.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.emailTextField.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.heightEdit.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.weightEdit.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.ageEdit.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.genderEdit.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.personalizeEdit.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.bmiInfo.getEditText()).setFocusable(false);
        Objects.requireNonNull(binding.weightCategory.getEditText()).setFocusable(false);

        database.collection("users").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).get().addOnSuccessListener(documentSnapshot -> {
            dialog.dismiss();
            User user = documentSnapshot.toObject(User.class);
            assert user != null;
            Objects.requireNonNull(binding.nameTextField.getEditText()).setText(user.getName());
            Objects.requireNonNull(binding.emailTextField.getEditText()).setText(user.getEmail());
            Objects.requireNonNull(binding.phoneNumberTextField.getEditText()).setText(user.getPhoneNumber());
            Objects.requireNonNull(binding.heightEdit.getEditText()).setText(user.getHeight());
            Objects.requireNonNull(binding.weightEdit.getEditText()).setText(user.getWeight());
            Objects.requireNonNull(binding.ageEdit.getEditText()).setText(user.getAge());
            Objects.requireNonNull(binding.genderEdit.getEditText()).setText(user.getGender());
            Objects.requireNonNull(binding.personalizeEdit.getEditText()).setText(user.getPersonalize());
            Objects.requireNonNull(binding.bmiInfo.getEditText()).setText(user.getBMI());
            Objects.requireNonNull(binding.weightCategory.getEditText()).setText(user.getWeightCategory());
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(MyInformation.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}