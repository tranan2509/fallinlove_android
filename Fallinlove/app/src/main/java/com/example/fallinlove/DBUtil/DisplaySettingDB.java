package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.DisplaySetting;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DisplaySettingDB extends DatabaseHandler{

    private static final String TABLE_DISPLAY_SETTING = "DISPLAY_SETTING";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_RESPONSIBILITY = "responsibility";
    private static final String KEY_HOME = "home";
    private static final String KEY_MAIN = "main";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static DisplaySettingDB instance;

    public static DisplaySettingDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new DisplaySettingDB(context);
            }
            return instance;
        }
    }

    public DisplaySettingDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(DisplaySetting displaySetting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, displaySetting.getUserId());
        values.put(KEY_RESPONSIBILITY, displaySetting.getResponsibility());
        values.put(KEY_HOME, displaySetting.getHome());
        values.put(KEY_MAIN, displaySetting.getMain());
        values.put(KEY_STATE, displaySetting.isState());

        db.insert(TABLE_DISPLAY_SETTING, null, values);
        db.close();
    }

    public DisplaySetting get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISPLAY_SETTING, new String[]{KEY_ID, KEY_USER_ID, KEY_RESPONSIBILITY, KEY_HOME, KEY_MAIN, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new DisplaySetting(cursor);
    }

    public DisplaySetting get(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISPLAY_SETTING, new String[]{KEY_ID, KEY_USER_ID, KEY_RESPONSIBILITY, KEY_HOME, KEY_MAIN, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new DisplaySetting(cursor);
    }

    public List<DisplaySetting> gets(){
        List<DisplaySetting> displaySettings = new ArrayList<DisplaySetting>();
        String query = "SELECT * FROM " + TABLE_DISPLAY_SETTING;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                DisplaySetting displaySetting = new DisplaySetting(cursor);
                displaySettings.add(displaySetting);
            }while (cursor.moveToNext());
        }
        return displaySettings;
    }

    public List<DisplaySetting> gets(User user){
        List<DisplaySetting> displaySettings = new ArrayList<DisplaySetting>();
        String query = "SELECT * FROM " + TABLE_DISPLAY_SETTING + " WHERE " + KEY_USER_ID + "=" + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                DisplaySetting displaySetting = new DisplaySetting(cursor);
                displaySettings.add(displaySetting);
            }while (cursor.moveToNext());
        }
        return displaySettings;
    }

    public int update(DisplaySetting displaySetting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, displaySetting.getUserId());
        values.put(KEY_RESPONSIBILITY, displaySetting.getResponsibility());
        values.put(KEY_HOME, displaySetting.getHome());
        values.put(KEY_MAIN, displaySetting.getMain());
        values.put(KEY_STATE, displaySetting.isState());
        return db.update(TABLE_DISPLAY_SETTING, values, KEY_ID + "=?", new String[]{String.valueOf(displaySetting.getId())});
    }

    public void delete(DisplaySetting displaySetting){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAY_SETTING, KEY_ID + "=?", new String[]{String.valueOf(displaySetting.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISPLAY_SETTING, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_DISPLAY_SETTING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
