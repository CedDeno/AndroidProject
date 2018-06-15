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
    private List<String> users_id;

    public Building() {

        users_id = new ArrayList<>();
    }

    public Building(String id, String name, String address, List<String> users_id) {
        this.name = name;
        this.address = address;
        this.users_id = users_id;
    }

    protected Building(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
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

    @Override
    public String toString() {
        return name;
    }
}
