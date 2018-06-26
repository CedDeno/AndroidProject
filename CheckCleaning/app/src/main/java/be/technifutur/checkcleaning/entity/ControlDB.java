package be.technifutur.checkcleaning.entity;

import java.util.Date;

public class ControlDB {

    private int rating;
    private Date date;

    public ControlDB() {
    }

    public ControlDB(int rating, Date date) {
        this.rating = rating;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
