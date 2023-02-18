package com.example.arcanamini;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_MINI_TABLE =
            "CREATE TABLE "+
                    Constants.MINI_TABLE + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.NAME + " TEXT, " +
                    Constants.SUIT + " TEXT, " +
                    Constants.STATUS + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Constants.MINI_TABLE;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_MINI_TABLE);

            // Initialize the db with values from my_array.xml
            final int DEFAULT_THIRD_COLUMN = 1;
            final String DEFAULT_FOURTH_COLUMN = "";
            ContentValues values = new ContentValues();
            Resources res = context.getResources();
            String[] myArray = res.getStringArray(R.array.my_array);
            for (String item : myArray){
                values.put(Constants.NAME, item);
                values.put(Constants.SUIT, DEFAULT_THIRD_COLUMN);
                values.put(Constants.STATUS, DEFAULT_FOURTH_COLUMN);
                db.insert(Constants.MINI_TABLE, null, values);
            }

            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}
