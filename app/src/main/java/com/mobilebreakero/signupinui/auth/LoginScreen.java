package com.mobilebreakero.signupinui.auth;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobilebreakero.signupinui.R;

public class LoginScreen extends Fragment {


    public LoginScreen() {
        super(R.layout.login_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button loginButton = view.findViewById(R.id.login_button_fragment);

        loginButton.setOnClickListener(v -> {
            if (validateForm()) {
                String email = getEmail();
                String password = getPassword();

                checkUserInFirestore(email, password);
            }
        });

        TextView forgetPassword = view.findViewById(R.id.password_forget);
        forgetPassword.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopEnterAnim(R.anim.fade_in)
                    .setEnterAnim(R.anim.slide_in)
                    .setPopExitAnim(R.anim.slide_out)
                    .setExitAnim(R.anim.fade_out)
                    .build();
            navController.navigate(R.id.action_loginScreen_to_passwordReset, null, navOptions);
        });
    }

    private void checkUserInFirestore(String email, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            signInWithEmailAndPassword(email, password);
                        } else {
                            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithEmailAndPassword(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {

                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        NavOptions navOptions = new NavOptions.Builder()
                                .setPopEnterAnim(R.anim.fade_in)
                                .setEnterAnim(R.anim.slide_in)
                                .setPopExitAnim(R.anim.slide_out)
                                .setExitAnim(R.anim.fade_out)
                                .build();

                        navController.navigate(R.id.action_loginScreen_to_homeScreen, null, navOptions);
                    } else {
                        Toast.makeText(requireContext(), "Failed to sign in", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm() {
        EditText regEmail = requireView().findViewById(R.id.username_login);
        EditText regPassword = requireView().findViewById(R.id.password_login);

        if (TextUtils.isEmpty(regEmail.getText().toString())) {
            regEmail.setError("Email is required");
            return false;
        }

        if (TextUtils.isEmpty(regPassword.getText().toString())) {
            regPassword.setError("Password is required");
            return false;
        }

        return true;
    }

    private String getEmail() {
        EditText regEmail = requireView().findViewById(R.id.username_login);
        return regEmail.getText().toString().trim();
    }

    private String getPassword() {
        EditText regPassword = requireView().findViewById(R.id.password_login);
        return regPassword.getText().toString().trim();
    }
}