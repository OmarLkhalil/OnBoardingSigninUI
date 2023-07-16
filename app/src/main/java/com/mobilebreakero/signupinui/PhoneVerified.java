package com.mobilebreakero.signupinui;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.fraggjkee.smsconfirmationview.SmsConfirmationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class PhoneVerified extends Fragment {

    private SmsConfirmationView smsCodeView;
    private MaterialButton confirmButton;
    private MaterialTextView returnLoginButton;

    public PhoneVerified() {
        super(R.layout.verified);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        smsCodeView = view.findViewById(R.id.sms_code_view);
        confirmButton = view.findViewById(R.id.confirm_button);
        returnLoginButton = view.findViewById(R.id.return_login_resetpassword);

        String verificationCode = getVerificationCode();

        showVerificationCodeSnackbar(verificationCode);
        smsCodeView.setEnteredCode(verificationCode);

        confirmButton.setOnClickListener(v -> {
            String enteredCode = smsCodeView.getEnteredCode();
            if (validateCode(enteredCode)) {

                if (isCodeVerified(enteredCode)) {

                    Toast.makeText(requireContext(), "Verification failed", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(requireContext(), "Verification successful", Toast.LENGTH_SHORT).show();

                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopEnterAnim(R.anim.fade_in)
                            .setEnterAnim(R.anim.slide_in)
                            .setPopExitAnim(R.anim.slide_out)
                            .setExitAnim(R.anim.fade_out)
                            .build();

                    navController.navigate(R.id.action_phoneVerified_to_setNewPasswordScreen, null, navOptions);
                }
            } else {
                Toast.makeText(requireContext(), "Invalid code", Toast.LENGTH_SHORT).show();
            }
        });

        returnLoginButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Return to login", Toast.LENGTH_SHORT).show();
        });
    }

    private String getVerificationCode() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return prefs.getString("savedPhoneNumber", "");
    }

    private boolean validateCode(String code) {
        return code.length() == 6;
    }

    private boolean isCodeVerified(String code) {
        String verificationCode = getVerificationCode();
        Log.e("Verification", verificationCode);
        return code.equals(verificationCode);
    }

    private void showVerificationCodeSnackbar(String code) {
        View rootView = requireView();

        Snackbar snackbar = Snackbar.make(rootView, "Verification Code: " + code, Snackbar.LENGTH_LONG);
        snackbar.setAction("Copy", v -> {
            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Verification Code", code);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(requireContext(), "Code copied to clipboard", Toast.LENGTH_SHORT).show();
        });

        snackbar.show();
    }
}
