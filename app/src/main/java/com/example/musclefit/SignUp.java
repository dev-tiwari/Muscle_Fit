package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.musclefit.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        binding.alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignIn.class);
                startActivity(i);
                finish();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass, name, confirmPass;

                name = binding.fullName.getText().toString().trim();
                email = binding.emailAddress.getText().toString().trim();
                pass = binding.password.getText().toString().trim();
                confirmPass = binding.confirmPassword.getText().toString().trim();

                if (name.isEmpty()) {
                    binding.fullName.setError("This field cannot be empty.");
                } else if (email.isEmpty()) {
                    binding.emailAddress.setError("This field cannot be empty.");
                } else if (pass.isEmpty() || pass.length() < 6) {
                    binding.password.setError("Password Length Should Exceed 6 Characters!");
                }else if (confirmPass.isEmpty() || confirmPass.length() < 6 ) {
                    binding.confirmPassword.setError("Password Length Should Exceed 6 Characters!");
                }else if (!confirmPass.equals(pass)) {
                    binding.confirmPassword.setError("Password does not Match!");
                } else {
                    final User user = new User(name, email, pass);
                    dialog.show();
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                database
                                        .collection("users")
                                        .document(uid)
                                        .set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    dialog.dismiss();
                                                    binding.fullName.setText("");
                                                    binding.emailAddress.setText("");
                                                    binding.password.setText("");
                                                    binding.confirmPassword.setText("");
                                                    Intent i = new Intent(getApplicationContext(), Dashboard.class);
                                                    startActivity(i);
                                                    finish();
                                                } else {
                                                    dialog.dismiss();
                                                    Toast.makeText(SignUp.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}