package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class Responsibility implements Serializable {

    public static int TYPE_DAILY = 1;
    public static int TYPE_RESPONSIBILITY = 2;

    private int id;
    private int userId;
    private String name;
    private String date;
    private int type; //1 daily - 2 responsibility
    private int level; //1 normal -2 important - 3 very important
    private boolean state;

    public Responsibility() {
    }

    public Responsibility(int userId, String name, String date, int type, int level, boolean state) {
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.type = type;
        this.level = level;
        this.state = state;
    }

    public Responsibility(int id, int userId, String name, String date, int type, int level, boolean state) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.type = type;
        this.level = level;
        this.state = state;
    }

    public Responsibility(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.date = cursor.getString(cursor.getColumnIndex("date"));
        this.type = cursor.getInt(cursor.getColumnIndex("type"));
        this.level = cursor.getInt(cursor.getColumnIndex("level"));
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
