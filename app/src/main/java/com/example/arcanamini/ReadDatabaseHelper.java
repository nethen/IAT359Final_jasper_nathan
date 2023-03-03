package com.example.arcanamini;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class ReadDatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constants.READING_TABLE + " (" +
                    "_id" + " INTEGER PRIMARY KEY," +
                    Constants.READING_OCCURED + " TEXT," +
                    Constants.READING_TYPE+ " TEXT," +
                    Constants.READING_CARDS + "TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constants.READING_TABLE;
    public static String DBNAME = "readings.db";
    public static final int DBVERSION = 1;
    public static String TABLE;
    public static String DBLOCATION;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public ReadDatabaseHelper(Context context){
        super(context, DBNAME, null, DBVERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
