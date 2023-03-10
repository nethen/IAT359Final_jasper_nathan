package com.example.arcanamini;

import android.content.ContentValues;
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

//    private static final String SQL_CREATE_ENTRIES =
//            "CREATE TABLE " + Constants.READING_TABLE + " (" +
//                    "_id" + " INTEGER PRIMARY KEY," +
////                    Constants.READING_OCCURED + " TEXT," +
////                    Constants.READING_TYPE+ " TEXT," +
//                    Constants.READING_OCCURED + "TEXT)";

    private static final String CREATE_TABLE =
            "CREATE TABLE "+
                    Constants.REFLECTION_TABLE + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.REFLECTION_OCCURED + " TEXT," +
                    Constants.REFLECTION_CONTENT + " TEXT);" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constants.REFLECTION_TABLE;

    public ReadDatabaseHelper(Context context){
        super(context, Constants.DBREFFLECTION_NAME, null, Constants.DATABASE_VERSION);
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

    public void openDatabase() {
        String DBPath = mContext.getDatabasePath(Constants.DBREFFLECTION_NAME).getPath();
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
        Cursor cursor = mDatabase.rawQuery("select * from "+Constants.REFLECTION_TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for reflection time
            s = new String(cursor.getString(1));
            arraylistString.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arraylistString;
    }

    public ArrayList<String> getItemsWithContent() {
        String time = null;
        String content = null;
        ArrayList<String> arraylistString = new ArrayList<String>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from "+Constants.REFLECTION_TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for reflection time
            time = new String(cursor.getString(1));
            content = new String(cursor.getString(2));
            //index 2 for reflection content
            arraylistString.add(time + "~" + content);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arraylistString;
    }

    public int getLength(){
        String s = null;
        ArrayList<String> arraylistString = new ArrayList<String>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from "+Constants.REFLECTION_TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for reflection time
            s = new String(cursor.getString(1));
            arraylistString.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        int len = arraylistString.size();
        return len;
    }
    public void updateContent(String name, String newContent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.REFLECTION_CONTENT, newContent);
        String selection = Constants.UID + " LIKE ?";
        String[] selection_arg = {name};
        db.update(Constants.REFLECTION_TABLE, values,selection , selection_arg);
    }

    public long insertDataReflection (String date, String content)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.REFLECTION_OCCURED, date);
        contentValues.put(Constants.REFLECTION_CONTENT, content);
        long id = db.insert(Constants.REFLECTION_TABLE, null, contentValues);
        return id;
    }


}
