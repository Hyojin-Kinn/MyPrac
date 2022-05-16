package com.example.myprac.navigation;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprac.R;
import com.example.myprac.diabets.Diabetes_level_ItemData;
import com.example.myprac.diabets.Diabetes_level_MyRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DiabetesFrag extends Fragment {

    private View view;

    ArrayList<Diabetes_level_ItemData> dataList = new ArrayList<>();
    Diabetes_level_MyRecyclerAdapter adapter = new Diabetes_level_MyRecyclerAdapter(dataList);

    TextView tv1, tv2;

    // 현재 날짜 표시
    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm:ss");
    String formatDate = dFormat.format(mReDate);
    String formatTime = tFormat.format(mReDate);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.diabetesfrag, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // 현재 날짜 표시
        tv1 = (TextView) view.findViewById(R.id.show_date);
        tv1.setText(formatDate);

        tv2 = (TextView) view.findViewById(R.id.show_time);
        tv2.setText(formatTime);

        Button btnInsert = (Button)view.findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuInsert(view);
            }
        });

        return view;
    }

    public void onMenuInsert(View view) {

        final View innerView = getLayoutInflater().inflate(R.layout.diabetes_level_list_insert, null);
        final Dialog mDialog = new Dialog(view.getContext());
        mDialog.setTitle("Title");
        mDialog.setContentView(innerView);
        mDialog.setCancelable(true);

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        final EditText editTime = mDialog.findViewById(R.id.dli_add_time);
        final EditText editBeforeN = mDialog.findViewById(R.id.dli_add_before_num);
        final EditText editAfterN = mDialog.findViewById(R.id.dli_add_after_num);
        Button btn_go = mDialog.findViewById(R.id.btn_go);
        Button btn_cancel = mDialog.findViewById(R.id.btn_cancel);

            //입력버튼을 누르면 실행되는 이벤트
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTime = editTime.getText().toString();
                String myBeforeN = editBeforeN.getText().toString();
                String myAfterN = editAfterN.getText().toString();

                dataList.add(new Diabetes_level_ItemData(myTime, myBeforeN, myAfterN));
                // Toast.makeText(getApplicationContext(), myTime, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        });

        // 취소를 누르면 다이얼로그 종료
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mDialog.dismiss();
                }
        });

        mDialog.show();
        adapter.notifyDataSetChanged();
    }
}
