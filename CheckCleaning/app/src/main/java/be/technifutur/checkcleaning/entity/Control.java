package be.technifutur.checkcleaning.entity;

import android.support.annotation.NonNull;

public class Control implements Comparable<Control>{

    private String id;
    private String category;
    private String detail;
    private int rating;
    private String comment;

    public Control() {
    }

    public Control(String id, String category, String detail, int rating, String comment) {
        this.id = id;
        this.category = category;
        this.detail = detail;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int compareTo(@NonNull Control t) {

        if(category.equals(t.category)){
            return 0;
        }else{
            return -1;
        }
    }
}

