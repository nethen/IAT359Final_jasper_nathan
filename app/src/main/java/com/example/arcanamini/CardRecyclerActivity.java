package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CardRecyclerActivity extends AppCompatActivity {
    private RecyclerView myRecycler;
    private CustomAdapter customAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_recycler);

        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> mArrayList = new ArrayList<String>();



//        customAdapter = new CustomAdapter(mArrayList);
//        myRecycler.setAdapter(customAdapter);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(mLayoutManager);
    }
}