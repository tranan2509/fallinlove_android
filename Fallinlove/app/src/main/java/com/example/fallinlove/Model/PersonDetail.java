package com.example.fallinlove.Model;

import android.database.Cursor;

import java.io.Serializable;

public class PersonDetail implements Serializable {

    private int id;
    private int personId;
    private String name;
    private String description;
    private boolean state;

    public PersonDetail() {
    }

    public PersonDetail(int personId, String name, String description, boolean state) {
        this.personId = personId;
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public PersonDetail(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.personId = cursor.getInt(cursor.getColumnIndex("personId"));
        this.name = cursor.getString(cursor.getColumnIndex("name"));
        this.description = cursor.getString(cursor.getColumnIndex("description"));
        this.state = cursor.getString(cursor.getColumnIndex("state")).equals("1");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
