package com.example.myprac.recipe;

import android.net.Uri;

public class StepData {

    private String step;
    private String img;

    public StepData(String step, String img) {
        this.step = step;
        this.img = img;
    }

    public String getStep() { return step; }
    public void setStep(String step) { this.step = step; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

}
