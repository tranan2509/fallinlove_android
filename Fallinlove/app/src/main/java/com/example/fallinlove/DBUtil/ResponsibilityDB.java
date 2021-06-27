package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Responsibility;
import com.example.fallinlove.Model.User;
import com.example.fallinlove.Provider.DateProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ResponsibilityDB extends DatabaseHandler{

    private static final String TABLE_RESPONSIBILITY = "RESPONSIBILITY";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_TYPE = "type";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static ResponsibilityDB instance;


    public static ResponsibilityDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new ResponsibilityDB(context);
            }
            return instance;
        }
    }

    public ResponsibilityDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(Responsibility responsibility){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, responsibility.getUserId());
        values.put(KEY_NAME, responsibility.getName());
        values.put(KEY_DATE, responsibility.getDate());
        values.put(KEY_TYPE, responsibility.getType());
        values.put(KEY_LEVEL, responsibility.getLevel());
        values.put(KEY_STATE, responsibility.isState());

        db.insert(TABLE_RESPONSIBILITY, null, values);
        db.close();
    }

    public Responsibility get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESPONSIBILITY, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_DATE, KEY_TYPE, KEY_LEVEL, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Responsibility(cursor);
    }

    public Responsibility get(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESPONSIBILITY, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_DATE, KEY_TYPE, KEY_LEVEL, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Responsibility(cursor);
    }

    public List<Responsibility> gets(){
        List<Responsibility> responsibilities = new ArrayList<Responsibility>();
        String query = "SELECT * FROM " + TABLE_RESPONSIBILITY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Responsibility responsibility = new Responsibility(cursor);
                responsibilities.add(responsibility);
            }while (cursor.moveToNext());
        }
        return responsibilities;
    }

    public List<Responsibility> gets(User user){
        List<Responsibility> responsibilities = new ArrayList<Responsibility>();
        String query = "SELECT * FROM " + TABLE_RESPONSIBILITY + " WHERE " + KEY_USER_ID + "=" + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Responsibility responsibility = new Responsibility(cursor);
                responsibilities.add(responsibility);
            }while (cursor.moveToNext());
        }
        return responsibilities;
    }

    public List<Responsibility> gets(User user, int type){
        List<Responsibility> responsibilities = new ArrayList<Responsibility>();
        String query = "SELECT * FROM " + TABLE_RESPONSIBILITY + " WHERE " + KEY_USER_ID + "=" + user.getId() + " AND " + KEY_TYPE  + "=" + type + " ORDER BY datetime(date) ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Responsibility responsibility = new Responsibility(cursor);
                responsibilities.add(responsibility);
            }while (cursor.moveToNext());
        }
        return responsibilities;
    }

    public List<Responsibility> getsSorted(User user, int type){
        List<Responsibility> responsibilities = gets(user, type);
        List<Responsibility> outOfDate_unchecked = new ArrayList<Responsibility>();
        List<Responsibility> expiryDate_unchecked = new ArrayList<Responsibility>();
        List<Responsibility> outOfDate_checked = new ArrayList<Responsibility>();
        List<Responsibility> expiryDate_checked = new ArrayList<Responsibility>();

        for (Responsibility responsibility: responsibilities){
            Date date = null;
            try {
                date = DateProvider.datetimeFormat.parse(responsibility.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (date.getTime() > cal.getTime().getTime()){
                if (!responsibility.isState()){
                    outOfDate_unchecked.add(responsibility);
                }else{
                    outOfDate_checked.add(responsibility);
                }
            }else{
                if (!responsibility.isState()){
                    expiryDate_unchecked.add(responsibility);
                }else{
                    expiryDate_checked.add(responsibility);
                }
            }
        }
        List<Responsibility> newResponsibilities = new ArrayList<Responsibility>(outOfDate_unchecked);
        newResponsibilities.addAll(expiryDate_unchecked);
        newResponsibilities.addAll(outOfDate_checked);
        newResponsibilities.addAll(expiryDate_checked);
        return newResponsibilities;
    }

    public int update(Responsibility responsibility){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, responsibility.getUserId());
        values.put(KEY_NAME, responsibility.getName());
        values.put(KEY_DATE, responsibility.getDate());
        values.put(KEY_TYPE, responsibility.getType());
        values.put(KEY_LEVEL, responsibility.getLevel());
        values.put(KEY_STATE, responsibility.isState());
        return db.update(TABLE_RESPONSIBILITY, values, KEY_ID + "=?", new String[]{String.valueOf(responsibility.getId())});
    }

    public void updateDaily(Responsibility responsibility){
        Calendar cal = Calendar.getInstance();
        String now = DateProvider.datetimeFormat.format(cal.getTime());
        String newDate = now.split(" ")[0] + " " + responsibility.getDate().split(" ")[1];
        responsibility.setDate(newDate);
        update(responsibility);
    }

    public void updateAllDaily(User user){
        List<Responsibility> responsibilities = gets(user);
        if (responsibilities != null && responsibilities.size() > 0){
            Calendar cal = Calendar.getInstance();
            String now = DateProvider.dateFormat.format(cal.getTime());
            Responsibility responsibility = responsibilities.get(0);
            String date = responsibility.getDate().split(" ")[0];
            try {
                if (DateProvider.dateFormat.parse(now).getTime() > DateProvider.dateFormat.parse(date).getTime()){
                    List<Responsibility> responsibilitiesDailies = gets(user, 1);
                    for (Responsibility responsibilityDaily: responsibilitiesDailies){
                        responsibilityDaily.setState(false);
                        updateDaily(responsibilityDaily);
                    }
                }
            }catch (Exception ex){

            }
        }
    }

    public void delete(Responsibility responsibility){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESPONSIBILITY, KEY_ID + "=?", new String[]{String.valueOf(responsibility.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESPONSIBILITY, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_RESPONSIBILITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

}
