package be.technifutur.checkcleaning.repository;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.listener.OnCreateBuildingFinishedListener;
import be.technifutur.checkcleaning.listener.OnGetTeamFinishedListener;
import be.technifutur.checkcleaning.listener.OnGetUserFinishedListener;
import be.technifutur.checkcleaning.listener.OnLoginFinishedListener;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.listener.OnUpdateUserFinishedListener;

public class UserRepository{

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public UserRepository() {

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    public boolean isAuthentificate(){
        return mAuth.getUid() != null;
    }

    public void signOut(){

        mAuth.getInstance().signOut();
    }

    public void signInByEmailAndPassword(String email, String password, final OnLoginFinishedListener listener, Activity view) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onLoginSuccess();
                        }else if(task.getException() instanceof FirebaseAuthInvalidUserException) {
                            listener.onUsernameError();
                        }else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                            listener.onPasswordError();
                        }
                    }
                });
    }

    public void getById(final OnGetUserFinishedListener listener) {

        DocumentReference docRef = mDatabase.collection("user").document(mAuth.getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().toObject(User.class);
                    listener.onGetUserSuccess(user);
                }
            }
        });
    }

    public void addBuildingToUser(List<String> buildings_id, final OnCreateBuildingFinishedListener listener) {

        mDatabase.collection("user").document(mAuth.getUid()).update("buildings_id", buildings_id)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onAddBuildingToUser();
                    }
                });
    }

    /**
     * Génère un document (avec un id) afin de l'assigner ensuite à l'objet User
     * @return
     */

    public String generateUserId(){

        DocumentReference ref = mDatabase.collection("user").document();

        return ref.getId();
    }

    public void getUsersByBuilding(final List<String> usersId, final OnGetTeamFinishedListener listener){

        final List<User> team = new ArrayList<>();
        final int size = usersId.size();

        for (int i = 0; i < size; i++){

            if (!usersId.get(i).equals(mAuth.getUid())){
                final String userId = usersId.get(i);

                DocumentReference docRef = mDatabase.collection("user").document(userId);

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            User user = task.getResult().toObject(User.class);
                            team.add(user);

                            if(usersId.size() - 1 == team.size()){
                                listener.onGetTeamSuccess(team);
                            }
                        }
                    }
                });
            }
        }
    }

    public void updateUser(User user, final OnUpdateUserFinishedListener listener){

        mDatabase.collection("user").document(user.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onUpdateUserSuccess();
            }
        });
    }
}
