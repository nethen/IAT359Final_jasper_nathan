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

        Cursor cursor = db.getMiniData();

        int index1 = cursor.getColumnIndex(Constants.NAME);
        int index2 = cursor.getColumnIndex(Constants.SUIT);
        int index3 = cursor.getColumnIndex(Constants.STATUS);
        int index4 = cursor.getColumnIndex(Constants.DEF_UPRIGHT);
        int index5 = cursor.getColumnIndex(Constants.DEF_REVERSED);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String cardName = cursor.getString(index1);
            String cardSuit = cursor.getString(index2);
            String cardStatus = cursor.getString(index3);
            String cardDefUpright = cursor.getString(index4);
            String cardDefReversed = cursor.getString(index5);
            String s = cardName +"," + cardSuit + "," + cardStatus + "," + cardDefUpright + "," + cardDefReversed;
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
            int status = Integer.parseInt(results[2]);
            String defUpright = results[3];
            String defReversed = results[4];
            cardTitle.setText(name + " " + suit);

            //status 0=upright, 1=reversed
            if(status == 0){
                cardDefinition.setText(defUpright);
            }else if (status == 1){
                cardDefinition.setText(defReversed);
            }

        } else{
            // did not receive bundle with extra data
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }
    }



}