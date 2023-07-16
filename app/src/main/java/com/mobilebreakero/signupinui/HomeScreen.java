package com.mobilebreakero.signupinui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

public class HomeScreen extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button login_button = view.findViewById(R.id.login_button);
        Button reg_button = view.findViewById(R.id.signup_button);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        NavOptions navOptions = new NavOptions.Builder()
                .setPopEnterAnim(R.anim.fade_in)
                .setEnterAnim(R.anim.slide_in)
                .setPopExitAnim(R.anim.slide_out)
                .setExitAnim(R.anim.fade_out)
                .build();


        login_button.setOnClickListener(v -> navController.navigate(R.id.loginScreen, null, navOptions));
        reg_button.setOnClickListener(v -> navController.navigate(R.id.registerScreen, null, navOptions));
    }
}
