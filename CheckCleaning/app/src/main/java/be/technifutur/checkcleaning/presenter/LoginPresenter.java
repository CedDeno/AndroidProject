package be.technifutur.checkcleaning.presenter;

import com.google.firebase.auth.FirebaseAuth;

import be.technifutur.checkcleaning.MainActivity;
import be.technifutur.checkcleaning.fragment.LoadingAnimationFragment;
import be.technifutur.checkcleaning.listener.OnGetUserFinishedListener;
import be.technifutur.checkcleaning.listener.OnLoginFinishedListener;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.repository.UserRepository;

public class LoginPresenter implements OnLoginFinishedListener, OnGetUserFinishedListener {

    private final MainActivity mView;
    private final UserRepository userRepo;

    public LoginPresenter(MainActivity view) {
        this.mView = view;
        this.userRepo = new UserRepository();
    }

    public void validateLogs(String email, String password) {

        if (email.contains("@") && password.length() >= 6) {

            userRepo.signInByEmailAndPassword(email, password, this, mView);
        } else {

            mView.showToast("Login échoué, vérifier les champs.");
        }
    }

    public void userExist(){

        if(userRepo.isAuthentificate()){
            mView.startAnimationActivity();
            userRepo.getById(this);
        }
    }

    @Override
    public void onUsernameError() {

        mView.showToast("Email invalide.");
        mView.setHintOfEditTextToRed(true);
    }

    @Override
    public void onPasswordError() {

        mView.showToast("Mot de passe invalide.");
        mView.setHintOfEditTextToRed(false);
    }

    @Override
    public void onLoginSuccess() {

        mView.startAnimationActivity();
        userRepo.getById(this);
    }

    @Override
    public void onGetUserSuccess(User user) {

        mView.showBuildingChoiceActivity(user);
    }
}
