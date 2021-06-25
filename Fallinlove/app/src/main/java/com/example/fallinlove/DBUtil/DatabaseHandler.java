package com.example.fallinlove.DBUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FALLINLOVE";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE USER (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "message TEXT, " +
                "lovedDate NUMERIC, " +
                "createdDate NUMERIC, " +
                "state NUMERIC)";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_PERSON_TABLE = "CREATE TABLE PERSON (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "name TEXT, " +
                "avatar BLOB, " +
                "gender NUMERIC, " +
                "dob NUMERIC, " +
                "state NUMERIC)";
        db.execSQL(CREATE_PERSON_TABLE);

        String CREATE_RESPONSIBILITY_TABLE = "CREATE TABLE RESPONSIBILITY (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "name TEXT, " +
                "date NUMERIC, " +
                "type INTEGER, " +
                "level INTEGER, " +
                "state NUMERIC)";
        db.execSQL(CREATE_RESPONSIBILITY_TABLE);

        String CREATE_ANNIVERSARY_TABLE = "CREATE TABLE ANNIVERSARY (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "name TEXT, " +
                "date NUMERIC, " +
                "description TEXT, " +
                "image BLOB, " +
                "state NUMERIC)";
        db.execSQL(CREATE_ANNIVERSARY_TABLE);

        String CREATE_RESTAURANT_TABLE = "CREATE TABLE RESTAURANT (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "name TEXT, " +
                "address TEXT, " +
                "timeStart NUMERIC, " +
                "timeEnd NUMERIC, " +
                "count INTEGER, " +
                "state NUMERIC)";
        db.execSQL(CREATE_RESTAURANT_TABLE);

        String CREATE_IMAGE_SETTING_TABLE = "CREATE TABLE IMAGE_SETTING (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "background BLOB, " +
                "heart BLOB, " +
                "days BLOB, " +
                "state NUMERIC)";
        db.execSQL(CREATE_IMAGE_SETTING_TABLE);

        String CREATE_DISPLAY_SETTING_TABLE = "CREATE TABLE DISPLAY_SETTING (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "responsibility INTEGER, " +
                "home INTEGER, " +
                "main INTEGER, " +
                "state NUMERIC)";
        db.execSQL(CREATE_DISPLAY_SETTING_TABLE);

        String CREATE_BACKGROUND_TABLE = "CREATE TABLE BACKGROUND (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId INTEGER, " +
                "image INTEGER, " +
                "type INTEGER, " +
                "state NUMERIC)";
        db.execSQL(CREATE_BACKGROUND_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS PERSON");
        db.execSQL("DROP TABLE IF EXISTS RESPONSIBILITY");
        db.execSQL("DROP TABLE IF EXISTS ANNIVERSARY");
        db.execSQL("DROP TABLE IF EXISTS RESTAURANT");
        db.execSQL("DROP TABLE IF EXISTS IMAGE_SETTING");
        db.execSQL("DROP TABLE IF EXISTS DISPLAY_SETTING");
        db.execSQL("DROP TABLE IF EXISTS BACKGROUND");
        onCreate(db);
    }
}
