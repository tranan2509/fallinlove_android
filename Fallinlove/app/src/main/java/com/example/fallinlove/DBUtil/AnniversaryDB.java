package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Anniversary;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class AnniversaryDB extends DatabaseHandler{

    private static final String TABLE_ANNIVERSARY = "ANNIVERSARY";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static AnniversaryDB instance;

    public static AnniversaryDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new AnniversaryDB(context);
            }
            return instance;
        }
    }

    public AnniversaryDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(Anniversary anniversary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, anniversary.getUserId());
        values.put(KEY_NAME, anniversary.getName());
        values.put(KEY_DATE, anniversary.getDate());
        values.put(KEY_DESCRIPTION, anniversary.getDescription());
        values.put(KEY_IMAGE, anniversary.getImage());
        values.put(KEY_STATE, anniversary.isState());

        db.insert(TABLE_ANNIVERSARY, null, values);
        db.close();
    }

    public Anniversary get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ANNIVERSARY, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_DATE, KEY_DESCRIPTION, KEY_IMAGE, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Anniversary(cursor);
    }

    public Anniversary get(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ANNIVERSARY, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_DATE, KEY_DESCRIPTION, KEY_IMAGE, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Anniversary(cursor);
    }

    public List<Anniversary> gets(){
        List<Anniversary> anniversaries = new ArrayList<Anniversary>();
        String query = "SELECT * FROM " + TABLE_ANNIVERSARY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Anniversary anniversary = new Anniversary(cursor);
                anniversaries.add(anniversary);
            }while (cursor.moveToNext());
        }
        return anniversaries;
    }

    public List<Anniversary> gets(User user){
        List<Anniversary> anniversaries = new ArrayList<Anniversary>();
        String query = "SELECT * FROM " + TABLE_ANNIVERSARY + " WHERE " + KEY_USER_ID + "=" + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Anniversary anniversary = new Anniversary(cursor);
                anniversaries.add(anniversary);
            }while (cursor.moveToNext());
        }
        return anniversaries;
    }

    public int update(Anniversary anniversary){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, anniversary.getUserId());
        values.put(KEY_NAME, anniversary.getName());
        values.put(KEY_DATE, anniversary.getDate());
        values.put(KEY_DESCRIPTION, anniversary.getDescription());
        values.put(KEY_IMAGE, anniversary.getImage());
        values.put(KEY_STATE, anniversary.isState());
        return db.update(TABLE_ANNIVERSARY, values, KEY_ID + "=?", new String[]{String.valueOf(anniversary.getId())});
    }

    public void delete(Anniversary anniversary){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANNIVERSARY, KEY_ID + "=?", new String[]{String.valueOf(anniversary.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANNIVERSARY, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_ANNIVERSARY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
