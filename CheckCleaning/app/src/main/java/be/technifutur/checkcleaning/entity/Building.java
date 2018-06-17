package be.technifutur.checkcleaning.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

public class Building implements Parcelable{

    private String id;
    private String name;
    private String address;
    private int cafetaria_count;
    private int meeting_room_count;
    private int office_count;
    private int open_space_count;
    private int relaxation_area_count;
    private int restaurant_count;
    private int wc_count;
    private List<String> users_id;

    public Building() {

        users_id = new ArrayList<>();
    }

    public Building(String id, String name, String address, List<String> users_id) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.users_id = users_id;
        this.cafetaria_count = 0;
        this.meeting_room_count = 0;
        this.office_count = 0;
        this.open_space_count = 0;
        this.relaxation_area_count = 0;
        this.restaurant_count = 0;
        this.wc_count = 0;
    }

    public Building(String id, String name, String address, int cafetaria_count, int meeting_room_count, int office_count, int open_space_count, int relaxation_area_count, int restaurant_count, int wc_count, List<String> users_id) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cafetaria_count = cafetaria_count;
        this.meeting_room_count = meeting_room_count;
        this.office_count = office_count;
        this.open_space_count = open_space_count;
        this.relaxation_area_count = relaxation_area_count;
        this.restaurant_count = restaurant_count;
        this.wc_count = wc_count;
        this.users_id = users_id;
    }

    protected Building(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        cafetaria_count = in.readInt();
        meeting_room_count = in.readInt();
        office_count = in.readInt();
        open_space_count = in.readInt();
        relaxation_area_count = in.readInt();
        restaurant_count = in.readInt();
        wc_count = in.readInt();
        users_id = new ArrayList<>();
        in.readList(users_id, User.class.getClassLoader());
    }

    public static final Creator<Building> CREATOR = new Creator<Building>() {
        @Override
        public Building createFromParcel(Parcel in) {
            return new Building(in);
        }

        @Override
        public Building[] newArray(int size) {
            return new Building[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(cafetaria_count);
        dest.writeInt(meeting_room_count);
        dest.writeInt(office_count);
        dest.writeInt(open_space_count);
        dest.writeInt(relaxation_area_count);
        dest.writeInt(restaurant_count);
        dest.writeInt(wc_count);
        dest.writeList(users_id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getUsers_id() {
        return users_id;
    }

    public void setUsers_id(List<String> users_id) {
        this.users_id = users_id;
    }

    public int getCafetaria_count() {
        return cafetaria_count;
    }

    public void setCafetaria_count(int cafetaria_count) {
        this.cafetaria_count = cafetaria_count;
    }

    public int getMeeting_room_count() {
        return meeting_room_count;
    }

    public void setMeeting_room_count(int meeting_room_count) {
        this.meeting_room_count = meeting_room_count;
    }

    public int getOffice_count() {
        return office_count;
    }

    public void setOffice_count(int office_count) {
        this.office_count = office_count;
    }

    public int getOpen_space_count() {
        return open_space_count;
    }

    public void setOpen_space_count(int open_space_count) {
        this.open_space_count = open_space_count;
    }

    public int getRelaxation_area_count() {
        return relaxation_area_count;
    }

    public void setRelaxation_area_count(int relaxation_area_count) {
        this.relaxation_area_count = relaxation_area_count;
    }

    public int getRestaurant_count() {
        return restaurant_count;
    }

    public void setRestaurant_count(int restaurant_count) {
        this.restaurant_count = restaurant_count;
    }

    public int getWc_count() {
        return wc_count;
    }

    public void setWc_count(int wc_count) {
        this.wc_count = wc_count;
    }

    @Override
    public String toString() {
        return name;
    }
}
