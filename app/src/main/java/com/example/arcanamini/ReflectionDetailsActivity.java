package com.example.arcanamini;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ReflectionDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView dayTextView, timeTextView, contentTextView;
    public static ArrayList<String> list;
    String date;
    Button editButton, deleteButton;
    EditText contentEditText;
    Boolean edit;
    int position;
    ReadDatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_details);
        edit = false;
        //text views
        dayTextView = findViewById(R.id.reflectionDetailsDayTextView);
        timeTextView = findViewById(R.id.reflectionDetailsTimeTextView);
        contentTextView = findViewById(R.id.reflectionDetailsContentEditText);
        //edit text
        contentEditText = findViewById(R.id.reflectionDetailsEditText);
        contentEditText.setOnClickListener(this);
        contentEditText.setVisibility(View.GONE);
        //button
        editButton = findViewById(R.id.editReflectionContentButton);
        editButton.setOnClickListener(this);
        deleteButton = findViewById(R.id.deleteReflectionButton);
        deleteButton.setOnClickListener(this);

        position = 0;
        date = String.valueOf(DateFormat.format("MMM dd, yyyy", Calendar.getInstance().getTime()));
        databaseHelper = new ReadDatabaseHelper(this, date);
        databaseHelper.getReadableDatabase();
        list = databaseHelper.getItemsWithContent();

        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getIntent().getExtras();
        if (extra_data!= null) {
            position = extra_data.getInt("ITEM_KEY");
            Log.i("pos", String.valueOf(position));
            //Toast.makeText(this, , Toast.LENGTH_SHORT).show();



            String[] reflection = list.get(position).split("~");
            String day = reflection[0];
            String time = reflection[1];
            dayTextView.setText(day);
            timeTextView.setText(time);
            //account for empty content
            if (reflection.length > 2) {
                String content = reflection[2];
                contentTextView.setText(content);
            }
        }else{
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.editReflectionContentButton)){
            if(edit == false){
                //edit mode on
                contentEditText.setText(contentTextView.getText());
                contentEditText.setVisibility(View.VISIBLE);
                contentTextView.setVisibility(View.GONE);
                editButton.setText(R.string.saveReflectionButtonText);
                Toast.makeText(this, "edit mode on", Toast.LENGTH_SHORT).show();
                edit = true;
            }else if (edit == true){
                //edit mode off
                contentTextView.setText(contentEditText.getText());
                contentEditText.setVisibility(View.GONE);
                contentTextView.setVisibility(View.VISIBLE);
                databaseHelper.updateContent( Integer.toString(position), contentEditText.getText().toString());
                editButton.setText(R.string.editReflectionButtonText);
                Toast.makeText(this, "edit mode off", Toast.LENGTH_SHORT).show();
                edit = false;
            }
        }else if (v == findViewById(R.id.deleteReflectionButton)){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure you want to delete this reflection?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           //delete entry
//                            databaseHelper.deleteContent(Integer.toString(position));
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }


    }
}