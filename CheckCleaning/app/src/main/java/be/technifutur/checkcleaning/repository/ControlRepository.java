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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.listener.OnCreateControlFinishedListener;
import be.technifutur.checkcleaning.listener.OnGetControlsFinishedListener;

public class ControlRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public ControlRepository() {

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    public void getByBuildingId(final String buildingId, final List<String> categories, final OnGetControlsFinishedListener listener) {

        final Map<String, List<Control>> controlMap = new HashMap<>();

        for (int i = 0; i < categories.size(); i++){

            CollectionReference colRef = mDatabase.collection("building_control").document(buildingId).collection(categories.get(i));

            final int finalI = i;
            colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()){

                        List<Control> controls = new ArrayList<>();

                        for (DocumentSnapshot doc : task.getResult().getDocuments()){

                            Control c = doc.toObject(Control.class);
                            controls.add(c);
                        }

                        controlMap.put(categories.get(finalI), controls);

                        if (controlMap.size() == 7){

                            listener.onGetControlsSuccess(controlMap);
                        }
                    }
                }
            });
        }
    }

    public void addControlToCategory(String buildingId, String category, Control mControl, final OnCreateControlFinishedListener listener) {

        mDatabase.collection("building_control").document(buildingId).collection(category).add(mControl)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        listener.onCreateControlSuccess();
                    }
                });
    }
}
