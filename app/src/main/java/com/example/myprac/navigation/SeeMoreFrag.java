package com.example.myprac.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myprac.R;

public class SeeMoreFrag extends Fragment {

    private View view;
    private LinearLayout setting;
    private LinearLayout dacdamol;
    private LinearLayout customerSenter;
    private LinearLayout notice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.seemorefrag, container, false);

        setting = view.findViewById(R.id.setting);
        dacdamol = view.findViewById(R.id.dacdamol);
        customerSenter = view.findViewById(R.id.customerCenter);
        notice = view.findViewById(R.id.notice);

        dacdamol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://mall.drdiary.co.kr/"));
                startActivity(intent);
            }
        });


        return view;
    }
}
