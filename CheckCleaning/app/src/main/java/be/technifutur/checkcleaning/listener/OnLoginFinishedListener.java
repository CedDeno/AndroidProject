package be.technifutur.checkcleaning.listener;

public interface OnLoginFinishedListener {

    void onUsernameError();
    void onPasswordError();
    void onLoginSuccess();
}
