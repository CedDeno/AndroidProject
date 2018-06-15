package be.technifutur.checkcleaning.repository;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.listener.OnCreateBuildingFinishedListener;
import be.technifutur.checkcleaning.listener.OnGetBuildingsListener;
import be.technifutur.checkcleaning.entity.Building;

public class BuildingRepository{

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public BuildingRepository() {

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    public void getByUserId(final List<String> buildings_id, final OnGetBuildingsListener listener) {

        final List<Building> buildings = new ArrayList<>();
        final int size = buildings_id.size();

        for (int i = 0; i < size; i++){

            final String buildingId = buildings_id.get(i);

            DocumentReference docRef = mDatabase.collection("building").document(buildingId);

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        Building building = task.getResult().toObject(Building.class);
                        buildings.add(building);

                        if(buildings_id.size() == buildings.size()){
                            listener.onGetBuildingsSuccess(buildings);
                        }
                    }
                }
            });
        }
    }

    public void createBuilding(final Building building, final OnCreateBuildingFinishedListener listener) {

        final String newId = generateBuildingId();
        building.setId(newId);

        building.getUsers_id().add(mAuth.getUid());
        mDatabase.collection("building").document(newId).set(building).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onCreateBuildingSuccess(newId);
            }
        });
    }

    public void getBuildingIdByName(){

        /*DocumentReference docRef = mDatabase.collection("building").document(id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User user = task.getResult().toObject(User.class);
                    listener.onGetUserSuccess(user);
                }
            }
        })*/
    }

    /**
     * Génère un document (avec un id) afin de l'assigner ensuite à l'objet Building
     * @return
     */

    public String generateBuildingId(){

        DocumentReference ref = mDatabase.collection("building").document();

        return ref.getId();
    }
}
