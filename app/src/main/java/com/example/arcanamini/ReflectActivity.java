package com.example.arcanamini;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ReflectActivity extends AppCompatActivity implements View.OnClickListener {
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;

    FragmentManager supportFragmentManager = getSupportFragmentManager();
    Button librariumButton;

//    final View androidRobotView = findViewById(R.id.bottom_nav);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.fragment_home, R.id.fragment_librarium, R.id.fragment_archive).build();
        Log.i("navHost", String.valueOf(navHostFragment));

        librariumButton = findViewById(R.id.goToLibrariumButton);
        librariumButton.setOnClickListener(this);

    }

    HomeFragment homeFragment = new HomeFragment();
    LibrariumFragment librariumFragment = new LibrariumFragment();
    ArchiveFragment archiveFragment = new ArchiveFragment();

    @Override
    public void onClick(View v) {
        if(v == findViewById(R.id.goToLibrariumButton)){
            Intent intent = new Intent (this, LibrariumActivity.class);
            this.startActivity(intent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Toast.makeText(this, String.valueOf(item), Toast.LENGTH_SHORT).show();
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }


}