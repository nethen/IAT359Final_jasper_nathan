package com.example.arcanamini;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnBoarding2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnBoarding2Fragment extends Fragment implements View.OnClickListener, TextWatcher {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button enterGrey, enterBlack;
    EditText nameEditText;
    SharedPreferences sharedPref;
    public OnBoarding2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnBoarding2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnBoarding2Fragment newInstance(String param1, String param2) {
        OnBoarding2Fragment fragment = new OnBoarding2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Context context = getActivity();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_on_boarding2, container, false);
        enterBlack = v.findViewById(R.id.onboarding2Button1);
        enterBlack.setOnClickListener(this);
        enterBlack.setVisibility(View.GONE);
        enterGrey = v.findViewById(R.id.onboarding2Button2);
        nameEditText = v.findViewById(R.id.onboardingNameEditText);
        nameEditText.addTextChangedListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == v.findViewById(R.id.onboarding2Button1)){
            //save user name
            SharedPreferences.Editor e = sharedPref.edit();
            e.putString("NAME", nameEditText.getText().toString());
            e.apply();
            //start next fragment
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.onboard_fragment_container, OnBoarding3Fragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //if user enters noting, hide valid button
        if(s.toString().trim().length()==0){
            enterBlack.setVisibility(View.GONE);
            enterGrey.setVisibility(View.VISIBLE);
        } else {
            //otherwise show valid button
            enterBlack.setVisibility(View.VISIBLE);
            enterGrey.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}