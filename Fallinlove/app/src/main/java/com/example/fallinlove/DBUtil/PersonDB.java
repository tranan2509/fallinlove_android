package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class PersonDB extends DatabaseHandler{

    private static final String TABLE_PERSON = "PERSON";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_NAME = "name";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DOB = "dob";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static PersonDB instance;


    public static PersonDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new PersonDB(context);
            }
            return instance;
        }
    }

    public PersonDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, person.getUserId());
        values.put(KEY_NAME, person.getName());
        values.put(KEY_AVATAR, person.getAvatar());
        values.put(KEY_GENDER, person.isGender());
        values.put(KEY_DOB, person.getDob());
        values.put(KEY_STATE, person.isState());

        db.insert(TABLE_PERSON, null, values);
        db.close();
    }

    public Person get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_AVATAR, KEY_GENDER, KEY_DOB, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Person(cursor);
    }

    public Person get(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, new String[]{KEY_ID, KEY_USER_ID, KEY_NAME, KEY_AVATAR, KEY_GENDER, KEY_DOB, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Person(cursor);
    }

    public List<Person> gets(){
        List<Person> persons = new ArrayList<Person>();
        String query = "SELECT * FROM " + TABLE_PERSON;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Person person = new Person(cursor);
                persons.add(person);
            }while (cursor.moveToNext());
        }
        return persons;
    }

    public List<Person> gets(User user){
        List<Person> persons = new ArrayList<Person>();
        String query = "SELECT * FROM " + TABLE_PERSON + " WHERE " + KEY_USER_ID + "=" + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Person person = new Person(cursor);
                persons.add(person);
            }while (cursor.moveToNext());
        }
        return persons;
    }

    public int update(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, person.getUserId());
        values.put(KEY_NAME, person.getName());
        values.put(KEY_AVATAR, person.getAvatar());
        values.put(KEY_GENDER, person.isGender());
        values.put(KEY_DOB, person.getDob());
        values.put(KEY_STATE, person.isState());
        return db.update(TABLE_PERSON, values, KEY_ID + "=?", new String[]{String.valueOf(person.getId())});
    }

    public void delete(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSON, KEY_ID + "=?", new String[]{String.valueOf(person.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSON, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_PERSON;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }
}
