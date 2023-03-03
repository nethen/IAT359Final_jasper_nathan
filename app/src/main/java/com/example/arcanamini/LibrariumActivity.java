package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;

public class LibrariumActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialCardView minorButton, majorButton, techniquesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarium);

        majorButton = findViewById(R.id.card_arcanaMajor);
        majorButton.setOnClickListener(this);
        minorButton = findViewById(R.id.card_arcanaMinor);
        minorButton.setOnClickListener(this);
        techniquesButton = findViewById(R.id.card_spreadsMethods);
        techniquesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.card_arcanaMajor)){
            Intent intent = new Intent (this, CardRecyclerActivity.class);
            int table = 0;
            intent.putExtra ("TABLE", table );
            this.startActivity(intent);
        } else if (v == findViewById(R.id.card_arcanaMinor)){
            Intent intent = new Intent (this, CardRecyclerActivity.class);
            int table = 1;
            intent.putExtra ("TABLE", table );
            this.startActivity(intent);
        }
        else if (v == findViewById(R.id.card_spreadsMethods)){
            Intent intent = new Intent (this, CardRecyclerActivity.class);
            int table = 2;
            intent.putExtra ("TABLE", table );
            this.startActivity(intent);
        }
    }
}