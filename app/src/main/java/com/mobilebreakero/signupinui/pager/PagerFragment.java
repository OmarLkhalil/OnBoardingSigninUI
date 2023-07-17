package com.mobilebreakero.signupinui.pager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.mobilebreakero.signupinui.R;
import com.mobilebreakero.signupinui.pager.screens.FirstScreen;
import com.mobilebreakero.signupinui.pager.screens.SecondScreen;
import com.mobilebreakero.signupinui.pager.screens.ThirdScreen;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;
import com.zhpan.indicator.utils.IndicatorUtils;

import java.util.ArrayList;
import java.util.List;

public class PagerFragment extends Fragment {

    private ViewPager2 viewPager;
    private IndicatorView indicatorView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FirstScreen());
        fragmentList.add(new SecondScreen());
        fragmentList.add(new ThirdScreen());

        viewPager = view.findViewById(R.id.pager_home);
        viewPager.setAdapter(new PagerAdapter(getActivity(), fragmentList));

        indicatorView = view.findViewById(R.id.indicatorView);


        indicatorView.setSlideMode(IndicatorSlideMode.WORM);
        indicatorView.setIndicatorStyle(IndicatorStyle.CIRCLE);
        indicatorView.setOrientation(IndicatorView.SCROLL_AXIS_VERTICAL);
        indicatorView.setSliderColor(getResources().getColor(R.color.grey2), getResources().getColor(R.color.red));
        indicatorView.setSliderWidth(IndicatorUtils.dp2px(15), IndicatorUtils.dp2px( 15));
        indicatorView.setSliderHeight(IndicatorUtils.dp2px(15));
        indicatorView.setSliderGap(IndicatorUtils.dp2px( 5));
        indicatorView.setPadding(10, 10, 10, 100);
        indicatorView.notifyDataChanged();



        indicatorView.setupWithViewPager(viewPager);
    }

    private static class PagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> fragmentList;

        public PagerAdapter(FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
            super(fragmentActivity);
            this.fragmentList = fragmentList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}