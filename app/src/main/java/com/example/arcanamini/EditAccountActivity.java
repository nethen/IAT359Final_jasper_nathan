package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditAccountActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Context context;
    SharedPreferences sharedPref;
    RadioGroup styleGroup;
    EditText nameEditText;
    Integer color;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting color theme
        context = this;
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Integer themeColor = sharedPref.getInt("COLOR", R.color.orange);
        if(themeColor == R.color.orange){
            this.setTheme(R.style.Theme_ArcanaMiniOrange);
        }else if(themeColor == R.color.green){
            this.setTheme(R.style.Theme_ArcanaMiniGreen);
        }else if(themeColor == R.color.pink){
            this.setTheme(R.style.Theme_ArcanaMiniPink);
        }
        setContentView(R.layout.activity_edit_account);

        saveButton = findViewById(R.id.accountSaveButton);
        saveButton.setOnClickListener(this);

        styleGroup = (RadioGroup) findViewById(R.id.accountStyleRadioGroup);
        styleGroup.setOnCheckedChangeListener(this);

        nameEditText = findViewById(R.id.accountNameEditText);
        String name = sharedPref.getString("NAME", "");
        nameEditText.setText(name);

        RadioButton orange = (RadioButton) findViewById(R.id.accountOrangeRadioButton);
        RadioButton green = (RadioButton) findViewById(R.id.accountGreenRadioButton);
        RadioButton pink = (RadioButton) findViewById(R.id.accountPinkRadioButton);
        color = themeColor;
        if(themeColor == R.color.orange){
            orange.setChecked(true);
        }else if(themeColor == R.color.green){
            green.setChecked(true);
        }else if (themeColor == R.color.pink){
            pink.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.accountOrangeRadioButton:
                //set primary color to orange
                color = R.color.orange;
                break;
            case R.id.accountGreenRadioButton:
                //set primary color to green
                color = R.color.green;
                break;
            case R.id.accountPinkRadioButton:
                //set primary color to pink
                color = R.color.pink;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.accountSaveButton)){
            //save info
            SharedPreferences.Editor e = sharedPref.edit();
            e.putString("NAME", nameEditText.getText().toString());
            e.apply();
            SharedPreferences.Editor e2 = sharedPref.edit();
            e2.putInt("COLOR", color);
            e2.apply();

            //finish activity and start MainActivity
            Intent intent = new Intent (this, MainActivity.class);
            this.startActivity(intent);
            finish();
        }
    }
}