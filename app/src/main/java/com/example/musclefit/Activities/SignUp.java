package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");

        binding.alreadyAccount.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignIn.class);
            startActivity(i);
            finish();
        });

        binding.submit.setOnClickListener(view -> {
            String email, pass, name, phoneNumber, confirmPass;

            name = Objects.requireNonNull(binding.fullName.getEditText()).getText().toString().trim();
            email = Objects.requireNonNull(binding.emailAddress.getEditText()).getText().toString().trim();
            phoneNumber = Objects.requireNonNull(binding.phoneNumber.getEditText()).getText().toString().trim();
            pass = Objects.requireNonNull(binding.password.getEditText()).getText().toString().trim();
            confirmPass = Objects.requireNonNull(binding.confirmPassword.getEditText()).getText().toString().trim();

            if (name.isEmpty()) {
                binding.fullName.setError("This field cannot be empty.");
            } else if (email.isEmpty()) {
                binding.emailAddress.setError("This field cannot be empty.");
            } else if (phoneNumber.isEmpty()) {
                binding.phoneNumber.setError("This field cannot be empty.");
            } else if (pass.isEmpty() || pass.length() < 6) {
                binding.password.setError("Password Length Should Exceed 6 Characters!");
            } else if (confirmPass.isEmpty() || confirmPass.length() < 6 ) {
                binding.confirmPassword.setError("Password Length Should Exceed 6 Characters!");
            } else if (!confirmPass.equals(pass)) {
                binding.confirmPassword.setError("Password does not Match!");
            } else {
                final User user = new User(name, email, phoneNumber);
                dialog.show();
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                        database
                                .collection("users")
                                .document(uid)
                                .set(user)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        dialog.dismiss();
                                        Intent i = new Intent(getApplicationContext(), SetGoals.class);
                                        i.putExtra("name", name);
                                        i.putExtra("email", email);
                                        i.putExtra("phoneNumber", phoneNumber);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(SignUp.this, Objects.requireNonNull(task1.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}