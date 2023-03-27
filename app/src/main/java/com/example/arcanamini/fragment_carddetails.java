package com.example.arcanamini;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_carddetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_carddetails extends Fragment {


    public fragment_carddetails() {
        // Required empty public constructor
    }

    public static fragment_carddetails newInstance() {
        fragment_carddetails fragment = new fragment_carddetails();
        Bundle args = new Bundle();
        return fragment;
    }

        TextView cardTitle, cardDefinition;
        String queryResults;
        Spinner spinner;
        ImageView image;
        DetailsLoader loader;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_card_details, container, false);

        ArrayList<String> mArrayList = new ArrayList<String>();

            //initialize text views
            cardTitle = v.findViewById(R.id.cardTitleDetailsTextView);
            cardDefinition = v.findViewById(R.id.cardDefinitionDetailsTextView);
            //initialize spinner
            spinner = v.findViewById(R.id.card_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.card_status_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            //listener for spinner
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Bundle extra_data = getArguments();
                    if (extra_data!= null) {
                        String itemName = extra_data.getString("ITEM_NAME");
                        //minor
                        if(loader.getSelectedDataMinor(itemName).contains(itemName) ) {
                            queryResults = loader.getSelectedDataMinor(itemName);
                            String[] results = queryResults.split("_");
                            String name = results[0];
                            int status = Integer.parseInt(results[1]);
                            String defUpright = "";
                            String defReversed = "";

                            String[] defUprightSplit = results[2].split("~");
                            for(int i = 0; i<defUprightSplit.length; i++){
                                defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
                            }
                            String[] defReversedSplit = results[3].split("~");
                            for(int i = 0; i<defReversedSplit.length; i++){
                                defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
                            }

                            switch(position){
                                case 0:
                                    //select upright
                                    loader.updateMinorCard(name, 1);

                                    break;
                                case 1:
                                    //select reversed
                                    loader.updateMinorCard(name,0);
                                    break;
                            }

                            //status 1=upright, 0=reversed
                            if(status == 0){
                                cardDefinition.setText(defUpright);
                                image.setRotation(0);
                            }else if (status == 1){
                                cardDefinition.setText(defReversed);
                                image.setRotation(180);
                            }
                        }
                        //major
                        if(loader.getSelectedDataMajor(itemName).contains(itemName) ){
                            queryResults = loader.getSelectedDataMajor(itemName);
                            String[] results = queryResults.split("_");
                            String name = results[0];
                            int status = Integer.parseInt(results[1]);
                            String defUpright = "";
                            String defReversed = "";

                            String[] defUprightSplit = results[2].split("~");
                            for(int i = 0; i<defUprightSplit.length; i++){
                                defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
                            }
                            String[] defReversedSplit = results[3].split("~");
                            for(int i = 0; i<defReversedSplit.length; i++){
                                defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
                            }

                            switch(position){
                                case 0:
                                    //select upright

                                    loader.updateMajorCard(name, 1);

                                    break;
                                case 1:
                                    //select reversed

                                    loader.updateMajorCard(name,0);
                                    break;
                            }
                            //status 1=upright, 0=reversed
                            if(status == 0){
                                cardDefinition.setText(defUpright);
                                image.setRotation(0);
                            }else if (status == 1){
                                cardDefinition.setText(defReversed);
                                image.setRotation(180);
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //image
            image=(ImageView) v.findViewById(R.id.detailsImageView);

            // retrieve the bundle from the intent that started this activity
            Bundle extra_data = getArguments();

            // check if the bundle was received (bundle not null)
            if (extra_data!= null) {
                String itemName = extra_data.getString("ITEM_NAME");
                int table = extra_data.getInt("ITEM_TABLE");
                loader = new DetailsLoader(getActivity(), table);

                if(loader.getSelectedDataMinor(itemName).contains(itemName) ){
                    queryResults = loader.getSelectedDataMinor(itemName);

                    String[] results = queryResults.split("_");
                    String name = results[0];
                    int status = Integer.parseInt(results[1]);
                    String defUpright = "";
                    String defReversed = "";

                    String[] defUprightSplit = results[2].split("~");
                    for(int i = 0; i<defUprightSplit.length; i++){
                        defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
                    }
                    String[] defReversedSplit = results[3].split("~");
                    for(int i = 0; i<defReversedSplit.length; i++){
                        defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
                    }
                    cardTitle.setText(name);
                    String imageName = new String (name.toLowerCase().replace(" ",""));
                    Resources res = getResources();
                    int resID = res.getIdentifier(imageName , "drawable", getActivity().getPackageName());
                    if (resID > 0){
                        Drawable drawable = res.getDrawable(resID );
                        image.setImageDrawable(drawable);
                    }



                    //status 1=upright, 0=reversed
                    if(status == 0){
                        cardDefinition.setText(defUpright);
                        image.setRotation(0);
                    }else if (status == 1){
                        cardDefinition.setText(defReversed);
                        image.setRotation(180);
                    }
                }

                if (loader.getSelectedDataMajor(itemName).contains(itemName) ){
                    queryResults = loader.getSelectedDataMajor(itemName);
                    String[] results = queryResults.split("_");
                    String name = results[0];
                    int status = Integer.parseInt(results[1]);
                    String defUpright = "";
                    String defReversed = "";

                    String[] defUprightSplit = results[2].split("~");
                    for(int i = 0; i<defUprightSplit.length; i++){
                        defUpright = defUpright + defUprightSplit[i] + "\n" + "\n";
                    }
                    String[] defReversedSplit = results[3].split("~");
                    for(int i = 0; i<defReversedSplit.length; i++){
                        defReversed = defReversed + defReversedSplit[i] + "\n" + "\n";
                    }
                    cardTitle.setText(name);
                    String imageName = new String (name.toLowerCase().replace(" ",""));
                    Resources res = getResources();
                    int resID = res.getIdentifier(imageName, "drawable", getActivity().getPackageName());

                    Drawable drawable = res.getDrawable(resID );

                    image.setImageDrawable(drawable);
                    //status 1=upright, 0=reversed
                    if(status == 0){
                        cardDefinition.setText(defUpright);
                        image.setRotation(0);
                    }else if (status == 1){
                        cardDefinition.setText(defReversed);
                        image.setRotation(180);
                    }
                }

                if (loader.getSelectedDataTechniques(itemName).contains(itemName) ){
                    queryResults = loader.getSelectedDataTechniques(itemName);
                    String[] results = queryResults.split("_");
                    String name = results[0];
                    String text = results[1];
                    cardTitle.setText(name);

                    cardDefinition.setText(text);

                    //no dropdown menu for this table
                    spinner.setVisibility(spinner.GONE);

                    String imageName = new String (name.toLowerCase().replace(" ",""));
                    Resources res = getResources();
                    int resID = res.getIdentifier(imageName, "drawable", getActivity().getPackageName());

                    if (resID > 0){
                        Drawable drawable = res.getDrawable(resID );
                        image.setImageDrawable(drawable);
                    }
                }


            } else{
                // did not receive bundle with extra data
                Toast.makeText(getActivity(), "Didn't receive any data", Toast.LENGTH_SHORT).show();
            }
            return v;
        }
}