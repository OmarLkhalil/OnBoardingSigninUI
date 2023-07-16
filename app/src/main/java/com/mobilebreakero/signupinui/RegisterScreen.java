package com.mobilebreakero.signupinui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterScreen extends Fragment {

    public RegisterScreen() {
        super(R.layout.register_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button regButton = view.findViewById(R.id.signup_button_fragment);
        regButton.setOnClickListener(v -> {
            if (validateForm()) {
                String email = getEmail();
                String phone = getPhone();
                String name = getName();

                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name);
                userData.put("email", email);
                userData.put("phone", phone);

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                FirebaseAuth auth = FirebaseAuth.getInstance();

                CollectionReference usersRef = db.collection("users");

                usersRef.add(userData)
                        .addOnSuccessListener(documentReference -> {
                            String password = getPassword();

                            auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(requireActivity(), task -> {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = auth.getCurrentUser();
                                            if (user != null) {
                                                user.sendEmailVerification().addOnCompleteListener(emailTask -> {
                                                    if (emailTask.isSuccessful()) {
                                                        auth.signOut();
                                                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                                                        NavOptions navOptions = new NavOptions.Builder()
                                                                .setPopEnterAnim(R.anim.fade_in)
                                                                .setEnterAnim(R.anim.slide_in)
                                                                .setPopExitAnim(R.anim.slide_out)
                                                                .setExitAnim(R.anim.fade_out)
                                                                .build();
                                                        navController.navigate(R.id.loginScreen, null, navOptions);
                                                    }
                                                });
                                            }
                                        } else {
                                            Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                            Log.e("TAG", "Registration failed: ", task.getException());
                                        }
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "Registration failed: ", e);
                        });
            }
        });
    }

    private boolean validateForm() {
        EditText regName = requireView().findViewById(R.id.name_register);
        EditText regEmail = requireView().findViewById(R.id.email_register);
        EditText regPhone = requireView().findViewById(R.id.phone_register);
        EditText regPassword = requireView().findViewById(R.id.password);

        if (TextUtils.isEmpty(regName.getText().toString())) {
            regName.setError("Name is required");
            return false;
        }

        if (TextUtils.isEmpty(regEmail.getText().toString())) {
            regEmail.setError("Email is required");
            return false;
        }

        if (TextUtils.isEmpty(regPhone.getText().toString())) {
            regPhone.setError("Phone is required");
            return false;
        }

        if (TextUtils.isEmpty(regPassword.getText().toString())) {
            regPassword.setError("Password is required");
            return false;
        }

        return true;
    }

    private String getEmail() {
        EditText regEmail = requireView().findViewById(R.id.email_register);
        return regEmail.getText().toString().trim();
    }

    private String getName() {
        EditText regName = requireView().findViewById(R.id.name_register);
        return regName.getText().toString().trim();
    }

    private String getPassword() {
        EditText regName = requireView().findViewById(R.id.password);
        return regName.getText().toString().trim();
    }

    private String getPhone() {
        EditText regPhone = requireView().findViewById(R.id.phone_register);
        return regPhone.getText().toString().trim();
    }
}