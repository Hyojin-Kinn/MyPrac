package com.example.myprac.navigation;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFrag extends Fragment {

    private View view;

    ViewPager2 homeBanner;
    ViewPager2 homeBanner2;
    private CircleIndicator3 mIndicator;
    BannerAdapter bannerAdapter;
    BannerAdapter bannerAdapter2;
    BarChart barChart;

    TextView todayDate;
    ImageButton refresh_btn;

    TextView dbt1;
    TextView dbt2;
    TextView dbt3;
    TextView dbt4;

    TextView state_tv;

    ArrayList<BarEntry> bar_entry; //식전 혈당
    ArrayList<BarEntry> bar_entry2; //식후 혈당
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

        barChart = view.findViewById(R.id.bar_chart);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        bar_entry = new ArrayList<>(); //차트에 띄울 데이터 리스트
        bar_entry2 = new ArrayList<>();

        int startX = 0;

        //현재 날짜 가져오기
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String myDate = sdf.format(date);

        state_tv = view.findViewById(R.id.home_state);

        for(int i = 0;i<diabetesList.size();i++) {
            Diabetes_level_ItemData d = diabetesList.get(i);
            if((d.getAft_n()>=200)||(d.getBef_n()>=126)) {
                state_tv.setText("혈당 수치가 높습니다.");
                state_tv.setTextColor(Color.rgb(255,111,111));
            }
            else if((d.getAft_n()>=140)||(d.getBef_n()>=100)) {
                state_tv.setText("혈당 수치가 살짝 높습니다.");
                state_tv.setTextColor(Color.rgb(255,227,88));
            }
            else {
                state_tv.setText("혈당 수치가 정상입니다.");
                state_tv.setTextColor(Color.rgb(91,219,100));
            }
        }
        //오늘 날짜인 데이터만 차트에 추가
        for(int i = 0;i<diabetesList.size();i++) {
            Diabetes_level_ItemData d = diabetesList.get(i);
            if(d.getDate().equals(myDate)) {
                float bef_n = d.getBef_n();
                bar_entry.add(new BarEntry(i, bef_n));
                float aft_n = d.getAft_n();
                bar_entry2.add(new BarEntry(i, aft_n));
            }
        }

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        //x축 라벨링할 값
        ArrayList<String> xLabel = new ArrayList<>();
        for(Diabetes_level_ItemData d:diabetesList) {
            xLabel.add(d.getTime());
        }
        String[] array = xLabel.toArray(new String[xLabel.size()]);

        xAxis.setAxisMinimum(startX);
        xAxis.setAxisMaximum(startX+4*(2*(0.2f+0.03f)+0.08f));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(0.54f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis){
                //return String.valueOf((int) value);
                return array[(int) value % array.length];
            }
        });

        /*xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return array[(int) value % array.length];
            }
        });*/

        yAxisLeft.setAxisMaximum(250);
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setDrawGridLines(false);

        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        //BarData bd = new BarData();
        BarDataSet barDataSet = new BarDataSet(bar_entry, "식전혈당");
        BarDataSet barDataSet2 = new BarDataSet(bar_entry2, "식후혈당");

        barDataSet.setColor(Color.rgb(210,210,210)); //바 색상
        barDataSet2.setColor(Color.rgb(255,168,53));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        dataSets.add(barDataSet2);
        BarData bd = new BarData(dataSets);
        barChart.setData(bd);
        bd.setBarWidth(0.2f);
        bd.groupBars(startX,0.08f,0.03f);

        barChart.invalidate();
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);

        dbt1 = view.findViewById(R.id.home_diabete1);
        dbt2 = view.findViewById(R.id.home_diabete2);
        dbt3 = view.findViewById(R.id.home_diabete3);
        dbt4 = view.findViewById(R.id.home_diabete4);

        if(diabetesList.size()>0) {
            if(diabetesList.size() <= 1) {
                dbt1.setText(diabetesList.get(0).getTime());
            }
            else if(diabetesList.size() <= 2) {
                dbt1.setText(diabetesList.get(0).getTime());
                dbt2.setText(diabetesList.get(1).getTime());
            }
            else if(diabetesList.size() <= 3) {
                dbt1.setText(diabetesList.get(0).getTime());
                dbt2.setText(diabetesList.get(1).getTime());
                dbt3.setText(diabetesList.get(2).getTime());
            }
            else if(diabetesList.size() <= 4) {
                dbt1.setText(diabetesList.get(0).getTime());
                dbt2.setText(diabetesList.get(1).getTime());
                dbt3.setText(diabetesList.get(2).getTime());
                dbt4.setText(diabetesList.get(3).getTime());
            }
        }

        //오늘 날짜 표시
        todayDate = view.findViewById(R.id.home_date);

        todayDate.setText(myDate);

        //새로고침 버튼
        refresh_btn = view.findViewById(R.id.refresh_btn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String myDate = sdf.format(date);

                todayDate.setText(myDate);
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
