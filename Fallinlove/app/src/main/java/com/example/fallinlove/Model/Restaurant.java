package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private int id;
    private int userId;
    private String name;
    private String address;
    private String timeStart;
    private String timeEnd;
    private int count;
    private boolean state;

    public Restaurant() {
    }

    public Restaurant(int userId, String name, String address, String timeStart, String timeEnd, int count, boolean state) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.count = count;
        this.state = state;
    }

    public Restaurant(int id, int userId, String name, String address, String timeStart, String timeEnd, int count, boolean state) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.count = count;
        this.state = state;
    }

    public Restaurant(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.address = cursor.getString(cursor.getColumnIndex("address"));
        this.timeStart = cursor.getString(cursor.getColumnIndex("timeStart"));
        this.timeEnd = cursor.getString(cursor.getColumnIndex("timeEnd"));
        this.count = cursor.getInt(cursor.getColumnIndex("count"));
        this.state = cursor.getString(cursor.getColumnIndex("state")).equals("1");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
