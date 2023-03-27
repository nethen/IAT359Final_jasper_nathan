package com.example.arcanamini;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

public class OnBoarding3Fragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    Button enterBlack;
    private RadioGroup styleGroup;

    public OnBoarding3Fragment() {
        // Required empty public constructor
    }

    public static OnBoarding3Fragment newInstance() {
        OnBoarding3Fragment fragment = new OnBoarding3Fragment();
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
        View v =  inflater.inflate(R.layout.fragment_on_boarding3, container, false);
        enterBlack = v.findViewById(R.id.onboarding3Button1);
        enterBlack.setOnClickListener(this);
        enterBlack.setClickable(false);
        enterBlack.setAlpha((float) 0.1);
        styleGroup = (RadioGroup) v.findViewById(R.id.onboardStyleRadioGroup);
        styleGroup.setOnCheckedChangeListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == v.findViewById(R.id.onboarding3Button1)){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.onboard_fragment_container, OnBoarding4Fragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //valid button hidden until a radio button is selected
        enterBlack.setClickable(true);
        enterBlack.setAlpha((float) 1);

        switch (checkedId) {
            case R.id.onboardLightRadioButton:
                //set app theme to light mode
                break;
            case R.id.onboardDarkRadioButton:
                //set app theme to dark mode
                break;
        }
    }

}