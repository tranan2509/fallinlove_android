package com.example.fallinlove.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fallinlove.Model.Account;
import com.example.fallinlove.Model.User;

import java.util.ArrayList;
import java.util.List;

public class AccountDB extends DatabaseHandler{

    private static final String TABLE_ACCOUNT = "ACCOUNT";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_STATE = "state";

    private Context context;
    private static final Object lock = new Object();
    private static AccountDB instance;


    public static AccountDB getInstance(Context context){
        synchronized (lock){
            if (instance == null){
                instance = new AccountDB(context);
            }
            return instance;
        }
    }

    public AccountDB(Context context){
        super(context);
        this.context = context;
    }

    public void add(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, account.getUserId());
        values.put(KEY_PASSWORD, account.getPassword());
        values.put(KEY_STATE, account.isState());
        db.insert(TABLE_ACCOUNT, null, values);
        db.close();
    }

    public Account get(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, new String[]{KEY_ID, KEY_USER_ID, KEY_PASSWORD, KEY_STATE},
                KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Account(cursor);
    }

    public Account get(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACCOUNT, new String[]{KEY_ID, KEY_USER_ID, KEY_PASSWORD, KEY_STATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(user.getId())}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;
        return new Account(cursor);
    }

    public List<Account> gets(){
        List<Account> accounts = new ArrayList<Account>();
        String query = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Account account = new Account(cursor);
                accounts.add(account);
            }while (cursor.moveToNext());
        }
        return accounts;
    }

    public int update(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, account.getUserId());
        values.put(KEY_PASSWORD, account.getPassword());
        values.put(KEY_STATE, account.isState());
        return db.update(TABLE_ACCOUNT, values, KEY_ID + "=?", new String[]{String.valueOf(account.getId())});
    }

    public void delete(Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, KEY_ID + "=?", new String[]{String.valueOf(account.getId())});
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ACCOUNT, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getCount();
    }

}
