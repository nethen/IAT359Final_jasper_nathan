package com.example.arcanamini;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class CamActivity extends AppCompatActivity {
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;
    FragmentManager supportFragmentManager = getSupportFragmentManager();
    Button goOnboardButton;
    Context context;
    SharedPreferences sharedPref;
//    final View androidRobotView = findViewById(R.id.bottom_nav);

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
        setContentView(R.layout.activity_cam);
        context = this;

        bottomNavigationView = findViewById(R.id.bottom_nav);
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment_cam);
        NavController navController = navHostFragment.getNavController();
        bottomNavigationView.setSelectedItemId(R.id.activity_main);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.activity_main, R.id.activity_librarium2, R.id.activity_archive).build();
        Log.i("navHost", String.valueOf(navHostFragment));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_cam);
        Toast.makeText(this, String.valueOf(item), Toast.LENGTH_SHORT).show();
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }


}