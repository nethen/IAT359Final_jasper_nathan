package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button librariumButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        librariumButton = findViewById(R.id.goToLibrariumButton);
        librariumButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.goToLibrariumButton)){
            Intent intent = new Intent (this, LibrariumActivity.class);
            this.startActivity(intent);
        }
    }
}