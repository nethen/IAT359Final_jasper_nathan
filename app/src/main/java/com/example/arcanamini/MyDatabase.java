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

    public long insertDataMini (String name, int status, String defUpright, String defReversed)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
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

    public long insertDataTechniques (String name, String text)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.TECHNIQUE_TEXT, text);
        long id = db.insert(Constants.TECHNIQUES_TABLE, null, contentValues);
        return id;
    }
    public Cursor getMiniData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};
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

    public Cursor getTechniquesData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.TECHNIQUE_TEXT};
        Cursor cursor = db.query(Constants.TECHNIQUES_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public String getSelectedDataMinor(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};

        String selection = Constants.NAME + "='" +name+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.MINI_TABLE, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.STATUS);
            int index3 = cursor.getColumnIndex(Constants.DEF_UPRIGHT);
            int index4 = cursor.getColumnIndex(Constants.DEF_REVERSED);

            String cardName = cursor.getString(index1);
            String cardStatus = cursor.getString(index2);
            String cardDefUpright = cursor.getString(index3);
            String cardDefReversed = cursor.getString(index4);
            buffer.append(cardName + "_" + cardStatus + "_" + cardDefUpright + "_" + cardDefReversed + "\n"); //
        }
        return buffer.toString();
    }

    public String getSelectedDataMajor(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};

        String selection = Constants.NAME + "='" +name+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.MAJOR_TABLE, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.STATUS);
            int index3 = cursor.getColumnIndex(Constants.DEF_UPRIGHT);
            int index4 = cursor.getColumnIndex(Constants.DEF_REVERSED);

            String cardName = cursor.getString(index1);
            String cardStatus = cursor.getString(index2);
            String cardDefUpright = cursor.getString(index3);
            String cardDefReversed = cursor.getString(index4);
            buffer.append(cardName + "_" + cardStatus + "_" + cardDefUpright + "_" + cardDefReversed + "\n"); //
        }
        return buffer.toString();
    }

    public String getSelectedDataTechniques(String name)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.TECHNIQUE_TEXT};

        String selection = Constants.NAME + "='" +name+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TECHNIQUES_TABLE, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.TECHNIQUE_TEXT);


            String techniqueName = cursor.getString(index1);
            String techniqueText = cursor.getString(index2);
            buffer.append(techniqueName + "_" + techniqueText + "\n"); //
        }
        return buffer.toString();
    }

    public void updateMiniCard(String name, int newStatus){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS, newStatus);
        String selection = Constants.NAME + " LIKE ?";
        String[] selection_arg = {name};
        db.update(Constants.MINI_TABLE, values,selection , selection_arg);
    }

    public void updateMajorCard(String name, int newStatus){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS, newStatus);
        String selection = Constants.NAME + " LIKE ?";
        String[] selection_arg = {name};
        db.update(Constants.MAJOR_TABLE, values,selection , selection_arg);
    }
}


