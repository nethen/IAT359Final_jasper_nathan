package com.example.arcanamini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class ReadDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    String date;
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
                    Constants.REFLECTION_TIME + " TEXT," +
                    Constants.REFLECTION_CONTENT + " TEXT);" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Constants.REFLECTION_TABLE;

    public ReadDatabaseHelper(Context context, String date){
        super(context, Constants.DBREFFLECTION_NAME, null, Constants.DATABASE_VERSION);
        this.mContext = context;
        this.date = date;
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
        Cursor cursor = mDatabase.rawQuery("select * from "+Constants.REFLECTION_TABLE+" where "+Constants.REFLECTION_OCCURED+" = ? ", new String[] {date});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for reflection time
            s = new String(cursor.getString(2));
            arraylistString.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arraylistString;
    }

    public ArrayList<String> getDates() {
        String s = null;
        ArrayList<String> arraylistString = new ArrayList<String>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select distinct "+Constants.REFLECTION_OCCURED+" from "+Constants.REFLECTION_TABLE, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for reflection time
            s = new String(cursor.getString(0));
            arraylistString.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arraylistString;
    }

    public LocalDate getOldestDate(){
        ArrayList<String> dates = getDates();
        LocalDate dateNow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        for (String date : dates){
            LocalDate localDate = LocalDate.parse(date, formatter);
            if (localDate.isBefore(dateNow)) dateNow = localDate;

        }
        return dateNow;
    }

    public ArrayList<String> getItemsWithContent() {
        String time = null;
        String content = null;
        ArrayList<String> arraylistString = new ArrayList<String>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from "+Constants.REFLECTION_TABLE+" where "+Constants.REFLECTION_OCCURED+" = ? ", new String[] {date});
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
    public String getId(int position) {
        String id = null;
        ArrayList<String> arraylistString = new ArrayList<String>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from "+Constants.REFLECTION_TABLE+" where "+Constants.REFLECTION_OCCURED+" = ? ", new String[] {date});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            //Index 1 for reflection time
            id = new String(cursor.getString(0));
            arraylistString.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return arraylistString.get(position);
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
    public void updateContent(String position, String newContent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.REFLECTION_CONTENT, "test!");
        String whereClause = "_id = ? ";
        db.update(Constants.REFLECTION_TABLE, values, whereClause , new String[]{ position });
        db.close();
    }

    public long insertDataReflection (String date, String time, String content)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.REFLECTION_OCCURED, date);
        contentValues.put(Constants.REFLECTION_TIME, time);
        contentValues.put(Constants.REFLECTION_CONTENT, content);
        long id = db.insert(Constants.REFLECTION_TABLE, null, contentValues);
        return id;
    }

    public void deleteContent(String position){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.REFLECTION_TABLE, "_id=? AND "+Constants.REFLECTION_OCCURED+" = ?", new String[]{position, date});
        db.close();
    }

}
