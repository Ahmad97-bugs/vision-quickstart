package com.google.mlkit.vision.demo.java.IS;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class DatabaseManager {
    private static DatabaseManager instance = null;
    private Context context = null;
    private List<Jump> jumps = new ArrayList<>();

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {

    }

    public Context getContext() {
        return context;

    }

    public boolean createUser(User u) {
        Firebase.getInstance().createUser(u);
        return true;
    }

    public boolean createJump(Jump j, String userID) {
        Firebase.getInstance().createJump(j, userID);
        return true;
    }

    public void initJumps(String userID){
        Firebase.getInstance().getAllJumps(userID);
    }

    public List<Jump> getJumps(String userID) {
        if(jumps.isEmpty())
            initJumps(userID);
        return jumps;
    }

    public void setJumps(List<Jump> jumps) {
        this.jumps = jumps;
    }
}
