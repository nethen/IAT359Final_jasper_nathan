package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LibrariumActivity extends AppCompatActivity implements View.OnClickListener {
    Button minorButton, majorButton, techniquesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarium);

        minorButton = findViewById(R.id.minorButton);
        minorButton.setOnClickListener(this);
        majorButton = findViewById(R.id.majorButton);
        majorButton.setOnClickListener(this);
        techniquesButton = findViewById(R.id.techniquesButton);
        techniquesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.minorButton)){

        } else if (v == findViewById(R.id.majorButton)){

        } else if (v == findViewById(R.id.techniquesButton)){

        }
    }
}