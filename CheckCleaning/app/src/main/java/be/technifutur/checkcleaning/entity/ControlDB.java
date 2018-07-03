package be.technifutur.checkcleaning.entity;

import android.support.annotation.NonNull;

import java.util.Date;

public class ControlDB implements Comparable<ControlDB> {

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


    @Override
    public int compareTo(@NonNull ControlDB o) {

        if (date.getMonth() < o.getDate().getMonth()){
            return -1;
        }else{
            return 0;
        }
    }
}
