package com.example.arcanamini;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LibrariumFragment extends Fragment {

    MaterialCardView minorButton, majorButton, techniquesButton;

    public LibrariumFragment() {
        // Required empty public constructor
    }

    public static LibrariumFragment newInstance(String param1, String param2) {
        LibrariumFragment fragment = new LibrariumFragment();
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
        View v =  inflater.inflate(R.layout.fragment_librarium, container, false);
        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int table = -1;
                switch (v.getId()){
                    case R.id.card_arcanaMajor:
                        table = 0;
                        break;
                    case R.id.card_arcanaMinor:
                        table = 1;
                        break;
                    case R.id.card_spreadsMethods:
                        table = 2;
                        break;
                }
                if (table >= 0) {
                    Bundle b = new Bundle();
                    b.putInt ("TABLE", table );
//                    getActivity().startActivity(intent);
                    Navigation.findNavController(v).navigate(R.id.action_librariumFragment_to_fragment_cardrecycler, b);
                }
            }
        };
        majorButton = v.findViewById(R.id.card_arcanaMajor);
        majorButton.setOnClickListener(click);
        minorButton = v.findViewById(R.id.card_arcanaMinor);
        minorButton.setOnClickListener(click);
        techniquesButton = v.findViewById(R.id.card_spreadsMethods);
        techniquesButton.setOnClickListener(click);
        return v;
    }
}