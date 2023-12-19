package com.mobilebreakero.signupinui.passwordreset;

import android.os.Bundle;
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
import com.mobilebreakero.signupinui.BuildConfig;
import com.mobilebreakero.signupinui.R;

import java.io.IOException;
import java.util.Random;

import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PasswordReset extends Fragment {

    private static final String TWILIO_ACCOUNT_SID = BuildConfig.TWILIO_ACCOUNT_SID;
    private static final String TWILIO_AUTH_TOKEN = BuildConfig.TWILIO_AUTH_TOKEN;
    private static final String TWILIO_PHONE_NUMBER = BuildConfig.TWILIO_PHONE_NUMBER;

    private FirebaseFirestore db;

    public PasswordReset() {
        super(R.layout.resetpassword);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button sendVerification = view.findViewById(R.id.send_SMS_resetpassword);

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
                    String message = "Your verification code is: " + new Random().nextInt(999999);
                    sendSmsToPhoneNumber(phoneNumber, message);
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void sendSmsToPhoneNumber(String phoneNumber, String messageBody) {

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("To", phoneNumber)
                .add("From", TWILIO_PHONE_NUMBER)
                .add("Body", messageBody)
                .build();

        Request request = new Request.Builder()
                .url("https://api.twilio.com/2010-04-01/Accounts/" + TWILIO_ACCOUNT_SID + "/Messages.json")
                .post(requestBody)
                .addHeader("Authorization", Credentials.basic(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                Log.d("TwilioSMS", "Message SID: " + response.body().string());

            }

            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Log.e("TwilioSMS", "Error sending SMS: " + e.getMessage());

            }

        });
    }
}

