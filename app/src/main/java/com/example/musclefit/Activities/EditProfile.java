package com.example.musclefit.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musclefit.User_Helper_Classes.User;
import com.example.musclefit.databinding.ActivityEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
    FirebaseFirestore database;
    FirebaseDatabase data;
    FirebaseStorage storage;

    String name, email, phoneNumber, personalize, height, weight, age, gender, BMI, weightCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
            auth = FirebaseAuth.getInstance();
            dialog = new ProgressDialog(this);
            dialog.setMessage("Just a Minute...");
            database = FirebaseFirestore.getInstance();
            data = FirebaseDatabase.getInstance();
            storage = FirebaseStorage.getInstance();

            setContentView(binding.getRoot());

            dialog.show();
            setFocus(false);

            database.collection("users")
                    .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        dialog.dismiss();
                        binding.materialToolbar2.setTitle("Edit Profile");
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
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(EditProfile.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Main.class));
                        finish();
                    });

            binding.edit.setOnClickListener(view -> {
                dialog.show();
                new Handler().postDelayed(() -> {
                    binding.materialToolbar2.setTitle("Ready to Edit");
                    dialog.dismiss();
                    binding.edit.setVisibility(View.INVISIBLE);
                    setFocus(true);
                    Objects.requireNonNull(binding.genderEdit).setVisibility(View.INVISIBLE);
                    Objects.requireNonNull(binding.personalizeEdit).setVisibility(View.INVISIBLE);
                    binding.save.setVisibility(View.VISIBLE);
                    binding.textView19.setVisibility(View.VISIBLE);
                    binding.toggleButton.setVisibility(View.VISIBLE);
                    binding.textView20.setVisibility(View.VISIBLE);
                    binding.buildMuscles2.setVisibility(View.VISIBLE);
                    binding.moreActive2.setVisibility(View.VISIBLE);
                    binding.loseWeight2.setVisibility(View.VISIBLE);
                }, 1000);
            });

            try {
                binding.toggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                    binding.male.setOnClickListener(view -> gender = "Male");
                    binding.female.setOnClickListener(view -> gender = "Female");
                    binding.notSpecify.setOnClickListener(view -> gender = "Not Specify");
                });
            } catch (Exception e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                gender = "Not Specify";
            }

            try {
                binding.buildMuscles2.setOnClickListener(view -> {
                    personalize = "Build Muscles";
                    binding.buildMuscles2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                    binding.loseWeight2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    binding.moreActive2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                });
                binding.loseWeight2.setOnClickListener(view -> {
                    personalize = "To Lose Weight";
                    binding.buildMuscles2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    binding.loseWeight2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                    binding.moreActive2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                });
                binding.moreActive2.setOnClickListener(view -> {
                    personalize = "To be More Active";
                    binding.buildMuscles2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    binding.loseWeight2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                    binding.moreActive2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                });
            } catch (Exception e ){
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                personalize = "To Lose Weight";
            }

            binding.save.setOnClickListener(view -> {
                dialog.show();
                name = Objects.requireNonNull(binding.nameTextField.getEditText()).getText().toString().trim();
                email = Objects.requireNonNull(binding.emailTextField.getEditText()).getText().toString().trim();
                phoneNumber = Objects.requireNonNull(binding.phoneNumberTextField.getEditText()).getText().toString().trim();
                String[] dem = Objects.requireNonNull(binding.heightEdit.getEditText()).getText().toString().trim().split(" ");
                height = dem[0] + " cm";
                String[] dem1 = Objects.requireNonNull(binding.weightEdit.getEditText()).getText().toString().trim().split(" ");
                weight = dem1[0] + " kg";
                String[] dem2 = Objects.requireNonNull(binding.ageEdit.getEditText()).getText().toString().trim().split(" ");
                age = dem2[0] + " yrs";

                float h, w, heightinm, doub, bmi;
                h = Float.parseFloat(dem[0]);
                w = Float.parseFloat(dem1[0]);
                heightinm = h / 100f;
                doub = heightinm * heightinm;
                BMI = String.valueOf(w / doub);
                bmi = Float.parseFloat(BMI);

                if (bmi < 16) {
                    weightCategory = "Severe Thinness";
                } else if (bmi >= 16 && bmi < 17) {
                    weightCategory = "Moderate Thinness";
                } else if (bmi >= 17 && bmi < 18.5) {
                    weightCategory = "Mild Thinness";
                } else if (bmi >= 18.5 && bmi < 25) {
                    weightCategory = "Normal";
                } else if (bmi >= 25 && bmi < 30) {
                    weightCategory = "Overweight";
                } else if (bmi >= 30 && bmi < 35) {
                    weightCategory = "Obese Class I";
                } else if (bmi >= 35 && bmi < 40) {
                    weightCategory = "Obese Class II";
                } else if (bmi >= 40) {
                    weightCategory = "Obese Class III";
                }

                if (name.isEmpty()) {
                    dialog.dismiss();
                    binding.nameTextField.getEditText().setError("This field cannot be empty.");
                } else if (email.isEmpty()) {
                    dialog.dismiss();
                    binding.emailTextField.getEditText().setError("This field cannot be empty.");
                } else if (phoneNumber.length() != 10) {
                    dialog.dismiss();
                    binding.phoneNumberTextField.getEditText().setError("Phone Number should be of 10 digits.");
                } else if (height.isEmpty()) {
                    dialog.dismiss();
                    binding.heightEdit.getEditText().setError("This field cannot be empty.");
                } else if (weight.isEmpty()) {
                    dialog.dismiss();
                    binding.weightEdit.getEditText().setError("This field cannot be empty.");
                } else if (binding.ageEdit.getEditText().getText().toString().isEmpty()) {
                    dialog.dismiss();
                    binding.ageEdit.getEditText().setError("This field cannot be empty.");
                } else {
                    User user = new User(name, email, phoneNumber, personalize, height, weight, age, gender, BMI, weightCategory);
                    database.collection("users")
                            .document(auth.getCurrentUser().getUid())
                            .set(user)
                            .addOnCompleteListener(task -> {
                                dialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(EditProfile.this, "Profile has been Update Successfully.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), EditProfile.class));
                                    finish();
                                } else {
                                    Toast.makeText(EditProfile.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
        }

    public void setFocus (boolean focus){
            if (focus) {
                Objects.requireNonNull(binding.nameTextField.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.phoneNumberTextField.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.emailTextField.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.heightEdit.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.weightEdit.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.ageEdit.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.genderEdit.getEditText()).setFocusableInTouchMode(true);
                Objects.requireNonNull(binding.personalizeEdit.getEditText()).setFocusableInTouchMode(true);
            } else {
                Objects.requireNonNull(binding.nameTextField.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.phoneNumberTextField.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.emailTextField.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.heightEdit.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.weightEdit.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.ageEdit.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.genderEdit.getEditText()).setFocusable(false);
                Objects.requireNonNull(binding.personalizeEdit.getEditText()).setFocusable(false);
            }
    }
}