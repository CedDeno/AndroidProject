package be.technifutur.checkcleaning.presenter;

import java.util.List;

import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.listener.OnGetTeamFinishedListener;
import be.technifutur.checkcleaning.repository.UserRepository;

public class BottomBarPresenter implements OnGetTeamFinishedListener{

    private BottomBarActivity mView;
    private UserRepository mRepo;
    private User mUser;
    private List<String> mUsersId;
    private List<User> mTeam;

    public BottomBarPresenter(BottomBarActivity mView, List<String> usersId) {
        this.mView = mView;
        this.mRepo = new UserRepository();
        mUsersId = usersId;
    }

    public void loadTeam(){
        mRepo.getUsersByBuilding(mUsersId, this);
    }

    @Override
    public void onGetTeamSuccess(List<User> team) {
        mTeam = team;
        mView.createViewPager(team);
    }
}
