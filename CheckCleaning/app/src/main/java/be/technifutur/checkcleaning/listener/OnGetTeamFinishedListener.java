package be.technifutur.checkcleaning.listener;

import java.util.List;

import be.technifutur.checkcleaning.entity.User;

public interface OnGetTeamFinishedListener {

    void onGetTeamSuccess(List<User> team);
}
