package be.technifutur.checkcleaning.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Structure implements Parcelable {

    private int kitchenette;
    private int meeting_room;
    private int office;
    private int open_space;
    private int parking;
    private int relaxation_area;
    private int restaurant;
    private int shower;
    private int wc;

    public Structure() {
    }

    protected Structure(Parcel in) {
        kitchenette = in.readInt();
        meeting_room = in.readInt();
        office = in.readInt();
        open_space = in.readInt();
        parking = in.readInt();
        relaxation_area = in.readInt();
        restaurant = in.readInt();
        shower = in.readInt();
        wc = in.readInt();
    }

    public static final Creator<Structure> CREATOR = new Creator<Structure>() {
        @Override
        public Structure createFromParcel(Parcel in) {
            return new Structure(in);
        }

        @Override
        public Structure[] newArray(int size) {
            return new Structure[size];
        }
    };

    public int getKitchenette() {
        return kitchenette;
    }

    public void setKitchenette(int kitchenette) {
        this.kitchenette = kitchenette;
    }

    public int getMeeting_room() {
        return meeting_room;
    }

    public void setMeeting_room(int meeting_room) {
        this.meeting_room = meeting_room;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public int getOpen_space() {
        return open_space;
    }

    public void setOpen_space(int open_space) {
        this.open_space = open_space;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getRelaxation_area() {
        return relaxation_area;
    }

    public void setRelaxation_area(int relaxation_area) {
        this.relaxation_area = relaxation_area;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    public int getShower() {
        return shower;
    }

    public void setShower(int shower) {
        this.shower = shower;
    }

    public int getWc() {
        return wc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(kitchenette);
        dest.writeInt(meeting_room);
        dest.writeInt(office);
        dest.writeInt(open_space);
        dest.writeInt(parking);
        dest.writeInt(relaxation_area);
        dest.writeInt(restaurant);
        dest.writeInt(shower);
        dest.writeInt(wc);
    }
}
