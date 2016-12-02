package com.example.chango.sensores;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener{

    private SensorManager mSensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.
                FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.texto);
        view.setBackgroundColor(Color.GREEN);
        mSensorManager = (SensorManager)
                getSystemService (SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        List<Sensor> sensorList = mSensorManager.getSensorList
                (Sensor.TYPE_ALL);
        for(Sensor sensor : sensorList){
            Log.d("Sensor", sensor.getName());
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        synchronized(this) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3; i++) {
                    Log.d("SensorActivity", String.valueOf
                            (sensorEvent.values[i]));
                }
            }
        }
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            getAccelerometer(sensorEvent);
        }
    }
    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        float accelationSquareRoot = (x * x + y * y + z * z) /
                (SensorManager.GRAVITY_EARTH *
                        SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2){
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Han agitado el dispositivo",
                    Toast.LENGTH_SHORT).show();
            if (color) {
                view.setBackgroundColor(Color.GREEN);
            } else {
                view.setBackgroundColor(Color.RED);
            }
            color = !color;
        }
    }

    @Override
    protected void onResume() {
        super.onResume(); /* register this class as a listener for
                            the orientation and accelerometer sensors*/
        mSensorManager.registerListener(this,mSensorManager.
                        getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Sensor senAccelerometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, senAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}
