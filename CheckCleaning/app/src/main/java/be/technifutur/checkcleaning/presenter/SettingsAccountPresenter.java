package be.technifutur.checkcleaning.presenter;

import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.fragment.SettingsAccountFragment;
import be.technifutur.checkcleaning.listener.OnUpdateUserFinishedListener;
import be.technifutur.checkcleaning.repository.UserRepository;

public class SettingsAccountPresenter implements OnUpdateUserFinishedListener{

    private final SettingsAccountFragment mView;
    private final UserRepository mRepo;
    private User mUser;

    public SettingsAccountPresenter(SettingsAccountFragment view, User user) {
        this.mView = view;
        this.mRepo = new UserRepository();
        this.mUser = user;
    }

    public void updateUserAccount(){

        mRepo.updateUser(mUser, this);
    }

    @Override
    public void onUpdateUserSuccess() {
        mView.displayToastOnUpdateUser();
    }
}
