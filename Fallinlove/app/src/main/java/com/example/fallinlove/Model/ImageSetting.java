package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class ImageSetting implements Serializable {

    private int id;
    private int userId;
    private byte[] background;
    private byte[] heart;
    private byte[] days;
    private boolean state;

    public ImageSetting() {
    }

    public ImageSetting(int userId, byte[] background, byte[] heart, byte[] days, boolean state) {
        this.userId = userId;
        this.background = background;
        this.heart = heart;
        this.days = days;
        this.state = state;
    }

    public ImageSetting(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.background = cursor.getBlob(cursor.getColumnIndex("background"));
        this.heart = cursor.getBlob(cursor.getColumnIndex("heart"));
        this.days = cursor.getBlob(cursor.getColumnIndex("days"));
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

    public byte[] getBackground() {
        return background;
    }

    public void setBackground(byte[] background) {
        this.background = background;
    }

    public byte[] getHeart() {
        return heart;
    }

    public void setHeart(byte[] heart) {
        this.heart = heart;
    }

    public byte[] getDays() {
        return days;
    }

    public void setDays(byte[] days) {
        this.days = days;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
