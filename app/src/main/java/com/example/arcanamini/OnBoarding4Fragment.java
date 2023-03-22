package com.example.arcanamini;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnBoarding4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnBoarding4Fragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button goToMainButton;
    public OnBoarding4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnBoarding4Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnBoarding4Fragment newInstance(String param1, String param2) {
        OnBoarding4Fragment fragment = new OnBoarding4Fragment();
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
        View v =  inflater.inflate(R.layout.fragment_on_boarding4, container, false);
        goToMainButton = v.findViewById(R.id.onboarding4Button);
        goToMainButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v == v.findViewById(R.id.onboarding4Button)){
            //finish activity and start MainActivity
            Intent intent = new Intent (getContext(), MainActivity.class);
            this.startActivity(intent);
            getActivity().finish();
        }
    }
}