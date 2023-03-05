package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class MainRecyclerActivity extends AppCompatActivity {
    private RecyclerView myRecycler;
    private ReadAdapter readAdapter;
    public static ArrayList<String> list;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.homeRecyclerView2);
        ArrayList<String> mArrayList = new ArrayList<String>();

        ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(this);
        databaseHelper.getReadableDatabase();
        list = databaseHelper.getItems();
        readAdapter = new ReadAdapter(list);
        readAdapter.notifyDataSetChanged();
        myRecycler.setAdapter(readAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(mLayoutManager);

    }
}