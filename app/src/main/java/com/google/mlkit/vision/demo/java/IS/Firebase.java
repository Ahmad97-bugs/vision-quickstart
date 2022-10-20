package com.google.mlkit.vision.demo.java.IS;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Firebase{
    //    private Context context;
    private static Firebase instance = null;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Firebase getInstance(){
        if(instance == null){
            instance = new Firebase();
        }
        return instance;
    }

    //add user u to firebase
    public void createUser(User u){
        db.collection("users")
                .document(u.getEmail()).set(u)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                    }
                });
    }


    //add jump j to firebase
    public void createJump(Jump j){
        db.collection("jumps")
                .document(j.getUserID()).collection(String.valueOf(1)).document().set(j)
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                    }
                });
    }


    //fetch all users from firebase
    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task){
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        //return Array
                        Map<String, Object> user = document.getData();
                        String id = (String) user.get("id");
                        String email = (String) user.get("email");
                        String name = (String) user.get("userName");
                        String password = (String) user.get("password");
                        User u = new User(id, name, email, password);
                        userList.add(u);
                    }
                }
            }
        });
        return userList;
    }

    //fetch all jumps for specific user form firebase
    public void getAllJumps(String userID){
        List<Jump> jumpList = new ArrayList<>();
        db.collection("jumps/" + userID + "/1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task){
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> jump = document.getData();
                        String jumpID = document.getId();
                        String userid = (String) jump.get("userID");
                        Float height = ((Double) jump.get("height")).floatValue();
                        Long date = (Long) jump.get("date");
                        Jump j = new Jump(jumpID, userid, height, date);
                        jumpList.add(j);
                    }
                }
                DatabaseManager.getInstance().setJumps(jumpList);
            }
        });
    }


    //delete a specific jump for user
    public void deleteJump(Jump j, String userID){
        db.collection("jumps/" + userID + "/1")
                .document(j.getJumpID())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>(){
                    @Override
                    public void onSuccess(Void aVoid){
                        DatabaseManager.getInstance().getJumps(userID);
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e){
                    }
                });
    }


    //delete all jumps for specific user
    public void deleteAllJumps(String userID){
        for(Jump j : DatabaseManager.getInstance().getJumps(userID)){
            deleteJump(j, userID);
        }
    }
}
