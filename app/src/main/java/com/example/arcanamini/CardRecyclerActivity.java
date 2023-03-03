package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardRecyclerActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView myRecycler;
    private LinearLayoutManager mLayoutManager;

    private SQLiteDatabase mDb;


    public static List<String> data;
    int table;
    TextView title;

    Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> mArrayList = new ArrayList<String>();

        title = findViewById(R.id.categoryTitleTextView);


        //retrieve extra
        Bundle extra_data = getIntent().getExtras();

//        // check if the bundle was received (bundle not null)
//        if (extra_data!= null) {
//            table = extra_data.getInt("TABLE");
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

        myButton=findViewById(R.id.addButton);
        myButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
//        if(v==findViewById(R.id.addButton)){
//            if(table ==2){
//                long id = db.insertDataTechniques("name1", "technique text");
//                if (id < 0)
//                {
//                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        }
    }
}