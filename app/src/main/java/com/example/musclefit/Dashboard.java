package com.example.musclefit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.musclefit.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());

        if (fUser.isEmailVerified()){
            binding.emailVerification.setText("Email has been Verified Successfully.");
        } else {
            binding.emailVerification.setText("Please Verify your Email.");
        }

        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                auth.signOut();
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), StartPanel.class));
                finish();
            }
        });
        Toast.makeText(this, "Welcome to the Dashboard.", Toast.LENGTH_SHORT).show();
    }
}