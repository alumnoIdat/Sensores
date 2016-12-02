package com.example.chango.sensores;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)
                getSystemService (SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList
                (Sensor.TYPE_ALL);
        for(Sensor sensor : sensorList)
            Log.d("Sensor", sensor.getName());
        
    }
}
