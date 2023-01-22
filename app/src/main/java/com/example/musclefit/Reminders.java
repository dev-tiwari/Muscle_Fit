package com.example.musclefit;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.musclefit.databinding.ActivityRemindersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Reminders extends AppCompatActivity {

    ActivityRemindersBinding binding;
    ProgressDialog dialog;
    FirebaseAuth auth;
    FirebaseFirestore database;
    String formattedTime1;
    String selectedDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRemindersBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Just a Minute...");

        dialog.show();

        binding.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialTimePicker picker =
                        new MaterialTimePicker.Builder()
                                .setTimeFormat(TimeFormat.CLOCK_12H)
                                .setHour(12)
                                .setMinute(00)
                                .setTitleText("Select Reminder time")
                                .build();
                new MaterialTimePicker.Builder().setInputMode(INPUT_MODE_CLOCK);
                picker.show(getSupportFragmentManager(), "tag");

                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        formattedTime1 = formatTime(picker.getHour(), picker.getMinute());
                        binding.textView16.setVisibility(View.VISIBLE);
                        binding.textView16.setText(formattedTime1);
                    }
                });
            }
        });

        binding.monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll("Monday", "");
                } else {
                    selectedDays = "Monday";
                }
            }
        });

        binding.tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll(" Tuesday", "");
                } else {
                    selectedDays = selectedDays + " Tuesday";
                }
            }
        });

        binding.wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll(" Wednesday", "");
                } else {
                    selectedDays = selectedDays + " Wednesday";
                }
            }
        });

        binding.thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll(" Thursday", "");
                } else {
                    selectedDays = selectedDays + " Thursday";
                }
            }
        });

        binding.friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll(" Friday", "");
                } else {
                    selectedDays = selectedDays + " Friday";
                }
            }
        });

        binding.saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll(" Saturday", "");
                } else {
                    selectedDays = selectedDays + " Saturday";
                }
            }
        });

        binding.sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    selectedDays = selectedDays.replaceAll(" Sunday", "");
                } else {
                    selectedDays = selectedDays + " Sunday";
                }
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                if (formattedTime1 == null) {
                    dialog.dismiss();
                    Toast.makeText(Reminders.this, "Please select the Time.", Toast.LENGTH_SHORT).show();
                } else if (selectedDays == null || selectedDays.equals("")){
                    dialog.dismiss();
                    Toast.makeText(Reminders.this, "Please select the days for the Reminder.", Toast.LENGTH_SHORT).show();
                } else {
                    ReminderAddition addition = new ReminderAddition(formattedTime1, selectedDays);
                    database.collection("users")
                            .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                            .collection("reminder")
                            .add(addition).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    dialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Reminders.this, "Successfully Added.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Reminders.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        database.collection("users")
                        .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                                .collection("reminder")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        dialog.dismiss();
                        assert value != null;
                        if (!value.isEmpty()) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(binding.con.getId(), new ReminderShowingFragment());
                            transaction.commit();
                            allInvisible();
                        }
                    }
                });

        setContentView(binding.getRoot());
    }

    private void allInvisible() {
        binding.textView15.setVisibility(View.INVISIBLE);
        binding.select.setVisibility(View.INVISIBLE);
        binding.textView16.setVisibility(View.INVISIBLE);
        binding.textView17.setVisibility(View.INVISIBLE);
        binding.monday.setVisibility(View.INVISIBLE);
        binding.tuesday.setVisibility(View.INVISIBLE);
        binding.wednesday.setVisibility(View.INVISIBLE);
        binding.thursday.setVisibility(View.INVISIBLE);
        binding.friday.setVisibility(View.INVISIBLE);
        binding.saturday.setVisibility(View.INVISIBLE);
        binding.sunday.setVisibility(View.INVISIBLE);
        binding.saveBtn.setVisibility(View.INVISIBLE);
    }

    public String formatTime(int hour, int minute){
        String formattedTime = null;
        String ap = null;

        if (hour > 12) {
            int hor = hour - 12;
            ap = "pm";
            if (minute > 10) {
                formattedTime = hor + " : " + minute + " " + ap;
            } else {
                formattedTime = hor + " : 0" + minute + " " + ap;
            }
        } else if (hour == 0) {
            ap = "am";
            if (minute > 10) {
                formattedTime = "12" + " : " + minute + " " + ap;
            } else {
                formattedTime = "12" + " : 0" + minute + " " + ap;
            }
        } else if (hour == 12) {
            ap = "pm";
            if (minute > 10) {
                formattedTime = hour + " : " + minute + " " + ap;
            } else {
                formattedTime = hour + " : 0" + minute + " " + ap;
            }
        } else if (hour < 12) {
            ap = "am";
            if (minute > 10) {
                formattedTime = hour + " : " + minute + " " + ap;
            } else {
                formattedTime = hour + " : 0" + minute + " " + ap;
            }
        }
        return formattedTime;
    }
}