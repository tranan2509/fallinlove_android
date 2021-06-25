package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String message;
    private String lovedDate;
    private String createdDate;
    private boolean state;

    public User() {
    }

    public User(String lovedDate, String createdDate, boolean state) {
        this.lovedDate = lovedDate;
        this.createdDate = createdDate;
        this.state = state;
    }

    public User(String message, String lovedDate, String createdDate, boolean state) {
        this.message = message;
        this.lovedDate = lovedDate;
        this.createdDate = createdDate;
        this.state = state;
    }

    public User(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.message = cursor.getString(cursor.getColumnIndex("message"));
        this.lovedDate = cursor.getString(cursor.getColumnIndex("lovedDate"));
        this.createdDate = cursor.getString(cursor.getColumnIndex("createdDate"));
        this.state = cursor.getString(cursor.getColumnIndex("state")).equals("1");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLovedDate() {
        return lovedDate;
    }

    public void setLovedDate(String lovedDate) {
        this.lovedDate = lovedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
