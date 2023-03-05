package com.example.arcanamini;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;


public class ReadDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Constants.READING_TABLE + " (" +
                    "_id" + " INTEGER PRIMARY KEY," +
//                    Constants.READING_OCCURED + " TEXT," +
//                    Constants.READING_TYPE+ " TEXT," +
                    Constants.READING_OCCURED + "TEXT)";

    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    Constants.READING_TABLE + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.READING_OCCURED + " TEXT);" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constants.READING_TABLE;

    public static String DBNAME = "readings.db";
    public static final int DBVERSION = 2;


    public ReadDatabaseHelper(Context context){
        super(context, DBNAME, null, DBVERSION);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);

            Toast.makeText(mContext, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(mContext, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
            Toast.makeText(mContext, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(mContext, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.READING_OCCURED};
        Cursor cursor = db.query(Constants.READING_TABLE, columns, null, null, null, null, null);
        return cursor;
    }


}
