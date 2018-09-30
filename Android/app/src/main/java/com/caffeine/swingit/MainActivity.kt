package com.caffeine.swingit

import android.Manifest
import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.caffeine.swingit.Graphics.GActivity
import android.drm.DrmStore.Playback.RESUME
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.lang.Exception
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient




class MainActivity : GActivity(R.id.viewController, R.layout.activity_main, GameScene::class.java) {
    companion object {
        const val QRCODE_ACTIVITY_CODE = 1
        const val MAP_ACTIVITY_CODE = 2
        const val GPS_REQUEST = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getLocation()
        getLastLocationNewMethod()
        val conf = "cleared"
        if(conf.equals("rainy"))
            GameScene.weather =  GameScene.Weather.Rainy
        else if(conf.equals("cloudy"))
            GameScene.weather =  GameScene.Weather.Cloudy
        else if(conf.equals("cleared"))
            GameScene.weather =  GameScene.Weather.Cleared
        else if(conf.equals("stormy"))
            GameScene.weather =  GameScene.Weather.Stormy
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val conf = data?.extras?.getString("text")?.toLowerCase()
        if(conf.equals("rainy"))
            GameScene.weather =  GameScene.Weather.Rainy
        else if(conf.equals("cloudy"))
            GameScene.weather =  GameScene.Weather.Cloudy
        else if(conf.equals("cleared"))
            GameScene.weather =  GameScene.Weather.Cleared
        else if(conf.equals("stormy"))
            GameScene.weather =  GameScene.Weather.Stormy
    }


    fun getLocation() {
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.GPS_REQUEST)
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            println("sdsdksndjksd")
            return
        }
        println("1")
        val locationContext = Context.LOCATION_SERVICE
        val locationManager = getSystemService(locationContext) as LocationManager
        val provider = LocationManager.GPS_PROVIDER
        val location = locationManager.getLastKnownLocation(provider)
        val lat = location.latitude
        val lon = location.longitude
        println("$lat --- $lon")
        println("2")
        if (location != null) {
            println("3")
            val lat = location.latitude
            val lon = location.longitude
            println("$lat --- $lon")
        }
    }

    private fun getLastLocationNewMethod() {
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.GPS_REQUEST)
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            println("sdsdksndjksd")
            return
        }
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // GPS location can be null if GPS is switched off
                    if (location != null) {
                        val lat = location.latitude
                        val lon = location.longitude
                        println("$lat --- $lon")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("MapDemoActivity", "Error trying to get last GPS location")
                    e.printStackTrace()
                }
    }
}


