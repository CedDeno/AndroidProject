package be.technifutur.checkcleaning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import be.technifutur.checkcleaning.activity.BuildingChoiceActivity;
import be.technifutur.checkcleaning.activity.SignUpActivity;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.fragment.LoadingAnimationFragment;
import be.technifutur.checkcleaning.presenter.LoginPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.login_editText)
    EditText loginEditText;

    @BindView(R.id.password_editText)
    EditText passwordEditText;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
    }


    @OnClick(R.id.sign_in_button)
    public void onClickSignIn() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        String email = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        loginPresenter.validateLogs(email, password);
    }

    @OnClick(R.id.sign_up_button)
    public void onClickSignUp() {

        Intent myIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(myIntent);
    }

    public void setHintOfEditTextToRed(boolean isUserError) {

        if(isUserError){

            loginEditText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
        }else{
            passwordEditText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
        }
    }

    public void showBuildingChoiceActivity(User user) {

        //BuildingChoiceFragment fragment = BuildingChoiceFragment.newInstance(user);
        //getSupportFragmentManager().beginTransaction().add(R.id.main_activity, fragment).addToBackStack(null).commit();

        Intent intent = new Intent(this, BuildingChoiceActivity.class);
        intent.putExtra("user", user);
        getSupportFragmentManager().popBackStackImmediate();
        startActivity(intent);
    }

    public void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startAnimationActivity() {

        Fragment fragment = new LoadingAnimationFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fade_in_bottom, android.R.animator.fade_out);
        ft.replace(R.id.main_activity, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
}
