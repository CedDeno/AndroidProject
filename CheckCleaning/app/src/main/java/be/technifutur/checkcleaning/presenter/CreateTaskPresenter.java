package be.technifutur.checkcleaning.presenter;

import be.technifutur.checkcleaning.entity.TaskData;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.fragment.CreateTaskFragment;
import be.technifutur.checkcleaning.listener.OnUpdateTasksFinishedListener;
import be.technifutur.checkcleaning.repository.TaskRepository;

public class CreateTaskPresenter implements OnUpdateTasksFinishedListener{

    private CreateTaskFragment mView;
    private TaskRepository mRepo;
    private User mUser;
    private TaskData mNewTask;

    public CreateTaskPresenter(CreateTaskFragment view, User user) {
        this.mView = view;
        mRepo = new TaskRepository();
        mUser = user;
    }

    public void saveTask(String taskText, String buildingName){

        mNewTask = new TaskData();
        mNewTask.setBuilding_name(buildingName);
        mNewTask.setContent(taskText);
        mUser.getTasks().add(mNewTask);

        mRepo.updateTasks(mUser, this);
    }

    @Override
    public void onUpdateTasksSuccess() {
        mView.updateRecyclerViewFromParent();
    }
}
