package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Restaurant;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDB extends DatabaseHandler {

    private static final String TABLE_RESTAURANT = "RESTAURANT";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_TIME_START = "timeStart";
    private static final String KEY_TIME_END = "timeEnd";
    private static final String KEY_COUNT = "count";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static RestaurantDB instance;

    public static RestaurantDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new RestaurantDB(context);
            }
            return instance;
        }
    }

    public RestaurantDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, restaurant.getUserId());
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_ADDRESS, restaurant.getAddress());
        values.put(KEY_TIME_START, restaurant.getTimeStart());
        values.put(KEY_TIME_END, restaurant.getTimeEnd());
        values.put(KEY_COUNT, restaurant.getCount());
        values.put(KEY_STATE, restaurant.isState());

        db.insert(TABLE_RESTAURANT, null, values);
        db.close();
    }

    public Restaurant get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTAURANT, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_ADDRESS, KEY_TIME_START, KEY_TIME_END, KEY_COUNT, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Restaurant(cursor);
    }

    public Restaurant get(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESTAURANT, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_ADDRESS, KEY_TIME_START, KEY_TIME_END, KEY_COUNT, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Restaurant(cursor);
    }

    public List<Restaurant> gets(){
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        String query = "SELECT * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Restaurant restaurant = new Restaurant(cursor);
                restaurants.add(restaurant);
            }while (cursor.moveToNext());
        }
        return restaurants;
    }

    public List<Restaurant> gets(User user){
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        String query = "SELECT * FROM " + TABLE_RESTAURANT + " WHERE " + KEY_USER_ID + "=" + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Restaurant restaurant = new Restaurant(cursor);
                restaurants.add(restaurant);
            }while (cursor.moveToNext());
        }
        return restaurants;
    }

    public int update(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, restaurant.getUserId());
        values.put(KEY_NAME, restaurant.getName());
        values.put(KEY_ADDRESS, restaurant.getAddress());
        values.put(KEY_TIME_START, restaurant.getTimeStart());
        values.put(KEY_TIME_END, restaurant.getTimeEnd());
        values.put(KEY_COUNT, restaurant.getCount());
        values.put(KEY_STATE, restaurant.isState());
        return db.update(TABLE_RESTAURANT, values, KEY_ID + "=?", new String[]{String.valueOf(restaurant.getId())});
    }

    public void delete(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT, KEY_ID + "=?", new String[]{String.valueOf(restaurant.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANT, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_RESTAURANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

}
