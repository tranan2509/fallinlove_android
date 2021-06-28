package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private int userId;
    private String password;
    private boolean state;

    public Account() {
    }

    public Account(int userId, String password, boolean state) {
        this.userId = userId;
        this.password = password;
        this.state = state;
    }

    public Account(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.password = cursor.getString(cursor.getColumnIndex("password"));
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
