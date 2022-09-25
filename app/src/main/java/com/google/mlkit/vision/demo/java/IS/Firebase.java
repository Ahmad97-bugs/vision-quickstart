package com.google.mlkit.vision.demo.java.IS;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firebase {
    private Context context;
    private static Firebase instance = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Firebase getInstance() {
        if (instance == null) {
            instance = new Firebase();
        }
        return instance;
    }

    public void createUser(User u) {
        db.collection("parents")
                .document(u.getEmail()).set(u)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Task<QuerySnapshot> parents = db.collection("parents").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //return Array
                        Map<String, Object> user = document.getData();
                        int id = (Integer) user.get("id");
                        String email = (String) user.get("email");
                        String name = (String) user.get("userName");
                        String password = (String) user.get("password");
                        User u = new User(id, name, email, password);
                        userList.add(u);
                    }
                } else {
                    //todo error message
                }
            }
        });
        return userList;
    }

    public boolean setUser(User u) {
        final boolean[] flag = {true};
        Map<String, Object> user = new HashMap<>();
        user.put("email", u.getEmail());
        user.put("id", u.getId());
        user.put("password", u.getPassword());
        user.put("name", u.getName());
        db.collection("users")
                .document(u.getEmail()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0] = true;
                        //todo
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0] = false;
                    }
                });
        if (flag[0]) {
            return true;
        }
        return false;
    }

    public boolean deleteUser(User u) {
        final boolean[] flag = {true};
        db.collection("users")
                .document(u.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        flag[0] = true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0] = false;
                    }
                });
        if (flag[0]) {
            return true;
        }
        return false;
    }

}
