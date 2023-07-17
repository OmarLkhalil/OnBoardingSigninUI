package com.mobilebreakero.signupinui.passwordreset;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobilebreakero.signupinui.R;

public class SetNewPasswordScreen extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public SetNewPasswordScreen() {
        super(R.layout.new_password);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Button setPasswordButton = view.findViewById(R.id.set_new_password);
        EditText newPasswordEditText = view.findViewById(R.id.password_new);
        EditText confirmPasswordEditText = view.findViewById(R.id.password_repeat);

        TextView returnToLogin = view.findViewById(R.id.return_login_resetpassword);

//        returnToLogin.setOnClickListener(v -> {
//            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//            NavOptions navOptions = new NavOptions.Builder()
//                    .setPopEnterAnim(R.anim.fade_in)
//                    .setEnterAnim(R.anim.slide_in)
//                    .setPopExitAnim(R.anim.slide_out)
//                    .setExitAnim(R.anim.fade_out)
//                    .build();
//            navController.navigate(R.id.action_setNewPasswordScreen_to_loginScreen, null, navOptions);
//        });

        setPasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString();
            String repeatpasswordtext = confirmPasswordEditText.getText().toString();

            if(newPassword.equals(repeatpasswordtext)) {
                if (TextUtils.isEmpty(newPassword) && TextUtils.isEmpty(repeatpasswordtext)) {
                    Toast.makeText(requireContext(), "Please enter a new password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "New password set successfully", Toast.LENGTH_SHORT).show();

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopEnterAnim(R.anim.fade_in)
                            .setEnterAnim(R.anim.slide_in)
                            .setPopExitAnim(R.anim.slide_out)
                            .setExitAnim(R.anim.fade_out)
                            .build();

                    navController.navigate(R.id.action_setNewPasswordScreen_to_passwordSuccessScreen, null, navOptions);

//                    FirebaseUser currentUser = auth.getCurrentUser();
//                    if (currentUser != null) {
//                        currentUser.updatePassword(newPassword)
//                                .addOnCompleteListener(task -> {
//                                    if (task.isSuccessful()) {
//
//                                    } else {
//                                        Toast.makeText(requireContext(), "Failed to set the new password", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    }
                }
            }
            if(!newPassword.equals(repeatpasswordtext)){
                Toast.makeText(requireContext(), "Passwords are not match", Toast.LENGTH_SHORT).show();
            }
        });
    }
}