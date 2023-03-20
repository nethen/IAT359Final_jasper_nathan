package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReflectionDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView timeTextView, contentTextView;
    public static ArrayList<String> list;
    Button editButton;
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
        timeTextView = findViewById(R.id.reflectionDetailsTimeTextView);
        contentTextView = findViewById(R.id.reflectionDetailsContentTextView);
        //edit text
        contentEditText = findViewById(R.id.reflectionDetailsEditText);
        contentEditText.setOnClickListener(this);
        contentEditText.setVisibility(View.GONE);
        //button
        editButton = findViewById(R.id.editReflectionContentButton);
        editButton.setOnClickListener(this);

        position = 0;

        databaseHelper = new ReadDatabaseHelper(this);
        databaseHelper.getReadableDatabase();
        list = databaseHelper.getItemsWithContent();

        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getIntent().getExtras();
        if (extra_data!= null) {
            position = extra_data.getInt("ITEM_KEY");
            Log.i("pos", String.valueOf(position));
            //Toast.makeText(this, , Toast.LENGTH_SHORT).show();



            String[] reflection = list.get(position).split("~");
            String time = reflection[0] ;
            timeTextView.setText(time);
            //account for empty content
            if (reflection.length > 1) {
                String content = reflection[1];
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
        }


    }
}