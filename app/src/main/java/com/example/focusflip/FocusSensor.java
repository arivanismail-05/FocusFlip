package com.example.focusflip;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

// Detects when phone is placed face-down using accelerometer
public class FocusSensor implements SensorEventListener {

    public interface SensorCallback {
        void onFaceDown();
        void onFaceUp();
    }

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final SensorCallback callback;
    private boolean isFaceDown = false;

    public FocusSensor(Context context, SensorCallback callback) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callback = callback;
    }

    public void register() {
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float z = event.values[2];
        if (z < -8.5 && !isFaceDown) {
            isFaceDown = true;
            callback.onFaceDown();
        } else if (z > -8.5 && isFaceDown) {
            isFaceDown = false;
            callback.onFaceUp();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
