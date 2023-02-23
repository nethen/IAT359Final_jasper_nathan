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

    Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> mArrayList = new ArrayList<String>();

        db = new MyDatabase(this);
        helper = new MyHelper(this);


        Cursor cursor = db.getData();

        int index1 = cursor.getColumnIndex(Constants.NAME);
        int index2 = cursor.getColumnIndex(Constants.SUIT);
        int index3 = cursor.getColumnIndex(Constants.STATUS);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String cardName = cursor.getString(index1);
            String cardSuit = cursor.getString(index2);
            String cardStatus = cursor.getString(index3);
            String s = cardName +"," + cardSuit + "," + cardStatus;
            mArrayList.add(s);
            cursor.moveToNext();
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
            long id = db.insertData("name1", "suit1", "status1", "definition1", "definition2");
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