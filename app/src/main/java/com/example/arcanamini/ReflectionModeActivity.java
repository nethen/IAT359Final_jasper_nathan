package com.example.arcanamini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReflectionModeActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    SensorManager mySensorManager;
    Sensor myAccelerometer;
    TextView instruction1, instruction2;
    Boolean isFlat;
    Boolean vTrigger;
    Vibrator v;
    Button startButton, startButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection_mode);
        //reference to sensor
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        isFlat = false;
        vTrigger = true;
        instruction1 = findViewById(R.id.reflectionModeInstructionTextView);
        instruction2 = findViewById(R.id.reflectionModeInstruction2TextView);
        startButton = findViewById(R.id.reflectionModeStartButton);
        startButton.setOnClickListener(this);
        startButton2 = findViewById(R.id.reflectionModeStartButton2);
        startButton2.setOnClickListener(this);
        startButton2.setVisibility(View.GONE);
        //try vibrating
        try{
            v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        }catch(Exception e){
            //if device does not have vibration functionality, toast
            Toast.makeText(this, "Device flat", Toast.LENGTH_SHORT).show();
        }
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
            //get sensor values
            float[] accel_vals = event.values;
            Float accelYnum = accel_vals[1];
            Float accelZnum = accel_vals[2];

            //if lying flat
            if(accelYnum > -0.8 && accelYnum< 0.8 && accelZnum >= 9.78 && accelZnum <= 9.86){
                isFlat = true;
            } else{
                isFlat = false;
            }

            if(isFlat){
                //if flat and trigger is true
                if(vTrigger){
                    //trigger is false, code will run once

                    startButton2.setVisibility(View.VISIBLE);
                    startButton.setVisibility(View.GONE);

                }
            }else{


                startButton2.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View view) {
        if(view == findViewById(R.id.reflectionModeStartButton2)){
            //go back to new reflection
            //this.finish();
            if(isFlat){
                if(vTrigger){
                    vTrigger=false;
                    //one minute = 60000
                    SystemClock.sleep(6000);
                    //vibrate for 1 sec
                    v.vibrate(1000);
                    Toast.makeText(this, "vibrating", Toast.LENGTH_SHORT).show();
                    //return trigger to true after vibrating so that reflection timer
                    //can be activated again after 1 minute has passed if needed
                    vTrigger = true;
                }
            }else{
                //if not flat, return trigger to true
                vTrigger = true;
            }
        }
    }
}