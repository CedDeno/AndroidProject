package be.technifutur.checkcleaning.repository;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import be.technifutur.checkcleaning.entity.TaskData;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.listener.OnGetTasksFinishedListener;
import be.technifutur.checkcleaning.listener.OnUpdateTasksFinishedListener;

public class TaskRepository {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mDatabase;

    public TaskRepository() {

        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseFirestore.getInstance();
    }

    public void getByUserId(final String userId, final OnGetTasksFinishedListener listener) {

        CollectionReference colRef = mDatabase.collection("user").document(userId).collection("tasks");
        Query query = colRef.orderBy("building_name");

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                List<TaskData> tasks = new ArrayList<>();

                for (TaskData mTask : task.getResult().toObjects(TaskData.class)){

                    tasks.add(mTask);
                }

                listener.onGetTasksSuccess(tasks);
            }
        });
    }

    public void updateTasks(User user, final OnUpdateTasksFinishedListener listener){

        mDatabase.collection("user").document(mAuth.getUid()).set(user, SetOptions.mergeFields("tasks"))
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onUpdateTasksSuccess();
                    }
                });
    }
}
