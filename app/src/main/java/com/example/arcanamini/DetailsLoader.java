package com.example.arcanamini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DetailsLoader {
    private SQLiteDatabase db;
    private Context context;
    private final DatabaseHelper helper;
    String inpTable;

    public DetailsLoader (Context c, int table){
        context = c;
        inpTable = new String();

        switch (table){
            case 0:
                inpTable = Constants.MAJOR_TABLE;
                break;
            case 1:
                inpTable = Constants.MINOR_TABLE;
                break;
            case 2:
                inpTable = Constants.TECHNIQUES_TABLE;
                break;
        }
        helper = new DatabaseHelper(context, "librariumdatabase", inpTable);
    }

    public long insertDataMinor (String name, int status, String defUpright, String defReversed)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.STATUS, status);
        contentValues.put(Constants.DEF_UPRIGHT, defUpright);
        contentValues.put(Constants.DEF_REVERSED, defReversed);
        long id = db.insert(Constants.MINOR_TABLE, null, contentValues);
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
    public Cursor getMinorData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};
        Cursor cursor = db.query(Constants.MINOR_TABLE, columns, null, null, null, null, null);
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
        Cursor cursor = db.query(Constants.MINOR_TABLE, columns, selection, null, null, null, null);

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

    public void updateMinorCard(String name, int newStatus){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS, newStatus);
        String selection = Constants.NAME + " LIKE ?";
        String[] selection_arg = {name};
        db.update(Constants.MINOR_TABLE, values,selection , selection_arg);
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
