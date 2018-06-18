package be.technifutur.checkcleaning.entity;

public class Control{

    private String detail;
    private int rating;
    private String comment;

    public Control() {
    }

    public Control(String detail, int rating, String comment) {
        this.detail = detail;
        this.rating = rating;
        this.comment = comment;
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
}

