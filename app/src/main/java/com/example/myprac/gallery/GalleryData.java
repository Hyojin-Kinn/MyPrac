package com.example.myprac.gallery;

import android.net.Uri;

public class GalleryData {

    private Uri imgUri;
    private String preDiabets;
    private String postDiabets;

    public GalleryData(Uri imgUri, String preDiabets, String postDiabets) {
        this.imgUri = imgUri;
        this.preDiabets = preDiabets;
        this.postDiabets = postDiabets;
    }
    public GalleryData(String preDiabets, String postDiabets) {
        this.preDiabets = preDiabets;
        this.postDiabets = postDiabets;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    public String getPreDiabets() {
        return preDiabets;
    }

    public void setPreDiabets(String preDiabets) {
        this.preDiabets = preDiabets;
    }

    public String getPostDiabets() {
        return postDiabets;
    }

    public void setPostDiabets(String postDiabets) {
        this.postDiabets = postDiabets;
    }
}
