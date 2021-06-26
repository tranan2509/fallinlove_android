package com.example.fallinlove.Model;

import android.database.Cursor;

public class Background {

    private int id;
    private int userId;
    private byte[] image;
    private String type;// background - heart - days
    private boolean state; //true: selected - false: unselected

    public Background() {
    }

    public Background(int userId, byte[] image, String type, boolean state) {
        this.userId = userId;
        this.image = image;
        this.type = type;
        this.state = state;
    }

    public Background(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.userId = cursor.getInt(cursor.getColumnIndex("userId"));
        this.image = cursor.getBlob(cursor.getColumnIndex("image"));
        this.type = cursor.getString(cursor.getColumnIndex("type"));
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
