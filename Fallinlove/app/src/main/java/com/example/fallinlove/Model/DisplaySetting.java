package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class DisplaySetting implements Serializable {

    private int id;
    private int userId;
    private int responsibility;
    private int home;
    private int main;
    private boolean state;

    public DisplaySetting() {
    }

    public DisplaySetting(int userId, int responsibility, int home, int main) {
        this.userId = userId;
        this.responsibility = responsibility;
        this.home = home;
        this.main = main;
        this.state = true;
    }

    public DisplaySetting(int userId, int responsibility, int home, int main, boolean state) {
        this.userId = userId;
        this.responsibility = responsibility;
        this.home = home;
        this.main = main;
        this.state = state;
    }

    public DisplaySetting(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.responsibility = cursor.getInt(cursor.getColumnIndex("responsibility"));
        this.home = cursor.getInt(cursor.getColumnIndex("home"));
        this.main = cursor.getInt(cursor.getColumnIndex("main"));
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

    public int getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(int responsibility) {
        this.responsibility = responsibility;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
