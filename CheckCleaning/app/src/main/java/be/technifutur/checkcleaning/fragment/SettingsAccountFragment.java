package be.technifutur.checkcleaning.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.presenter.SettingsAccountPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsAccountFragment extends Fragment implements TextWatcher {

    @BindView(R.id.settings_account_firstName_editText)
    EditText firstNameEditText;
    @BindView(R.id.settings_account_lastName_editText)
    EditText lastNameEditText;
    @BindView(R.id.settings_account_mobile_number_editText)
    EditText mobileNumberEditText;
    @BindView(R.id.settings_account_update_button)
    Button updateButton;

    private User mUser;
    private SettingsAccountPresenter mPresenter;
    private boolean mIsClickable;
    Unbinder unbinder;

    public SettingsAccountFragment() {
        // Required empty public constructor
    }

    public static SettingsAccountFragment newInstance(User user) {
        SettingsAccountFragment fragment = new SettingsAccountFragment();
        fragment.mUser = user;
        fragment.mIsClickable = true;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_account, container, false);
        ButterKnife.bind(this, view);

        firstNameEditText.setText(mUser.getFirst_name());
        lastNameEditText.setText(mUser.getLast_name());
        mobileNumberEditText.setText(mUser.getPhone_number());
        firstNameEditText.addTextChangedListener(this);
        lastNameEditText.addTextChangedListener(this);
        mobileNumberEditText.addTextChangedListener(this);
        updateButton.setEnabled(false);
        mPresenter = new SettingsAccountPresenter(this, mUser);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (lastNameEditText.getText().toString().equals(mUser.getLast_name())
                && firstNameEditText.getText().toString().equals(mUser.getFirst_name())
                && mobileNumberEditText.getText().toString().equals(mUser.getPhone_number())) {

            updateButton.setEnabled(false);
            updateButton.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        }else if (lastNameEditText.getText().length() < 3
                || firstNameEditText.getText().length() < 3
                || mobileNumberEditText.getText().length() < 10){

            updateButton.setEnabled(false);
            updateButton.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        }else{
            updateButton.setEnabled(true);
            updateButton.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
    }

    @OnClick(R.id.settings_account_update_button)
    public void updateAccount(){

        if (mIsClickable){
            mIsClickable = false;
            mUser.setFirst_name(firstNameEditText.getText().toString());
            mUser.setLast_name(lastNameEditText.getText().toString());
            mUser.setPhone_number(mobileNumberEditText.getText().toString());
            mPresenter.updateUserAccount();
        }
    }

    public void displayToastOnUpdateUser() {

        Toast.makeText(getContext(), "Coordonnées mises à jour.", Toast.LENGTH_SHORT).show();
        updateButton.setEnabled(false);
        mIsClickable = true;
    }
}
