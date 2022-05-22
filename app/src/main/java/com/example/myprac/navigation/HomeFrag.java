package com.example.myprac.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myprac.R;
import com.example.myprac.home.BannerAdapter;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFrag extends Fragment {

    private View view;

    ViewPager2 homeBanner;
    private CircleIndicator3 mIndicator;
    BannerAdapter bannerAdapter;

    int[] images = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground}; //이미지 주소를 넣는다

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefrag, container, false);

        homeBanner = view.findViewById(R.id.home_banner);
        bannerAdapter = new BannerAdapter(images);
        homeBanner.setAdapter(bannerAdapter);

        mIndicator = (CircleIndicator3)view.findViewById(R.id.banner_indicator);
        mIndicator.setViewPager(homeBanner);

        return view;
    }
}
