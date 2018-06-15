package be.technifutur.checkcleaning.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable{

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String role;
    private String token;
    private List<String> buildings_id;
    private List<TaskData> tasks;

    public User(){}

    protected User(Parcel in) {
        id = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        phone_number = in.readString();
        role = in.readString();
        token = in.readString();
        buildings_id = new ArrayList<>();
        in.readList(buildings_id, String.class.getClassLoader());
        tasks = new ArrayList<>();
        in.readList(tasks, TaskData.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public List<String> getBuildings_id() {
        return buildings_id;
    }

    public void setBuildings_id(List<String> buildings_id) {
        this.buildings_id = buildings_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(phone_number);
        dest.writeString(role);
        dest.writeString(token);
        dest.writeList(buildings_id);
        dest.writeList(tasks);
    }

    /*private void readFromParcel(Parcel in) {
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        phone_number = in.readString();
        role = in.readString();
        token = in.readString();
        buildings_id = in.readList(String.class.getClassLoader());
        tasks = in.readList(Task.class.getClassLoader());
    }*/
}

