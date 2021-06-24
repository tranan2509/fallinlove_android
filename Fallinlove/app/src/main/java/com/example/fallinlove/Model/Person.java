package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class Person implements Serializable {

    private int id;
    private int userId;
    private String name;
    private byte[] avatar;
    private boolean gender;
    private String dob;
    private boolean state;

    public Person() {
    }

    public Person(int userId, String name, byte[] avatar, boolean gender, String dob, boolean state) {
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.dob = dob;
        this.state = state;
    }

    public Person(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.avatar = cursor.getBlob(cursor.getColumnIndex("avatar"));
        this.gender = cursor.getString(cursor.getColumnIndex("gender")).equals("1");
        this.dob = cursor.getString(cursor.getColumnIndex("dob"));
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
