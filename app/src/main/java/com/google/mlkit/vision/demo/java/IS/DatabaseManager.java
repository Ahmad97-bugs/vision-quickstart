package com.google.mlkit.vision.demo.java.IS;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance = null;
    private Context context = null;
    private Database db = null;
    private User selectedUser = null;

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

    public void openDatabase(Context context) {
        this.context = context;
        if (context != null) {
            db = new Database(context);
//            db.deleteAllUsers();
            db.open();
        }
    }

    public void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    public boolean createUser(User u) {
        if(db.getAllUsers() != null)
            u.setId(db.getAllUsers().size() + 1);
        else{
            u.setId(1);
        }
        if (db != null) {
            db.createUser(u);
            Firebase.getInstance().createUser(u);
            return true;
        }
        return false;
    }

    public boolean createJump(Jump j) {
        if (db != null) {
            db.createJump(j);
            Firebase.getInstance().createJump(j);
            return true;
        }
        return false;
    }

    public User readUser(int id) {
        User result = null;
        if (db != null) {
            result = db.readUsers(id);
        }
        return result;
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<User>();
        if (db != null) {
            result = db.getAllUsers();
        }
        return result;
    }

    public List<Jump> getAllJumps(int id) {
        List<Jump> result = new ArrayList<Jump>();
        if (db != null) {
            result = db.readJumps(id);
            System.out.println("nooo");

        }
        return result;
    }

    public boolean updateUser(User u) {
        if (db != null && u != null) {
            db.updateUser(u);
        }
        if (Firebase.getInstance().setUser(u))
            return true;
        return false;
    }

    public boolean deleteUser(User u) {
        if (db != null) {
            db.deleteUser(u);
        }
        if (Firebase.getInstance().deleteUser(u))
            return true;
        return false;
    }

    public void removeAllUsers() {
        if (db != null) {
            db.deleteAllUsers();
        }
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }


}
