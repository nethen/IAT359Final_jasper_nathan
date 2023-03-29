package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment implements View.OnClickListener {
    ReadDatabaseHelper dbHelper;

    private TextView dateTimeDisplay, empty_content;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private TextView welcome;

    //for recycler
    private RecyclerView myRecycler, calRecycler;
    private ReadAdapter readAdapter;

    private LinearLayout emptyState;
    public static ArrayList<String> list;
    private LinearLayoutManager mLayoutManager;

    SharedPreferences sharedPref;

    //new reflection
    FloatingActionButton newRefButton;
    ImageButton accountButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MMM d, yyyy");

        Context context = getActivity();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //default boolean is set to true
        Boolean isFirstRun = sharedPref.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            //runs this code only once on the first start up by changing boolean to false
            SharedPreferences.Editor e = sharedPref.edit();
            e.putBoolean("isFirstRun", false);
            e.apply();
            //start onboarding activity
            Intent intent = new Intent (getContext(), OnBoardingActivity.class);
            this.startActivity(intent);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        date = dateFormat.format(calendar.getTime());
        Log.i("dateF", date);
        dbHelper = new ReadDatabaseHelper(getContext(), date);

        dateTimeDisplay = view.findViewById(R.id.text_date);
        dateTimeDisplay.setText(date);
        welcome = view.findViewById(R.id.text_welcome);

        //get username and set welcome text
        String s = sharedPref.getString("NAME", "none");
        Log.i("username",s);
        welcome.setText(welcome.getText() + " " + s);

        myRecycler = (RecyclerView) view.findViewById(R.id.homeRecyclerView);
        calRecycler = (RecyclerView) view.findViewById(R.id.home_calendar);
        emptyState = (LinearLayout) view.findViewById(R.id.home_empty);

        ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(this.getContext(), date);
        databaseHelper.getReadableDatabase();
        list = databaseHelper.getItems();
        if (list.size() > 0){
            emptyState.setVisibility(View.GONE);
            myRecycler.setVisibility(View.VISIBLE);
        } else {
            myRecycler.setVisibility(View.GONE);
        }

        readAdapter = new ReadAdapter(list, String.valueOf(date));
        readAdapter.notifyDataSetChanged();
        myRecycler.setAdapter(readAdapter);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        myRecycler.setLayoutManager(mLayoutManager);

        newRefButton = view.findViewById(R.id.newReflectionFloatingActionButton);
        newRefButton.setOnClickListener(this);
        accountButton = view.findViewById(R.id.accountButton);
        accountButton.setOnClickListener(this);
        empty_content = view.findViewById(R.id.empty_content);
        empty_content.setOnClickListener(this);
        
        setWeekView();
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == v.findViewById(R.id.newReflectionFloatingActionButton) || v == v.findViewById(R.id.empty_content)){
            //start new reflection
            Intent intent = new Intent (this.getContext(), CamActivity.class);
//            intent.putExtra ("DATE", calendar.getTime() );
//            Log.i("date", String.valueOf(calendar.getTime()));
            this.startActivity(intent);
        } else if (v== v.findViewById(R.id.accountButton)){
            //start account edit page
            Intent intent = new Intent (this.getContext(), EditAccountActivity.class);
            this.startActivity(intent);
        }
    }

    private void setWeekView(){
        ArrayList<LocalDate> daysInWeek = daysInWeekArray(LocalDate.now());
        CalAdapter calendarAdapter = new CalAdapter(daysInWeek, LocalDate.now());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calRecycler.setLayoutManager(layoutManager);
        calRecycler.setAdapter(calendarAdapter);

    }
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate)
    {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate sundayForDate(LocalDate current)
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }

        return null;
    }

}