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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button enterGrey, enterBlack;
    private RadioGroup styleGroup;

    public OnBoarding3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnBoarding3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnBoarding3Fragment newInstance(String param1, String param2) {
        OnBoarding3Fragment fragment = new OnBoarding3Fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_on_boarding3, container, false);
        enterBlack = v.findViewById(R.id.onboarding3Button1);
        enterBlack.setOnClickListener(this);
        enterBlack.setVisibility(View.GONE);
        enterGrey = v.findViewById(R.id.onboarding3Button2);
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
        enterBlack.setVisibility(View.VISIBLE);
        enterGrey.setVisibility(View.GONE);

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