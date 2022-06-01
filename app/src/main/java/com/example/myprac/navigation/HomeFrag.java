package com.example.myprac.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myprac.MainActivity;
import com.example.myprac.R;
import com.example.myprac.diabets.Diabetes_level_ItemData;
import com.example.myprac.home.BannerAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFrag extends Fragment {

    private View view;

    ViewPager2 homeBanner;
    ViewPager2 homeBanner2;
    private CircleIndicator3 mIndicator;
    BannerAdapter bannerAdapter;
    BannerAdapter bannerAdapter2;
    BarChart barChart;

    TextView tx;

    ArrayList<BarEntry> bar_entry;
    ArrayList<Diabetes_level_ItemData> diabetesList;

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

        diabetesList = ((MainActivity)getActivity()).getDiabetesList();

        tx = view.findViewById(R.id.chart_title);

        if(diabetesList.size()>0) {
            Diabetes_level_ItemData d = diabetesList.get(0);
            tx.setText(d.getTime());
        }

        barChart = view.findViewById(R.id.bar_chart);
        bar_entry = new ArrayList<>(); //차트에 띄울 데이터 리스트

        for(int i = 0;i<diabetesList.size();i++) {
            Diabetes_level_ItemData d = diabetesList.get(i);
            float aft_n = d.getAft_n();
            bar_entry.add(new BarEntry(i,aft_n));
        }

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        xAxis.setAxisMaximum(4);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxisLeft.setAxisMaximum(250);
        yAxisLeft.setAxisMinimum(0);

        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        BarData bd = new BarData();
        BarDataSet barDataSet = new BarDataSet(bar_entry, "식후혈당");

        barDataSet.setColor(Color.DKGRAY); //바 색상
        bd.addDataSet(barDataSet);
        barChart.setData(bd);
        bd.setBarWidth(0.8f);

        barChart.invalidate();
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);

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
