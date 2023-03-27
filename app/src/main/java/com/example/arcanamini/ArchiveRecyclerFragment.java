package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArchiveRecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArchiveRecyclerFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ReadDatabaseHelper dbHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private TextView welcome;

    //for recycler
    private RecyclerView myRecycler;
    private ReadAdapter readAdapter;

    private LinearLayout emptyState;
    public static ArrayList<String> list;
    private LinearLayoutManager mLayoutManager;

    SharedPreferences sharedPref;

    //new reflection
    FloatingActionButton newRefButton;
    public ArchiveRecyclerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     */
    // TODO: Rename and change types and number of parameters
    public static ArchiveRecyclerFragment newInstance(String param1, String param2) {
        ArchiveRecyclerFragment fragment = new ArchiveRecyclerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_archive_recycler, container, false);
        date = dateFormat.format(calendar.getTime());
        Log.i("dateF", date);
        dbHelper = new ReadDatabaseHelper(getContext(), date);

        dateTimeDisplay = view.findViewById(R.id.text_date);
        dateTimeDisplay.setText(date);

        //get username and set welcome text
        String s = sharedPref.getString("NAME", "none");
        Log.i("username",s);

        myRecycler = (RecyclerView) view.findViewById(R.id.archiveRecyclerView);
        emptyState = (LinearLayout) view.findViewById(R.id.archive_empty);

        ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(this.getContext(), date);
        databaseHelper.getReadableDatabase();
        list = databaseHelper.getItems();
        if (list.size() > 0){
            emptyState.setVisibility(View.GONE);
            myRecycler.setVisibility(View.VISIBLE);
        } else {
            myRecycler.setVisibility(View.GONE);
        }

        readAdapter = new ReadAdapter(list);
        readAdapter.notifyDataSetChanged();
        myRecycler.setAdapter(readAdapter);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        myRecycler.setLayoutManager(mLayoutManager);


        return view;
    }
}