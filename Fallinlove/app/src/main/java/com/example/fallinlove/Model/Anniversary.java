package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class Anniversary implements Serializable {

    private int id;
    private int userId;
    private String name;
    private String date;
    private String description;
    private byte[] image;
    private boolean state;

    public Anniversary() {
    }

    public Anniversary(int userId, String name, String date, String description, byte[] image, boolean state) {
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.description = description;
        this.image = image;
        this.state = state;
    }

    public Anniversary(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.date = cursor.getString(cursor.getColumnIndex("date"));
        this.description = cursor.getString(cursor.getColumnIndex("description"));
        this.image = cursor.getBlob(cursor.getColumnIndex("image"));
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
