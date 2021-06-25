package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Background;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class BackgroundDB extends DatabaseHandler{

    private static final String TABLE_BACKGROUND = "BACKGROUND";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_TYPE = "type";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static BackgroundDB instance;

    public static BackgroundDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new BackgroundDB(context);
            }
            return instance;
        }
    }

    public BackgroundDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(Background background){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, background.getUserId());
        values.put(KEY_IMAGE, background.getImage());
        values.put(KEY_TYPE, background.getType());
        values.put(KEY_STATE, background.isState());
        db.insert(TABLE_BACKGROUND, null, values);
        db.close();
    }

    public Background get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BACKGROUND, new String[]{KEY_ID, KEY_USER_ID, KEY_IMAGE, KEY_TYPE, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Background(cursor);
    }

    public List<Background> gets(){
        List<Background> backgrounds = new ArrayList<Background>();
        String query = "SELECT * FROM " + TABLE_BACKGROUND;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Background background = new Background(cursor);
                backgrounds.add(background);
            }while (cursor.moveToNext());
        }
        return backgrounds;
    }

    public List<Background> gets(User user, String type){
        List<Background> backgrounds = new ArrayList<Background>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_BACKGROUND, new String[]{KEY_ID, KEY_USER_ID, KEY_IMAGE, KEY_TYPE, KEY_STATE},
                KEY_ID + "=? AND " + KEY_TYPE + "=?", new String[]{String.valueOf(user.getId()), type}, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Background background = new Background(cursor);
                backgrounds.add(background);
            }while (cursor.moveToNext());
        }
        return backgrounds;
    }

    public int update(Background background){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, background.getUserId());
        values.put(KEY_IMAGE, background.getImage());
        values.put(KEY_TYPE, background.getType());
        values.put(KEY_STATE, background.isState());
        return db.update(TABLE_BACKGROUND, values, KEY_ID + "=?", new String[]{String.valueOf(background.getId())});
    }

    public void delete(Background background){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BACKGROUND, KEY_ID + "=?", new String[]{String.valueOf(background.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BACKGROUND, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_BACKGROUND;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

}
