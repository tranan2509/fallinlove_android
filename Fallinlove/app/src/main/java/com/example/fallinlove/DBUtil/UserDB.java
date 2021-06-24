package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB extends DatabaseHandler{

    private static final String TABLE_USER = "USER";
    private static final String KEY_ID = "id";
    private static final String KEY_LOVED_DATE = "lovedDate";
    private static final String KEY_CREATED_DATE = "createdDate";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static UserDB instance;


    public static UserDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new UserDB(context);
            }
            return instance;
        }
    }

    public UserDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOVED_DATE, user.getLovedDate());
        values.put(KEY_CREATED_DATE, user.getCreatedDate());
        values.put(KEY_STATE, user.isState());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{KEY_ID, KEY_LOVED_DATE, KEY_CREATED_DATE, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new User(cursor);
    }

    public List<User> gets(){
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                User user = new User(cursor);
                users.add(user);
            }while (cursor.moveToNext());
        }
        return users;
    }

    public int update(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOVED_DATE, user.getLovedDate());
        values.put(KEY_CREATED_DATE, user.getCreatedDate());
        values.put(KEY_STATE, user.isState());

        return db.update(TABLE_USER, values, KEY_ID + "=?", new String[]{String.valueOf(user.getId())});
    }

    public void delete(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

}
