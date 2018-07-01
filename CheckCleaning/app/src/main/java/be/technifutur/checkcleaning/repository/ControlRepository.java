package be.technifutur.checkcleaning.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.entity.ControlDB;
import be.technifutur.checkcleaning.listener.OnCreateControlFinishedListener;
import be.technifutur.checkcleaning.listener.OnGetControlsFinishedListener;

public class ControlRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public ControlRepository() {

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    public void getControlsByBuilding(String buildingId, final OnGetControlsFinishedListener listener){

        final List<ControlDB> controls = new ArrayList<>();

        CollectionReference colRef = mDatabase.collection("building_control").document(buildingId).collection("control");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ControlDB control = document.toObject(ControlDB.class);
                        controls.add(control);
                    }
                }
                listener.onGetControlsSuccess(controls);
            }
        });
    }
}
