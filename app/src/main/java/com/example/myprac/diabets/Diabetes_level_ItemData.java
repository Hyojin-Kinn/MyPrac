package com.example.myprac.diabets;

import android.os.Parcel;
import android.os.Parcelable;

public class Diabetes_level_ItemData {


    String date;
    String time;
    float bef_n;
    float aft_n;


    public Diabetes_level_ItemData(String date, String time, float bef_n, float aft_n) {
        this.date = date;
        this.time = time;
        this.bef_n = bef_n;
        this.aft_n = aft_n;
    }

    public Diabetes_level_ItemData(String time, float bef_n, float aft_n) {
        this.time = time;
        this.bef_n = bef_n;
        this.aft_n = aft_n;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public float getBef_n() {
        return bef_n;
    }
    public void setBef_n(float bef_n) {
        this.bef_n = bef_n;
    }

    public float getAft_n() {
        return aft_n;
    }
    public void setAft_n(float aft_n) {
        this.aft_n = aft_n;
    }

}
