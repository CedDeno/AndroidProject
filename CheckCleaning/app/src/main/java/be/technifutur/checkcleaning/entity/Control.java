package be.technifutur.checkcleaning.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Control{

    private String detail;
    private int rating;
    private String comment;
    private List<Bitmap> pictures;

    public Control() {
        pictures = new ArrayList<>();
    }

    public Control(String detail, int rating, String comment, List<Bitmap> pictures) {
        this.detail = detail;
        this.rating = rating;
        this.comment = comment;
        this.pictures = pictures;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Bitmap> getPictures() {
        return pictures;
    }

    public void setPictures(List<Bitmap> pictures) {
        this.pictures = pictures;
    }
}

