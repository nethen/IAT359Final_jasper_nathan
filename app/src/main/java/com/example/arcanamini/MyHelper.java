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
//        try {
//            db.execSQL(CREATE_MINOR_TABLE);
//
//
//            db.execSQL(CREATE_MAJOR_TABLE);
//
//            db.execSQL(CREATE_TECHNIQUES_TABLE);
//
//
//            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
//        } catch (SQLException e) {
//            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
//        }
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
    //----------------

//    public long insertDataMinor (String name, int status, String defUpright, String defReversed)
//    {
//        db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Constants.NAME, name);
//        contentValues.put(Constants.STATUS, status);
//        contentValues.put(Constants.DEF_UPRIGHT, defUpright);
//        contentValues.put(Constants.DEF_REVERSED, defReversed);
//        long id = db.insert(Constants.MINOR_TABLE, null, contentValues);
//        return id;
//    }
//
//    public long insertDataMajor (String name, int status, String defUpright, String defReversed)
//    {
//        db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(Constants.NAME, name);
//        contentValues.put(Constants.STATUS, status);
//        contentValues.put(Constants.DEF_UPRIGHT, defUpright);
//        contentValues.put(Constants.DEF_REVERSED, defReversed);
//        long id = db.insert(Constants.MAJOR_TABLE, null, contentValues);
//        return id;
//    }
//
    public long insertDataTechniques (String name, String text)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NAME, name);
        contentValues.put(Constants.TECHNIQUE_TEXT, text);
        long id = db.insert(Constants.TECHNIQUES_TABLE, null, contentValues);
        return id;
    }
    public Cursor getMinorData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};
        Cursor cursor = db.query(Constants.MINOR_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getMajorData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.STATUS, Constants.DEF_UPRIGHT, Constants.DEF_REVERSED};
        Cursor cursor = db.query(Constants.MAJOR_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public Cursor getTechniquesData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {Constants.UID, Constants.NAME, Constants.TECHNIQUE_TEXT};
        Cursor cursor = db.query(Constants.TECHNIQUES_TABLE, columns, null, null, null, null, null);
        return cursor;
    }

    public String getSelectedDataMinor(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
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
        SQLiteDatabase db = this.getWritableDatabase();
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
        SQLiteDatabase db = this.getWritableDatabase();
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS, newStatus);
        String selection = Constants.NAME + " LIKE ?";
        String[] selection_arg = {name};
        db.update(Constants.MINOR_TABLE, values,selection , selection_arg);
    }

    public void updateMajorCard(String name, int newStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.STATUS, newStatus);
        String selection = Constants.NAME + " LIKE ?";
        String[] selection_arg = {name};
        db.update(Constants.MAJOR_TABLE, values,selection , selection_arg);
    }



    // Creates an empty database
    // on the system and rewrites it
    // with your own database.
    public void createDataBase()
            throws IOException
    {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        }
        else {
            // By calling this method and
            // the empty database will be
            // created into the default system
            // path of your application
            // so we are gonna be able
            // to overwrite that database
            // with our database.
            this.getWritableDatabase();
            try {
                copyDataBase();
            }
            catch (IOException e) {
                throw new Error(
                        "Error copying database");
            }
        }
    }

    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB
                    = SQLiteDatabase
                    .openDatabase(
                            myPath, null,
                            SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e) {

            // database doesn't exist yet.
            Log.e("message", "" + e);
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }


    private void copyDataBase()
            throws IOException
    {
        // Open your local db as the input stream
        InputStream myInput
                = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH;

        // Open the empty db as the output stream
        OutputStream myOutput
                = new FileOutputStream(outFileName);

        // transfer bytes from the
        // inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase()
            throws SQLException
    {
        // Open the database
        String myPath = DB_PATH;
        db = SQLiteDatabase
                .openDatabase(
                        myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close()
    {
        // close the database.
        if (db != null)
            db.close();
        super.close();
    }

}
