package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView cardTitle, cardDefinition;
    String queryResults;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        ArrayList<String> mArrayList = new ArrayList<String>();

        //initialize text views
        cardTitle = findViewById(R.id.cardTitleDetailsTextView);
        cardDefinition = findViewById(R.id.cardDefinitionDetailsTextView);
        //initialize spinner
        spinner = findViewById(R.id.card_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.card_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //listener for spinner
        spinner.setOnItemSelectedListener(this);

        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getIntent().getExtras();

        // check if the bundle was received (bundle not null)
        if (extra_data!= null) {
            String itemName = extra_data.getString("ITEM_NAME");

//            if(db.getSelectedDataMinor(itemName).contains(itemName) ){
//                queryResults = db.getSelectedDataMinor(itemName);
//                Toast.makeText(this, queryResults,
//                        Toast.LENGTH_SHORT).show();
//                String[] results = queryResults.split("_");
//                String name = results[0];
//                int status = Integer.parseInt(results[1]);
//                String defUpright = "";
//                String defReversed = "";
//
//                String[] defUprightSplit = results[2].split("~");
//                for(int i = 0; i<defUprightSplit.length; i++){
//                    defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
//                }
//                String[] defReversedSplit = results[3].split("~");
//                for(int i = 0; i<defReversedSplit.length; i++){
//                    defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
//                }
//                cardTitle.setText(name);
//
//                //status 0=upright, 1=reversed
//                if(status == 0){
//                    cardDefinition.setText(defUpright);
//                }else if (status == 1){
//                    cardDefinition.setText(defReversed);
//                }
//            }

//            if (db.getSelectedDataMajor(itemName).contains(itemName) ){
//                queryResults = db.getSelectedDataMajor(itemName);
//                Toast.makeText(this, queryResults,
//                        Toast.LENGTH_SHORT).show();
//                String[] results = queryResults.split("_");
//                String name = results[0];
//                int status = Integer.parseInt(results[1]);
//                String defUpright = "";
//                String defReversed = "";
//
//                String[] defUprightSplit = results[2].split("~");
//                for(int i = 0; i<defUprightSplit.length; i++){
//                    defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
//                }
//                String[] defReversedSplit = results[3].split("~");
//                for(int i = 0; i<defReversedSplit.length; i++){
//                    defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
//                }
//                cardTitle.setText(name);
//
//                //status 0=upright, 1=reversed
//                if(status == 0){
//                    cardDefinition.setText(defUpright);
//                }else if (status == 1){
//                    cardDefinition.setText(defReversed);
//                }
//            }

//            if (db.getSelectedDataTechniques(itemName).contains(itemName) ){
//                queryResults = db.getSelectedDataTechniques(itemName);
//                Toast.makeText(this, queryResults,
//                        Toast.LENGTH_SHORT).show();
//                String[] results = queryResults.split("_");
//                String name = results[0];
//                String text = results[1];
//                cardTitle.setText(name);
//
//                cardDefinition.setText(text);
//
//                //no dropdown menu for this table
//                spinner.setVisibility(spinner.GONE);
//            }


        } else{
            // did not receive bundle with extra data
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Bundle extra_data = getIntent().getExtras();
//        if (extra_data!= null) {
//            String itemName = extra_data.getString("ITEM_NAME");
//            //minor
//            if(db.getSelectedDataMinor(itemName).contains(itemName) ) {
//                queryResults = db.getSelectedDataMinor(itemName);
//                String[] results = queryResults.split("_");
//                String name = results[0];
//                int status = Integer.parseInt(results[1]);
//                String defUpright = "";
//                String defReversed = "";
//
//                String[] defUprightSplit = results[2].split("~");
//                for(int i = 0; i<defUprightSplit.length; i++){
//                    defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
//                }
//                String[] defReversedSplit = results[3].split("~");
//                for(int i = 0; i<defReversedSplit.length; i++){
//                    defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
//                }
//
//                switch(position){
//                    case 0:
//                        //select upright
//                        Toast.makeText(this, "to go upright", Toast.LENGTH_LONG).show();
//                        db.updateMinorCard(name, 1);
//
//                        break;
//                    case 1:
//                        //select reversed
//                        Toast.makeText(this, "to go reversed", Toast.LENGTH_LONG).show();
//                        db.updateMinorCard(name,0);
//                        break;
//                }
//                //status 0=upright, 1=reversed
//                if(status == 0){
//                    cardDefinition.setText(defUpright);
//                }else if (status == 1){
//                    cardDefinition.setText(defReversed);
//                }
//            }
//            //major
//            if(db.getSelectedDataMajor(itemName).contains(itemName) ){
//                queryResults = db.getSelectedDataMajor(itemName);
//                String[] results = queryResults.split("_");
//                String name = results[0];
//                int status = Integer.parseInt(results[1]);
//                String defUpright = "";
//                String defReversed = "";
//
//                String[] defUprightSplit = results[2].split("~");
//                for(int i = 0; i<defUprightSplit.length; i++){
//                    defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
//                }
//                String[] defReversedSplit = results[3].split("~");
//                for(int i = 0; i<defReversedSplit.length; i++){
//                    defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
//                }
//
//                switch(position){
//                    case 0:
//                        //select upright
//                        Toast.makeText(this, "to go upright", Toast.LENGTH_LONG).show();
//                        db.updateMajorCard(name, 1);
//
//                        break;
//                    case 1:
//                        //select reversed
//                        Toast.makeText(this, "to go reversed", Toast.LENGTH_LONG).show();
//                        db.updateMajorCard(name,0);
//                        break;
//                }
//                //status 0=upright, 1=reversed
//                if(status == 0){
//                    cardDefinition.setText(defUpright);
//                }else if (status == 1){
//                    cardDefinition.setText(defReversed);
//                }
//            }
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}