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


public class ReflectionDetailsFragment extends Fragment {


    private RecyclerView myRecycler;
    private LinearLayoutManager mLayoutManager;

    private MyAdapter myAdapter;

    private SQLiteDatabase mDb;

    public static ArrayList<String> list;
    int table;
    String inpTable;
    TextView title;
    SearchView searchView;
    public ReflectionDetailsFragment() {
        // Required empty public constructor
    }

    public static ReflectionDetailsFragment newInstance() {
        ReflectionDetailsFragment fragment = new ReflectionDetailsFragment();
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
        View v =  inflater.inflate(R.layout.fragment_reflection_details, container, false);


        return v;

    }

}