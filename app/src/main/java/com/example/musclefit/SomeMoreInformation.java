package com.example.musclefit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.musclefit.databinding.ActivitySomeMoreInformationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SomeMoreInformation extends AppCompatActivity {

    ActivitySomeMoreInformationBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    private String gender;
    private String height, weight, age, BMI;
    float h, w;
    float heightinm, doub;
    float bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySomeMoreInformationBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");
        setContentView(binding.getRoot());

        binding.calcBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                height = Objects.requireNonNull(binding.height.getEditText()).getText().toString().trim();
                weight = Objects.requireNonNull(binding.weight.getEditText()).getText().toString().trim();
                age = Objects.requireNonNull(binding.age.getEditText()).getText().toString().trim();

                if (height.isEmpty()) {
                    dialog.dismiss();
                    binding.height.setError("This Field Cannot be Empty.");
                } else if (weight.isEmpty()) {
                    dialog.dismiss();
                    binding.weight.setError("This Field Cannot be Empty.");
                } else if (age.isEmpty()) {
                    dialog.dismiss();
                    binding.age.setError("This Field Cannot be Empty.");
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            binding.calcBMI.setVisibility(View.INVISIBLE);
                            binding.continueButton.setVisibility(View.VISIBLE);
                            binding.textView11.setVisibility(View.VISIBLE);
                            binding.toggleButton.setVisibility(View.VISIBLE);
                            h = Float.parseFloat(height);
                            w = Float.parseFloat(weight);
                            heightinm = h / 100f;
                            doub = heightinm * heightinm;
                            BMI = String.valueOf(w/doub);
                            bmi = Float.parseFloat(BMI);

                            binding.textView8.setVisibility(View.VISIBLE);
                            binding.textView9.setText(BMI);
                            binding.textView9.setVisibility(View.VISIBLE);

                            if (bmi < 16) {
                                binding.textView10.setText("Severe Thinness");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 16 && bmi < 17) {
                                binding.textView10.setText("Moderate Thinness");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 17 && bmi < 18.5) {
                                binding.textView10.setText("Mild Thinness");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 18.5 && bmi < 25) {
                                binding.textView10.setText("Normal");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 25 && bmi < 30) {
                                binding.textView10.setText("Overweight");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 30 && bmi < 35) {
                                binding.textView10.setText("Obese Class I");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 35 && bmi < 40) {
                                binding.textView10.setText("Obese Class II");
                                binding.textView10.setVisibility(View.VISIBLE);
                            } else if (bmi >= 40) {
                                binding.textView10.setText("Obese Class III");
                                binding.textView10.setVisibility(View.VISIBLE);
                            }

                        }
                    },1000);
                    binding.toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                        @Override
                        public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                            binding.male.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gender = "Male";
                                }
                            });
                            binding.female.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gender = "Female";
                                }
                            });
                            binding.notSpecify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gender = "Not Specify";
                                }
                            });
                        }
                    });
                }
                binding.continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.show();
                        final MoreInformation user = new MoreInformation(height, weight, age, gender, BMI);

                        database
                                .collection("users")
                                .document(auth.getCurrentUser().getUid())
                                .collection("moreInformation")
                                .add(user)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            startActivity(new Intent(getApplicationContext(), Main.class));
                                            finish();
                                        } else {
                                            Toast.makeText(SomeMoreInformation.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }
}