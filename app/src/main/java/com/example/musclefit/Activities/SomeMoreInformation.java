package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.ActivitySomeMoreInformationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SomeMoreInformation extends AppCompatActivity {

    ActivitySomeMoreInformationBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    String name, email, phoneNumber, personalize;
    private String gender;
    private String height, weight, age, BMI, h1, w1;
    float h, w;
    float heightinm, doub;
    float bmi;
    private String weightCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySomeMoreInformationBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        personalize = getIntent().getStringExtra("personalize");

        binding.calcBMI.setOnClickListener(view -> {
            dialog.show();
            String[] height1 = Objects.requireNonNull(binding.height.getEditText()).getText().toString().trim().split(" ");
            height = height1[0];
            String[] weight1 = Objects.requireNonNull(binding.weight.getEditText()).getText().toString().trim().split(" ");
            weight = weight1[0];
            String[] age1 = Objects.requireNonNull(binding.age.getEditText()).getText().toString().trim().split(" ");
            age = age1[0] + " yrs";
            h1 = height + " cm";
            w1 = weight + " kg";

            if (height.isEmpty()) {
                dialog.dismiss();
                binding.height.setError("This Field Cannot be Empty.");
            } else if (weight.isEmpty()) {
                dialog.dismiss();
                binding.weight.setError("This Field Cannot be Empty.");
            } else if (Objects.requireNonNull(binding.age.getEditText()).getText().toString().trim().isEmpty()) {
                dialog.dismiss();
                binding.age.setError("This Field Cannot be Empty.");
            } else {
                new Handler().postDelayed(() -> {
                    dialog.dismiss();
                    binding.calcBMI.setVisibility(View.INVISIBLE);
                    binding.continueButton.setVisibility(View.VISIBLE);
                    binding.textView11.setVisibility(View.VISIBLE);
                    binding.toggleButton.setVisibility(View.VISIBLE);
                    h = Float.parseFloat(height);
                    w = Float.parseFloat(weight);
                    heightinm = h / 100f;
                    doub = heightinm * heightinm;
                    float bm = (w / doub);
                    BMI = String.valueOf(bm);
                    bmi = Float.parseFloat(BMI);

                    binding.textView8.setVisibility(View.VISIBLE);
                    binding.textView9.setText(BMI);
                    binding.textView9.setVisibility(View.VISIBLE);

                    if (bmi < 16) {
                        weightCategory = "Severe Thinness";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 16 && bmi < 17) {
                        weightCategory = "Moderate Thinness";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 17 && bmi < 18.5) {
                        weightCategory = "Mild Thinness";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 18.5 && bmi < 25) {
                        weightCategory = "Normal";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 25 && bmi < 30) {
                        weightCategory = "Overweight";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 30 && bmi < 35) {
                        weightCategory = "Obese Class I";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 35 && bmi < 40) {
                        weightCategory = "Obese Class II";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    } else if (bmi >= 40) {
                        weightCategory = "Obese Class III";
                        binding.textView10.setText(weightCategory);
                        binding.textView10.setVisibility(View.VISIBLE);
                    }

                },1000);

                try {
                    binding.toggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                        binding.male.setOnClickListener(view1 -> gender = "Male");
                        binding.female.setOnClickListener(view1 -> gender = "Female");
                        binding.notSpecify.setOnClickListener(view1 -> gender = "Not Specify");
                    });
                } catch (Exception e) {
                    Toast.makeText(SomeMoreInformation.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    gender = "Not Specify";
                }
            }
            binding.continueButton.setOnClickListener(view12 -> {
                dialog.show();
                final User user = new User(name, email, phoneNumber, personalize, h1, w1, age, gender, BMI, weightCategory);
                database
                        .collection("users")
                        .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                        .set(user)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), Main.class));
                                finish();
                            } else {
                                Toast.makeText(SomeMoreInformation.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            });
        });
    }
}