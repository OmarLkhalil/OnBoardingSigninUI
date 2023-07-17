package com.mobilebreakero.signupinui.pager.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.mobilebreakero.signupinui.R;

public class FirstScreen extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_one, container, false);
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//        NavOptions navOptions = new NavOptions.Builder()
//                .setPopEnterAnim(R.anim.fade_in)
//                .setEnterAnim(R.anim.slide_in)
//                .setPopExitAnim(R.anim.slide_out)
//                .setExitAnim(R.anim.fade_out)
//                .build();
//
//        view.findViewById(R.id.next).setOnClickListener(v -> navController.navigate(R.id.secondScreen, null, navOptions));
        return view;
    }

}