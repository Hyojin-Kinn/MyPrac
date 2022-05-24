package com.example.myprac.navigation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myprac.R;
import com.example.myprac.home.BannerAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFrag extends Fragment {

    private View view;

    ViewPager2 homeBanner;
    ViewPager2 homeBanner2;
    private CircleIndicator3 mIndicator;
    BannerAdapter bannerAdapter;
    BannerAdapter bannerAdapter2;

    int firstImgCount = 0;
    private Handler headerHandler = new Handler();
    private Runnable headerRunnable = new Runnable() {
        @Override
        public void run() {
            homeBanner2.setCurrentItem(homeBanner2.getCurrentItem() + 1, true);

        }
    };

    int[] images = {R.drawable.banner_01, R.drawable.banner_02,
            R.drawable.banner_03}; //이미지 주소를 넣는다

    int[] images2 = {R.drawable.banner_04, R.drawable.banner_05};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.homefrag, container, false);

        /*images.add(R.drawable.banner_01);
        images.add(R.drawable.banner_02);
        images.add(R.drawable.banner_03);
        images2.add(R.drawable.banner_04);
        images2.add(R.drawable.banner_05);*/


        firstImgCount = images.length;
        homeBanner = view.findViewById(R.id.home_banner);
        bannerAdapter = new BannerAdapter(images, 0);
        homeBanner.setAdapter(bannerAdapter);

        mIndicator = (CircleIndicator3) view.findViewById(R.id.banner_indicator);
        mIndicator.createIndicators(images.length,0);

        homeBanner2 = view.findViewById(R.id.home_banner_2);
        bannerAdapter2 = new BannerAdapter(images2, 1);
        homeBanner2.setAdapter(bannerAdapter2);

        homeBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%firstImgCount);
            }
        });

        homeBanner2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                headerHandler.removeCallbacks(headerRunnable);
                headerHandler.postDelayed(headerRunnable, 7000); //슬라이드 7초 지속
            }
        });

        return view;
    }
    @Override
    public void onPause(){
        super.onPause();
        headerHandler.removeCallbacks(headerRunnable);
    }
    @Override
    public void onResume() {
        super.onResume();
        headerHandler.postDelayed(headerRunnable, 7000);
    }
}
