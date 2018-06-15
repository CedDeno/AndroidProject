package be.technifutur.checkcleaning.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import be.technifutur.checkcleaning.MainActivity;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.lastName_editText)
    EditText lastNameEditText;

    @BindView(R.id.firstName_editText)
    EditText firstNameEditText;

    @BindView(R.id.email_editText)
    EditText emailEditText;

    @BindView(R.id.phoneNumber_editText)
    EditText phoneNumberEditText;

    @BindView(R.id.password_editText)
    EditText passwordEditText;

    @BindView(R.id.confirPassword_editText)
    EditText confirPasswordEditText;

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+[a-z]+";
    private boolean lastNameIsOk = false;
    private boolean firstNameIsOk = false;
    private boolean emailIsOk = false;
    private boolean mobileIsOk = false;
    private boolean passwordIsOk = false;
    private boolean confirmPasswordIsOk = false;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        final Drawable errorIcon = getResources().getDrawable(R.mipmap.ic_launcher);

        setTitle("Inscription");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        lastNameEditText.addTextChangedListener(this);
        firstNameEditText.addTextChangedListener(this);
        emailEditText.addTextChangedListener(this);
        phoneNumberEditText.addTextChangedListener(this);
        passwordEditText.addTextChangedListener(this);
        confirPasswordEditText.addTextChangedListener(this);
    }

    @OnClick(R.id.confirm_signUp_button)
    public void onValidateSignUp(){

        if (allFieldsAreOk()){
            createUserAuth();

        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.hashCode() == lastNameEditText.getText().hashCode()) {

            lastNameIsOk = setHintOfSimpleEditText(lastNameEditText, 3);
        }else if (s.hashCode() == firstNameEditText.getText().hashCode()) {

            firstNameIsOk = setHintOfSimpleEditText(firstNameEditText, 3);
        }else if (s.hashCode() == emailEditText.getText().hashCode()) {

            emailIsOk = setHintOfEmailEditText(emailEditText);
        }else if (s.hashCode() == phoneNumberEditText.getText().hashCode()) {

            mobileIsOk = setHintOfPhoneNumberEditText(phoneNumberEditText);
        }else if (s.hashCode() == passwordEditText.getText().hashCode()) {

            passwordIsOk = setHintOfSimpleEditText(passwordEditText, 6);
        }else{
            confirmPasswordIsOk = matchingPassword(passwordEditText, confirPasswordEditText);
        }
    }

    public boolean setHintOfSimpleEditText(EditText editText, int sizeMin){

        if(editText.length() >= sizeMin){
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.greenOkColor));
            return true;
        }else{
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
            return false;
        }
    }

    public boolean setHintOfEmailEditText(EditText editText){

        if(editText.getText().toString().matches(emailPattern)){
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.greenOkColor));
            return true;
        }else{
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
            return false;
        }
    }

    public boolean setHintOfPhoneNumberEditText(EditText editText){

        if(editText.getText().length() == 10){
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.greenOkColor));
            return true;
        }else{
            editText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
            return false;
        }
    }

    public boolean matchingPassword(EditText firstEditText, EditText secondEditText){

        if(firstEditText.getText().toString().equals(secondEditText.getText().toString())){
            secondEditText.setBackgroundTintList(getResources().getColorStateList(R.color.greenOkColor));
            return true;
        }else{
            secondEditText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
            return false;
        }
    }

    public boolean allFieldsAreOk(){

        return lastNameIsOk && firstNameIsOk && emailIsOk && mobileIsOk && passwordIsOk && confirmPasswordIsOk;
    }

    public void createUserAuth() {


        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    createUser();

                                } else {
                                    Toast.makeText(SignUpActivity.this, R.string.email_already_exist_toast, Toast.LENGTH_SHORT).show();
                                    emailEditText.setBackgroundTintList(getResources().getColorStateList(R.color.redErrorColor));
                                }
                            }
                        });

    }

    public void createUser(){

        currentUser = new User();
        currentUser.setId(mAuth.getUid());
        currentUser.setLast_name(lastNameEditText.getText().toString());
        currentUser.setFirst_name(firstNameEditText.getText().toString());
        currentUser.setEmail(emailEditText.getText().toString());
        currentUser.setPhone_number(phoneNumberEditText.getText().toString());
        currentUser.setRole("admin");

        mDatabase.collection("user").document(mAuth.getUid()).set(currentUser).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(SignUpActivity.this, BottomBarActivity.class);
                        SignUpActivity.this.startActivity(intent);
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "ERREUR : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
