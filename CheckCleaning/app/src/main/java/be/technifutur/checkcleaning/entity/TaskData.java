package be.technifutur.checkcleaning.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import be.technifutur.checkcleaning.activity.BottomBarActivity;

public class TaskData implements Parcelable, Comparable<TaskData> {


    private String building_name;
    private String content;

    public TaskData() { }

    public TaskData(String building_name, String content) {
        this.building_name = building_name;
        this.content = content;
    }

    protected TaskData(Parcel in) {
        building_name = in.readString();
        content = in.readString();
    }

    public static final Creator<be.technifutur.checkcleaning.entity.TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };

    public static Creator<be.technifutur.checkcleaning.entity.TaskData> getCREATOR() {
        return CREATOR;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(building_name);
        dest.writeString(content);
    }

    public int compareTo(@NonNull TaskData t) {

        if(building_name.equals(BottomBarActivity.mBuildingName) && !t.building_name.equals(BottomBarActivity.mBuildingName)){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return building_name + " : " + content;
    }
}
