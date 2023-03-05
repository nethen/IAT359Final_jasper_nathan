package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReflectionDetailsActivity extends AppCompatActivity {
    TextView timeTextView, contentTextView;
    public static ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_details);

        timeTextView = findViewById(R.id.reflectionDetailsTimeTextView);
        contentTextView = findViewById(R.id.reflectionDetailsContentTextView);

        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getIntent().getExtras();
        if (extra_data!= null) {
            int position = extra_data.getInt("ITEM_KEY");

            //Toast.makeText(this, , Toast.LENGTH_SHORT).show();

            ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(this);
            databaseHelper.getReadableDatabase();
            list = databaseHelper.getItemsWithContent();

            String[] reflection = list.get(position).split(",");
            String time = reflection[0] ;
            String content = reflection[1];
            timeTextView.setText(time);
            contentTextView.setText(content);
        }else{
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }

    }
}