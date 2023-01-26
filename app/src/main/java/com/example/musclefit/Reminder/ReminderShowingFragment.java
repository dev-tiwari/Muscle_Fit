package com.example.musclefit.Reminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musclefit.databinding.FragmentReminderShowingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ReminderShowingFragment extends Fragment {
    public ReminderShowingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentReminderShowingBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReminderShowingBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

//        database.collection("users")
//                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
//                .collection("reminder")


        return binding.getRoot();
    }
}