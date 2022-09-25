package com.google.mlkit.vision.demo.java.IS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "JUMP_APP_DB";


    private static final String TABLE_USERS_NAME = "users";
    private static final String USER_COLUMN_ID = "id";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_PASSWORD = "password";
    private static final String[] TABLE_USERS_COLUMNS = {USER_COLUMN_ID, USER_COLUMN_NAME, USER_COLUMN_EMAIL, USER_COLUMN_PASSWORD};

    private SQLiteDatabase db = null;
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PARENT_TABLE = "create table if not exists " + TABLE_USERS_NAME + " ( "
                    + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_COLUMN_NAME + " name,"
                    + USER_COLUMN_EMAIL + " TEXT, "
                    + USER_COLUMN_PASSWORD + " TEXT)";
            db.execSQL(CREATE_PARENT_TABLE);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // drop item table if already exists
            db.execSQL("DROP TABLE IF EXISTS users");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        //onCreate(db);
    }

    public void createUser(User user) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_ID, user.getId());
            values.put(USER_COLUMN_NAME, user.getName());
            values.put(USER_COLUMN_EMAIL, user.getEmail());
            values.put(USER_COLUMN_PASSWORD, user.getPassword());
            // insert item
            db.insert(TABLE_USERS_NAME, null, values);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public User readUsers(int id) {
        User user = null;
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_USERS_NAME, // a. table
                            TABLE_USERS_COLUMNS, USER_COLUMN_ID + " = ?",
                            new String[]{String.valueOf(id)}, null, null,
                            null, null);
            if (cursor != null)
                cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<User>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS_NAME, TABLE_USERS_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User item = cursorToUser(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }


    private User cursorToUser(Cursor cursor) {
        User result = new User();
        try {
            result.setId(cursor.getInt(0));
            result.setName(cursor.getString(1));
            result.setEmail(cursor.getString(2));
            result.setPassword(cursor.getString(3));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public int updateUser(User user) {
        int count = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_NAME, user.getName());
            values.put(USER_COLUMN_EMAIL, user.getEmail());
            values.put(USER_COLUMN_PASSWORD, user.getPassword());
            //time converted to INTEGER
            //values.put(PARENT_COLUMN_DATE, parent.getLicenseDate().getTime());
            // update
            count = db.update(TABLE_USERS_NAME, values, USER_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return count;
    }

    public void deleteUser(User user) {
        try {
            db.delete(TABLE_USERS_NAME, USER_COLUMN_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void deleteAllUsers() {
        try {
            db.delete(TABLE_USERS_NAME, null, null);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
