package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReflectionModeActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    SensorManager mySensorManager;
    Sensor myAccelerometer;
    Boolean isFlat;
    Boolean vTrigger;
    Vibrator v;
    Button startButton;
    Date initTime;

    TextView timer, back;
    int timerVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reflect_mode);
        //reference to sensor
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        isFlat = false;
        vTrigger = false;
        back = findViewById(R.id.reflect_backReadings);
        startButton = findViewById(R.id.button_reflect_start);
        timer = findViewById(R.id.reflect_timer);
        timerVal = 60;
        timer.setText(String.valueOf(timerVal));
        timer.setAlpha((float) 0.3);
        startButton.setAlpha((float) 0.3);
        back.setOnClickListener(this);
        startButton.setOnClickListener(this);
        //try vibrating
        try{
            v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        }catch(Exception e){
            //if device does not have vibration functionality, toast
            Toast.makeText(this, "Device flat", Toast.LENGTH_SHORT).show();
        }
        initTime = Calendar.getInstance().getTime();
    }

    @Override
    public void onResume(){
        super.onResume();
        //register listener
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        //unregister listener
        mySensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if(type == Sensor.TYPE_ACCELEROMETER)
        {

            float accelVals[] = event.values;
            if (Math.abs(9.5 - Math.abs(accelVals[2])) < 0.75){
                if(Math.abs(0.25 - Math.abs(accelVals[0])) < 0.25 && Math.abs(0.25 - Math.abs(accelVals[1])) < 0.25){
                    isFlat = true;
                }
            } else {
                isFlat = false;
            }

            if(isFlat){
                timer.setAlpha(1);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Date currentTime = Calendar.getInstance().getTime();
                        if ((currentTime.getTime() - initTime.getTime()) > 1000) {
                            initTime = currentTime;
                            if (timerVal > 0) timerVal --;
                            timer.setText(String.valueOf(timerVal));
                        }
                    }
                });
                t.start();
                //if flat and trigger is true
                if(timerVal == 0 && !vTrigger){
                    v.vibrate(VibrationEffect.createOneShot (1000, VibrationEffect.DEFAULT_AMPLITUDE));
                    vTrigger = true;
                    //trigger is false, code will run once
                   startButton.setAlpha(1);

                }
            }else{

            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.button_reflect_start)){
            //go back to new reflection
            Intent i = new Intent(this, MainActivity.class);

            Calendar calendar = Calendar.getInstance();
            Object dateVal = calendar.getTime();
            String date = new SimpleDateFormat("MMM d, yyyy").format(dateVal);
            String hourMin = new SimpleDateFormat("hh:mm aaa").format(dateVal);
            ReadDatabaseHelper databaseHelper = new ReadDatabaseHelper(this, date);
            databaseHelper.getWritableDatabase();


            databaseHelper.insertDataReflection(date,hourMin);
            ArrayList<String> list = databaseHelper.getItemsWithContent();

            i.putExtra("FORCE_CAM", true);
            i.putExtra("ITEM_KEY", list.size()-1);
            i.putExtra("ITEM_DATE", date);
            this.finish();
            startActivity(i);

        }
        if (view == findViewById(R.id.reflect_backReadings)){
            Intent i = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(i);
        }
    }

}