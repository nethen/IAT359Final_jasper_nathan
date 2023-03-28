package com.example.arcanamini;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


public class CamFragment extends Fragment {
    private TextView title;
    private Button skip, sensor;
    public CamFragment() {
        // Required empty public constructor
    }

    public static CamFragment newInstance() {
        CamFragment fragment = new CamFragment();
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
        View v =  inflater.inflate(R.layout.fragment_precamera, container, false);
        title = v.findViewById(R.id.precam_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_camFragment_to_activity_main);
            }
        });
        skip = v.findViewById(R.id.button_reflect_skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                Object dateVal = calendar.getTime();
                String date = new SimpleDateFormat("MMM d, yyyy").format(dateVal);
                String hourMin = new SimpleDateFormat("hh:mm aaa").format(dateVal);
                ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(getActivity(), date);
                databaseHelper.getWritableDatabase();


                databaseHelper.insertDataReflection(date,hourMin, "No content found");
                ArrayList<String> list = databaseHelper.getItemsWithContent();

//                Bundle b = new Bundle();
//                b.putBoolean("FORCE_REFLECT", true);
//                b.putInt("ITEM_KEY", list.size()-1);
//                b.putString("ITEM_DATE", date);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("FORCE_REFLECT", true);
                intent.putExtra("ITEM_KEY", list.size()-1);
                intent.putExtra("ITEM_DATE", date);
                intent.putExtra("EDIT_AUTO", true);
                startActivity(intent);
//                Navigation.findNavController(v).navigate(R.id.action_camFragment_to_reflectionDetailsFragment3, b);
            }
        });
        sensor = v.findViewById(R.id.button_reflect_sensor);
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;

    }}