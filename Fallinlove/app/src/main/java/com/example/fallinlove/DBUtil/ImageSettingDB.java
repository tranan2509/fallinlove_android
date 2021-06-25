package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.ImageSetting;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class ImageSettingDB extends DatabaseHandler{

    private static final String TABLE_IMAGE_SETTING = "IMAGE_SETTING";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_BACKGROUND = "background";
    private static final String KEY_HEART = "heart";
    private static final String KEY_DAYS = "days";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static ImageSettingDB instance;

    public static ImageSettingDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new ImageSettingDB(context);
            }
            return instance;
        }
    }

    public ImageSettingDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(ImageSetting imageSetting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, imageSetting.getUserId());
        values.put(KEY_BACKGROUND, imageSetting.getBackground());
        values.put(KEY_HEART, imageSetting.getHeart());
        values.put(KEY_DAYS, imageSetting.getDays());
        values.put(KEY_STATE, imageSetting.isState());

        db.insert(TABLE_IMAGE_SETTING, null, values);
        db.close();
    }

    public ImageSetting get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMAGE_SETTING, new String[]{KEY_ID, KEY_USER_ID, KEY_BACKGROUND, KEY_HEART, KEY_DAYS, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new ImageSetting(cursor);
    }

    public ImageSetting get(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMAGE_SETTING, new String[]{KEY_ID, KEY_USER_ID, KEY_BACKGROUND, KEY_HEART, KEY_DAYS, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new ImageSetting(cursor);
    }

    public List<ImageSetting> gets(){
        List<ImageSetting> imageSettings = new ArrayList<ImageSetting>();
        String query = "SELECT * FROM " + TABLE_IMAGE_SETTING;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                ImageSetting imageSetting = new ImageSetting(cursor);
                imageSettings.add(imageSetting);
            }while (cursor.moveToNext());
        }
        return imageSettings;
    }

    public List<ImageSetting> gets(User user){
        List<ImageSetting> imageSettings = new ArrayList<ImageSetting>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_IMAGE_SETTING, new String[]{KEY_ID, KEY_USER_ID, KEY_BACKGROUND, KEY_HEART, KEY_DAYS, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                ImageSetting imageSetting = new ImageSetting(cursor);
                imageSettings.add(imageSetting);
            }while (cursor.moveToNext());
        }
        return imageSettings;
    }

    public int update(ImageSetting imageSetting){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, imageSetting.getUserId());
        values.put(KEY_BACKGROUND, imageSetting.getBackground());
        values.put(KEY_HEART, imageSetting.getHeart());
        values.put(KEY_DAYS, imageSetting.getDays());
        values.put(KEY_STATE, imageSetting.isState());
        return db.update(TABLE_IMAGE_SETTING, values, KEY_ID + "=?", new String[]{String.valueOf(imageSetting.getId())});
    }

    public void delete(ImageSetting imageSetting){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE_SETTING, KEY_ID + "=?", new String[]{String.valueOf(imageSetting.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGE_SETTING, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_IMAGE_SETTING;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

}
