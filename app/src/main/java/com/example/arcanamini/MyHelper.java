package com.example.arcanamini;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyHelper extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase db;
    private static String DB_PATH = "";
    private static String DB_NAME = "librariumdatabase.db";

    private static final String CREATE_MINOR_TABLE =
            "CREATE TABLE "+
                    Constants.MINOR_TABLE + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.NAME + " TEXT, " +
                    Constants.STATUS + " TEXT, " +
                    Constants.DEF_UPRIGHT + " TEXT, " +
                    Constants.DEF_REVERSED + " TEXT);" ;

    private static final String CREATE_MAJOR_TABLE =
            "CREATE TABLE "+
                    Constants.MAJOR_TABLE + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.NAME + " TEXT, " +
                    Constants.STATUS + " TEXT, " +
                    Constants.DEF_UPRIGHT + " TEXT, " +
                    Constants.DEF_REVERSED + " TEXT);" ;

    private static final String CREATE_TECHNIQUES_TABLE =
            "CREATE TABLE "+
                    Constants.MAJOR_TABLE + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.NAME + " TEXT, " +
                    Constants.TECHNIQUE_TEXT + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Constants.MINOR_TABLE;

    private static final String DROP_TABLE2 = "DROP TABLE IF EXISTS " + Constants.MAJOR_TABLE;

    private static final String DROP_TABLE3 = "DROP TABLE IF EXISTS " + Constants.TECHNIQUES_TABLE;
    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME)
                .toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MINOR_TABLE);


            db.execSQL(CREATE_MAJOR_TABLE);

            db.execSQL(CREATE_TECHNIQUES_TABLE);


            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            db.execSQL(DROP_TABLE2);
            db.execSQL(DROP_TABLE3);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }

}
