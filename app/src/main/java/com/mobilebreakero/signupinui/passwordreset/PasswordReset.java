package com.mobilebreakero.signupinui.passwordreset;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobilebreakero.signupinui.R;

import java.util.Random;

public class PasswordReset extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    public PasswordReset() {
        super(R.layout.resetpassword);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sendVerification = view.findViewById(R.id.send_SMS_resetpassword);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        sendVerification.setOnClickListener(v -> {
            if (validationForm()) {
                String email = getEmail();
                retrievePhoneNumberFromFirestore(email);

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopEnterAnim(R.anim.fade_in)
                        .setEnterAnim(R.anim.slide_in)
                        .setPopExitAnim(R.anim.slide_out)
                        .setExitAnim(R.anim.fade_out)
                        .build();

                navController.navigate(R.id.action_passwordReset_to_phoneVerified, null, navOptions);
            }
        });
    }

    private boolean validationForm() {
        EditText email = requireView().findViewById(R.id.email_reset_password);

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Email is required");
            return false;
        }

        return true;
    }

    private String getEmail() {
        EditText email = requireView().findViewById(R.id.email_reset_password);
        return email.getText().toString();
    }

    private void retrievePhoneNumberFromFirestore(String email) {
        CollectionReference usersCollection = db.collection("users");
        Query query = usersCollection.whereEqualTo("email", email).limit(1);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot != null && !snapshot.isEmpty()) {
                    DocumentSnapshot document = snapshot.getDocuments().get(0);
                    String phoneNumber = document.getString("phone");
                    sendSmsToPhoneNumber(phoneNumber);
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSmsToPhoneNumber(String phoneNumber) {
        Random random = new Random();

        int verificationCode = random.nextInt(900000) + 100000;
        Log.e("sent", String.valueOf(verificationCode));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        prefs.edit().putString("savedPhoneNumber", String.valueOf(verificationCode)).apply();

//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(phoneNumber, null, "Your password reset code: " + verificationCode, null, null);
    }
}