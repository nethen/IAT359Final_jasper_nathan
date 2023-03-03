package com.example.arcanamini;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DBNAME;
    public static String TABLE;
    public static String DBLOCATION;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public DatabaseHelper(Context context, String DBname, String table){
        super(context, DBNAME, null, 1);
        DBNAME = DBname+".db";
        TABLE = table;
        DBLOCATION = "/data/data/" + context.getPackageName()+"/databases";
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String DBPath = mContext.getDatabasePath(DBNAME).getPath();
        Log.i("Open", DBPath);
        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(DBPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void closeDatabase(){
        if (mDatabase != null){
            mDatabase.close();
        }
    }

    public ArrayList<String> getItems() {
        String s = null;
        ArrayList<String> arraylistString = new ArrayList<String>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from "+TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for card name
            s = new String(cursor.getString(1));
            arraylistString.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arraylistString;
    }
}
