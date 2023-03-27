package com.example.arcanamini;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_cardrecycler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_cardrecycler extends Fragment {


    private RecyclerView myRecycler;
    private LinearLayoutManager mLayoutManager;

    private MyAdapter myAdapter;

    private SQLiteDatabase mDb;

    public static ArrayList<String> list;
    int table;
    String inpTable;
    TextView title;
    SearchView searchView;
    public fragment_cardrecycler() {
        // Required empty public constructor
    }

    public static fragment_cardrecycler newInstance() {
        fragment_cardrecycler fragment = new fragment_cardrecycler();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View v =  inflater.inflate(R.layout.fragment_cardrecycler, container, false);

        myRecycler = (RecyclerView) v.findViewById(R.id.recyclerView);
        title = v.findViewById(R.id.categoryTitleTextView);

        //retrieve extra
        Bundle extra_data = getArguments();

        inpTable = new String();
//        // check if the bundle was received (bundle not null)
        if (extra_data!= null) {
            table = extra_data.getInt("TABLE");
            switch (table){
                case 0:
                    inpTable = Constants.MAJOR_TABLE;
                    title.setText("Major arcana");
                    break;
                case 1:
                    inpTable = Constants.MINOR_TABLE;
                    title.setText("Minor arcana");
                    break;
                case 2:
                    inpTable = Constants.TECHNIQUES_TABLE;
                    title.setText("Techniques");
                    break;
            }
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "librariumdatabase", inpTable);
        File database = getActivity().getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);

        if (database.exists() == false){
            databaseHelper.getReadableDatabase();
            if(!copyDatabase(getActivity())){
                return v;
            }

        }
        list = databaseHelper.getItems();
        myAdapter = new MyAdapter(list, table);
        myAdapter.notifyDataSetChanged();
        myRecycler.setAdapter(myAdapter);

        title = v.findViewById(R.id.categoryTitleTextView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        myRecycler.setLayoutManager(mLayoutManager);

        //search bar
        searchView = v.findViewById(R.id.recyclerSearchView);
        searchView.setQueryHint("Enter your search here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return v;

    }

    public boolean copyDatabase(Context context){
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION+"/"+DatabaseHelper.DBNAME;
            File f = new File(outFileName);
            f.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int i = 0;
            while ( (i = inputStream.read(buffer)) > 0){
                outputStream.write(buffer,0, i);
            }
            outputStream.flush();
            outputStream.close();
            return true;

        } catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
}