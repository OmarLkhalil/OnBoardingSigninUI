package com.mobilebreakero.signupinui.passwordreset;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.mobilebreakero.signupinui.R;

public class PasswordSuccessScreen extends Fragment {

    public PasswordSuccessScreen() {
        super(R.layout.successpassword
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button login = requireView().findViewById(R.id.login_button_passwordsuccess);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        NavOptions navOptions = new NavOptions.Builder()
                .setPopEnterAnim(R.anim.fade_in)
                .setEnterAnim(R.anim.slide_in)
                .setPopExitAnim(R.anim.slide_out)
                .setExitAnim(R.anim.fade_out)
                .build();

        login.setOnClickListener(v -> {
            navController.navigate(R.id.action_passwordSuccessScreen_to_loginScreen, null, navOptions);
        });
    }
}
