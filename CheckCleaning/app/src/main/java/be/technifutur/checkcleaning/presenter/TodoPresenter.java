package be.technifutur.checkcleaning.presenter;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import be.technifutur.checkcleaning.entity.TaskData;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.fragment.TodoFragment;
import be.technifutur.checkcleaning.listener.OnGetTasksFinishedListener;
import be.technifutur.checkcleaning.listener.OnUpdateTasksFinishedListener;
import be.technifutur.checkcleaning.repository.TaskRepository;

public class TodoPresenter implements OnGetTasksFinishedListener, OnUpdateTasksFinishedListener {

    private final TodoFragment mView;
    private final TaskRepository mRepo;
    private User mUser;

    public TodoPresenter(TodoFragment mView, User user) {
        this.mView = mView;
        this.mRepo = new TaskRepository();
        mUser = user;
    }

    public void loadTasksInOrder(String userId){
        mRepo.getByUserId(userId, this);
    }

    @Override
    public void onGetTasksSuccess(List<TaskData> tasks) {
        mView.setRecyclerView(tasks);
    }

    public void removeTasks(Set<Integer> selections) {

        Toast.makeText(mView.getContext(), "TASKS = " + mUser.getTasks().toString() + " SELECTIONS = " + selections.toString(), Toast.LENGTH_LONG).show();
        System.out.println("TASKS = " + mUser.getTasks().toString() + " SELECTIONS = " + selections.toString());

        List<Integer> intList = new ArrayList<>();
        for (int i  : selections){

            intList.add(i);
        }

        for (int i = intList.size() - 1; i >= 0; i--){
            int selection = intList.get(i);
            mUser.getTasks().remove(selection);
        }

        mRepo.updateTasks(mUser, this);
    }

    @Override
    public void onUpdateTasksSuccess() {

    }
}
