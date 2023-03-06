package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CardRecyclerActivity extends AppCompatActivity {
    private RecyclerView myRecycler;
    private LinearLayoutManager mLayoutManager;

    private MyAdapter myAdapter;

    private SQLiteDatabase mDb;

    public static ArrayList<String> list;
    int table;
    String inpTable;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);

        //retrieve extra
        Bundle extra_data = getIntent().getExtras();
        title = findViewById(R.id.categoryTitleTextView);

        inpTable = new String();
//        // check if the bundle was received (bundle not null)
        if (extra_data!= null) {
            table = extra_data.getInt("TABLE");
            switch (table){
                case 0:
                    inpTable = Constants.MAJOR_TABLE;
                    title.setText("Major arcana");
                    break;
                case 1:
                    inpTable = Constants.MINOR_TABLE;
                    title.setText("Minor arcana");
                    break;
                case 2:
                    inpTable = Constants.TECHNIQUES_TABLE;
                    title.setText("Techniques");
                    break;
            }
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this, "librariumdatabase", inpTable);
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);

        if (database.exists() == false){
            databaseHelper.getReadableDatabase();
            if(!copyDatabase(CardRecyclerActivity.this)){
                return;
            }

        }
        list = databaseHelper.getItems();
        myAdapter = new MyAdapter(list, table);
        myAdapter.notifyDataSetChanged();
        myRecycler.setAdapter(myAdapter);

        title = findViewById(R.id.categoryTitleTextView);

//            Log.i("TABLE", String.valueOf(table));
//            if(table == 0){
//                //major table
//                title.setText("Major arcana");
//                Cursor cursor = db.getMajorData();
//
//                int index1 = cursor.getColumnIndex(Constants.NAME);
//                int index2 = cursor.getColumnIndex(Constants.STATUS);
//
//
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    String cardName = cursor.getString(index1);
//                    String cardStatus = cursor.getString(index2);
//                    String s = cardName + "," + cardStatus;
//                    mArrayList.add(s);
//                    cursor.moveToNext();
//                }
//            }
//
//        } else{
//            // did not receive bundle with extra data
//            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
//        }

//        customAdapter = new CustomAdapter(mArrayList);
//        myRecycler.setAdapter(customAdapter);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(mLayoutManager);

    }

    public boolean copyDatabase(Context context){
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION+"/"+DatabaseHelper.DBNAME;
            File f = new File(outFileName);
            f.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int i = 0;
            while ( (i = inputStream.read(buffer)) > 0){
                outputStream.write(buffer,0, i);
            }
            outputStream.flush();
            outputStream.close();
            return true;

        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

}