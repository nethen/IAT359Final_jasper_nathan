package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardDetailsActivity extends AppCompatActivity {
    TextView cardTitle, cardDefinition;
    private MyHelper helper;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        ArrayList<String> mArrayList = new ArrayList<String>();

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Cursor cursor = db.getData();

        int index1 = cursor.getColumnIndex(Constants.NAME);
        int index2 = cursor.getColumnIndex(Constants.SUIT);
        int index3 = cursor.getColumnIndex(Constants.STATUS);
        int index4 = cursor.getColumnIndex(Constants.DEFINITION);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String cardName = cursor.getString(index1);
            String cardSuit = cursor.getString(index2);
            String cardStatus = cursor.getString(index3);
            String cardDefinition = cursor.getString(index4);
            String s = cardName +"," + cardSuit + "," + cardStatus + "," + cardDefinition;
            mArrayList.add(s);
            cursor.moveToNext();
        }

        //initialize text views
        cardTitle = findViewById(R.id.cardTitleDetailsTextView);
        cardDefinition = findViewById(R.id.cardDefinitionDetailsTextView);

        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getIntent().getExtras();

        // check if the bundle was received (bundle not null)
        if (extra_data!= null) {
            int position = extra_data.getInt("ITEM_KEY");
            String[] results = (mArrayList.get(position).toString()).split(",");
            String name = results[0];
            String suit = results[1];
            String status = results[2];
            String definition = results[3];
            cardTitle.setText(name + " " + suit);
            cardDefinition.setText(definition);
        } else{
            // did not receive bundle with extra data
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }
    }



}