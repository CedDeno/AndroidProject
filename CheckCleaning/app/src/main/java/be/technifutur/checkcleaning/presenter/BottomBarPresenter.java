package be.technifutur.checkcleaning.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.entity.ControlDB;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.listener.OnGetControlsFinishedListener;
import be.technifutur.checkcleaning.listener.OnGetTeamFinishedListener;
import be.technifutur.checkcleaning.repository.ControlRepository;
import be.technifutur.checkcleaning.repository.UserRepository;

public class BottomBarPresenter implements OnGetTeamFinishedListener, OnGetControlsFinishedListener{

    private BottomBarActivity mView;
    private UserRepository mUserRepo;
    private ControlRepository mControlRepo;
    private Building mbuilding;
    private List<User> mTeam;

    public BottomBarPresenter(BottomBarActivity mView, Building building) {
        this.mView = mView;
        this.mUserRepo = new UserRepository();
        this.mControlRepo = new ControlRepository();
        mbuilding = building;
    }

    public void loadTeam(){
        mUserRepo.getUsersByBuilding(mbuilding.getUsers_id(), this);
    }

    @Override
    public void onGetTeamSuccess(List<User> team) {
        mTeam = team;
        mView.createViewPager(team);
    }

    public void loadControls() {

        mControlRepo.getControlsByBuilding(mbuilding.getId(), this);
    }

    @Override
    public void onGetControlsSuccess(List<ControlDB> controls) {

        mView.setControls(controls);
    }

    public void disconnectUser(){

        mUserRepo.signOut();
        mView.returnToMainActivity();
    }
}
