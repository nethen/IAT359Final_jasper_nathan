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


public class OnBoarding2Fragment extends Fragment implements View.OnClickListener, TextWatcher {

    Button enterGrey, enterBlack;
    EditText nameEditText;
    SharedPreferences sharedPref;
    public OnBoarding2Fragment() {
        // Required empty public constructor
    }

    public static OnBoarding2Fragment newInstance() {
        OnBoarding2Fragment fragment = new OnBoarding2Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            enterBlack.setClickable(false);
            enterBlack.setAlpha((float) 0.1);
        } else {
            //otherwise show valid button
            enterBlack.setClickable(true);
            enterBlack.setAlpha((float) 1);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}