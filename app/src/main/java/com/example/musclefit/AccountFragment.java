package com.example.musclefit;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musclefit.databinding.FragmentAccountBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
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

        database.collection("users")
                .document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        dialog.dismiss();
                        User user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        binding.profileName.setText(user.getName());
                        binding.profileGmail.setText(user.getEmail());
                        if(check){
                            binding.emailVerifyBack.setCardBackgroundColor(Color.parseColor("#00FF00"));
                            binding.verifyEmail.setText("Verified");
                        } else {
                            binding.emailVerifyBack.setCardBackgroundColor(Color.parseColor("#FF0000"));
                            binding.verifyEmail.setText("Not Verified");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });

        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Settings.class));
            }
        });

        binding.logoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        auth.signOut();
                        startActivity(new Intent(getContext(), StartPanel.class));
                        requireActivity().finish();
                    }
                }, 1000);
            }
        });


//        binding.navigationRail.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                assert getFragmentManager() != null;
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                switch (item.getItemId()) {
//                    case R.id.profile:
//                        dialog.show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                                transaction.replace(binding.navigationContent.getId(), new ProfileFragment());
//                                transaction.commit();
//                            }
//                        }, 1000);
//                        break;
//                    case R.id.settings:
//                        dialog.show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                                transaction.replace(binding.navigationContent.getId(), new SettingsFragment());
//                                transaction.commit();
//                            }
//                        }, 1000);
//                        break;
//                    case R.id.logout:
//                        dialog.show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                                auth.signOut();
//                                startActivity(new Intent(getContext(), StartPanel.class));
//                                requireActivity().finish();
//                            }
//                        },1000);
//                        break;
//                }
//                return true;
//            }
//        });
//
//        binding.navigationRail.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                switch (item.getItemId()) {
//                    case R.id.profile:
//                        dialog.show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                                transaction.replace(binding.navigationContent.getId(), new ProfileFragment());
//                                transaction.commit();
//                            }
//                        }, 1000);
//                        break;
//                    case R.id.settings:
//                        dialog.show();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                                transaction.replace(binding.navigationContent.getId(), new SettingsFragment());
//                                transaction.commit();
//                            }
//                        }, 1000);
//                        break;
//                }
//            }
//        });

        return binding.getRoot();
    }
}