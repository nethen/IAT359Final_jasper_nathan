package com.example.arcanamini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class ReflectionDetailsFragment extends Fragment {
    TextView dayTextView, timeTextView;
    public static ArrayList<String> list;
    String date;
    Button editButton, deleteButton;
    EditText contentEditText;
    Boolean edit;
    int position;
    ReadDatabaseHelper databaseHelper;
    public ReflectionDetailsFragment() {
        // Required empty public constructor
    }

    public static ReflectionDetailsFragment newInstance() {
        ReflectionDetailsFragment fragment = new ReflectionDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_reflection_details, container, false);
        edit = false;
        //text views
        dayTextView = v.findViewById(R.id.reflectionDetailsDayTextView);
        timeTextView = v.findViewById(R.id.reflectionDetailsTimeTextView);
        contentEditText= v.findViewById(R.id.reflectionDetailsContentEditText);
        //edit text
        contentEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        contentEditText.setEnabled(false);
        //button


        position = 0;
        // retrieve the bundle from the intent that started this activity
        Bundle extra_data = getArguments();
        if (extra_data!= null) {
        date = extra_data.getString("ITEM_DATE");
        databaseHelper = new ReadDatabaseHelper(getActivity(), date);
        databaseHelper.getReadableDatabase();
        list = databaseHelper.getItemsWithContent();

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
                contentEditText.setText(content);
            }
        }



        return v;

    }

}