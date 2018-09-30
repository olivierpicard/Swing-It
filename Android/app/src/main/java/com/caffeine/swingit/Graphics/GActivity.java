package com.caffeine.swingit.Graphics;

/**
 * Created by olivierpicard on 16/04/2018.
 */

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class GActivity extends AppCompatActivity
        implements IGActivitySwitchable, SensorEventListener
{
    private GSceneViewController sceneViewController;
    private int sceneViewControllerID, contentView;
    private Class sceneClass;
    private SensorManager sensorManager;
    private List<Sensor> sensors;


    protected GActivity(int sceneViewControllerID, int contentView, Class sceneClass) {
        this.sceneViewControllerID = sceneViewControllerID;
        this.sceneClass = sceneClass;
        this.contentView = contentView;
        this.sensors = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(this.contentView);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        GTools.resources = getResources();
        GTools.activitySwitcher = this;
        GTools.activity = this;

        // Récupération de la taille de l'écran et la stock dans GTools
        this.getWindowManager().getDefaultDisplay().getMetrics(GTools.screenMetrics);
        sceneViewController = findViewById(this.sceneViewControllerID);
        sceneViewController.confScene(this.sceneClass);
    }


    protected void onResume()
    {
        super.onResume();
        // Dès que le jeu reprend ou commence on débute l'écoute de tous les sensors
        for(Sensor sensor : sensors)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }


    protected void onPause()
    {
        super.onPause();
        // On arrête l'écoute de tous les sensors quand le jeu est en arrière plan
        sensorManager.unregisterListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sceneViewController.onTouch(event);
        return super.onTouchEvent(event);
    }


    @Override
    public void switchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void switchActivity(Class activity, String Message) {}


    @Override
    public void switchActivityWithResult(Class activity, int requestCode) {
        Intent intent = new Intent(this, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        sceneViewController.onAccelerometer(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
    @Override
    public void switchActivityWithResult(Class activity, String Message, int requestCode){}
    public GScene getScene() { return sceneViewController.getScene(); }
    protected void addSensor(Sensor sensor)
    {
        sensors.add(sensor);
    }
    protected SensorManager getSensorManager() { return sensorManager; }
}
