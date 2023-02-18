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

    public long insertData (String name, String suit, String status)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.SUIT, suit);
        contentValues.put(Constants.STATUS, status);
        long id = db.insert(Constants.MINI_TABLE, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.NAME, Constants.SUIT, Constants.STATUS};
        Cursor cursor = db.query(Constants.MINI_TABLE, columns, null, null, null, null, null);
        return cursor;
    }


    public String getSelectedData(String suit)
    {
        //select plants from database of type 'herb'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.NAME, Constants.SUIT, Constants.STATUS};

        String selection = Constants.SUIT + "='" +suit+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.MINI_TABLE, columns, selection, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {

            int index1 = cursor.getColumnIndex(Constants.NAME);
            int index2 = cursor.getColumnIndex(Constants.SUIT);
            int index3 = cursor.getColumnIndex(Constants.STATUS);
            String cardName = cursor.getString(index1);
            String cardSuit = cursor.getString(index2);
            String cardStatus = cursor.getString(index3);
            buffer.append(cardName + "," + cardSuit + "," + cardStatus + "\n"); //
        }
        return buffer.toString();
    }

}


