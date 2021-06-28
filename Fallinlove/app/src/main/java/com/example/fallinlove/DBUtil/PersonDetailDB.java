package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Person;
import com.example.fallinlove.Model.PersonDetail;

import java.util.ArrayList;
import java.util.List;

public class PersonDetailDB extends DatabaseHandler{

    private static final String TABLE_PERSON_DETAIL = "PERSON_DETAIL";
    private static final String KEY_ID = "id";
    private static final String KEY_PERSON_ID = "personId";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static PersonDetailDB instance;


    public static PersonDetailDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new PersonDetailDB(context);
            }
            return instance;
        }
    }

    public PersonDetailDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(PersonDetail personDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_ID, personDetail.getPersonId());
        values.put(KEY_NAME, personDetail.getName());
        values.put(KEY_DESCRIPTION, personDetail.getDescription());
        values.put(KEY_STATE, personDetail.isState());
        db.insert(TABLE_PERSON_DETAIL, null, values);
        db.close();
    }

    public PersonDetail get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON_DETAIL, new String[]{KEY_ID, KEY_PERSON_ID, KEY_NAME, KEY_DESCRIPTION, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new PersonDetail(cursor);
    }

    public PersonDetail get(Person person){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON_DETAIL, new String[]{KEY_ID, KEY_PERSON_ID, KEY_NAME, KEY_DESCRIPTION, KEY_STATE},
                KEY_PERSON_ID + "=?", new String[]{String.valueOf(person.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new PersonDetail(cursor);
    }

    public List<PersonDetail> gets(){
        List<PersonDetail> personDetails = new ArrayList<PersonDetail>();
        String query = "SELECT * FROM " + TABLE_PERSON_DETAIL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                PersonDetail personDetail = new PersonDetail(cursor);
                personDetails.add(personDetail);
            }while (cursor.moveToNext());
        }
        return personDetails;
    }

    public List<PersonDetail> gets(Person person){
        List<PersonDetail> personDetails = new ArrayList<PersonDetail>();
        String query = "SELECT * FROM " + TABLE_PERSON_DETAIL + " WHERE " + KEY_PERSON_ID + "=" + person.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                PersonDetail personDetail = new PersonDetail(cursor);
                personDetails.add(personDetail);
            }while (cursor.moveToNext());
        }
        return personDetails;
    }

    public int update(PersonDetail personDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_ID, personDetail.getPersonId());
        values.put(KEY_NAME, personDetail.getName());
        values.put(KEY_DESCRIPTION, personDetail.getDescription());
        values.put(KEY_STATE, personDetail.isState());
        return db.update(TABLE_PERSON_DETAIL, values, KEY_ID + "=?", new String[]{String.valueOf(personDetail.getId())});
    }

    public void delete(PersonDetail personDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSON_DETAIL, KEY_ID + "=?", new String[]{String.valueOf(personDetail.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSON_DETAIL, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_PERSON_DETAIL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }


}
