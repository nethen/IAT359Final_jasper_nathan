package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class CardRecyclerActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView myRecycler;
    private CustomAdapter customAdapter;
    private LinearLayoutManager mLayoutManager;
    private MyHelper helper;
    private MyDatabase db;
    int table;

    Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> mArrayList = new ArrayList<String>();

        db = new MyDatabase(this);
        helper = new MyHelper(this);



        //retrieve extra
        Bundle extra_data = getIntent().getExtras();

        // check if the bundle was received (bundle not null)
        if (extra_data!= null) {
            table = extra_data.getInt("TABLE");
            if(table == 0){
                //minor table
                Cursor cursor = db.getMinorData();

                int index1 = cursor.getColumnIndex(Constants.NAME);
                int index2 = cursor.getColumnIndex(Constants.STATUS);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String cardName = cursor.getString(index1);
                    String cardStatus = cursor.getString(index2);
                    String s = cardName + "," + cardStatus;
                    mArrayList.add(s);
                    cursor.moveToNext();
                }

            }else if(table == 1){
                //major table
                Cursor cursor = db.getMajorData();

                int index1 = cursor.getColumnIndex(Constants.NAME);
                int index2 = cursor.getColumnIndex(Constants.STATUS);


                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String cardName = cursor.getString(index1);
                    String cardStatus = cursor.getString(index2);
                    String s = cardName + "," + cardStatus;
                    mArrayList.add(s);
                    cursor.moveToNext();
                }
            }else if (table == 2){
                Cursor cursor = db.getTechniquesData();

                int index1 = cursor.getColumnIndex(Constants.NAME);
                int index2 = cursor.getColumnIndex(Constants.TECHNIQUE_TEXT);

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String techniqueName = cursor.getString(index1);
                    String techniqueText = cursor.getString(index2);
                    String s = techniqueName + "," + techniqueText;
                    mArrayList.add(s);
                    cursor.moveToNext();
                }
            }

        } else{
            // did not receive bundle with extra data
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }

        customAdapter = new CustomAdapter(mArrayList);
        myRecycler.setAdapter(customAdapter);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(mLayoutManager);

        myButton=findViewById(R.id.addButton);
        myButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==findViewById(R.id.addButton)){
            if(table ==0){
                long id = db.insertDataMinor("name1", 0, "definition1", "definition2");
                if (id < 0)
                {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                }
            }else if(table==1){
                long id = db.insertDataMajor("name1", 0, "definition1", "definition2");
                if (id < 0)
                {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                }
            }else if(table==2){
                long id = db.insertDataTechniques("name1", "technique text");
                if (id < 0)
                {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}