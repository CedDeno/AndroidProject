package be.technifutur.checkcleaning.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.listener.OnGetControlsFinishedListener;

public class ControlRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public ControlRepository() {

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    public void getByBuildingId(String buildingId, final OnGetControlsFinishedListener listener) {

        final List<Control> controls = new ArrayList<>();

        CollectionReference colRef = mDatabase.collection("building_control").document(buildingId).collection("control");

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Control control = doc.toObject(Control.class);
                        controls.add(control);
                    }
                    listener.onGetControlsSuccess(controls);
                }
            }
        });
    }
}
