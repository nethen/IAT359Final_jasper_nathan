package com.example.arcanamini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {
    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase (Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertDataMini (String name, String suit, int status, String defUpright, String defReversed)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.SUIT, suit);
        contentValues.put(Constants.STATUS, status);
        contentValues.put(Constants.DEF_UPRIGHT, defUpright);
        contentValues.put(Constants.DEF_REVERSED, defReversed);
        long id = db.insert(Constants.MINI_TABLE, null, contentValues);
        return id;
    }

    public long insertDataMajor (String name, int status, String defUpright, String defReversed)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.STATUS, status);
        contentValues.put(Constants.DEF_UPRIGHT, defUpright);
        contentValues.put(Constants.DEF_REVERSED, defReversed);
        long id = db.insert(Constants.MAJOR_TABLE, null, contentValues);
        return id;
    }

    public Cursor getMiniData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.SUIT, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};
        Cursor cursor = db.query(Constants.MINI_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getMajorData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};
        Cursor cursor = db.query(Constants.MAJOR_TABLE, columns, null, null, null, null, null);
        return cursor;
    }
}


