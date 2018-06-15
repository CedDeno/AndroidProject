package be.technifutur.checkcleaning.listener;

import java.util.List;

import be.technifutur.checkcleaning.entity.TaskData;

public interface OnGetTasksFinishedListener {

    void onGetTasksSuccess(List<TaskData> tasks);
}
