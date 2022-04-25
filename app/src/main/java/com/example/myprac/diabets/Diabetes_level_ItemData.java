package com.example.myprac.diabets;

public class Diabetes_level_ItemData {
    String time;
    String bef_n;
    String aft_n;


    public Diabetes_level_ItemData(String time, String bef_n, String aft_n) {
        this.time = time;
        this.bef_n = bef_n;
        this.aft_n = aft_n;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getBef_n() {
        return bef_n;
    }

    public void setBef_n(String bef_n) {
        this.bef_n = bef_n;
    }
    public String getAft_n() {
        return aft_n;
    }

    public void setAft_n(String aft_n) {
        this.aft_n = aft_n;
    }
}
