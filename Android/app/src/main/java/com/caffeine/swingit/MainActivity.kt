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
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.annotation.NonNull
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.caffeine.swingit.Graphics.GSize
import com.caffeine.swingit.Graphics.GTools
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*


class MainActivity : GActivity(R.id.viewController, R.layout.activity_main, GameScene::class.java) {
    companion object {
        const val QRCODE_ACTIVITY_CODE = 1
        const val MAP_ACTIVITY_CODE = 2
        const val GPS_REQUEST = 2
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        decryptString(GTools.readPref(R.string.bonusPref.toString()))
        decryptString(GTools.readPref(R.string.ennemyPref.toString()))
        decryptString(GTools.readPref(R.string.weatherPref.toString()))
//        val conf = "stormy"
//        decryptString(conf)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        val conf = data?.extras?.getString("text")?.toLowerCase()
        conf?.let { decryptString(it) }
    }



    fun decryptString(conf: String?)
    {
        if(conf == null) return

        if(conf.equals("rainy")) {
            GameScene.weather = GameScene.Weather.Rainy
            GTools.savePref(R.string.weatherPref.toString(), conf)
        }
        else if(conf.equals("cloudy")) {
            GameScene.weather = GameScene.Weather.Cloudy
            GTools.savePref(R.string.weatherPref.toString(), conf)
        }
        else if(conf.equals("cleared")) {
            GameScene.weather = GameScene.Weather.Cleared
            GTools.savePref(R.string.weatherPref.toString(), conf)
        }
        else if(conf.equals("stormy")) {
            GameScene.weather = GameScene.Weather.Stormy
            GTools.savePref(R.string.weatherPref.toString(), conf)
        }
        else if(conf.equals("nobombs")) {
            GameScene.ennemyProbability = 0f
            GTools.savePref(R.string.ennemyPref.toString(), conf)
        }
        else if(conf.equals("bombs")) {
            GameScene.ennemyProbability = GameScene.ENNEMY_PROBABILITY
            GTools.savePref(R.string.ennemyPref.toString(), conf)
        }
        else if(conf.equals("bread")) {
            GameScene.bonusID = R.drawable.bread
            GameScene.bonusSize = GSize(60f, 30f)
            GTools.savePref(R.string.bonusPref.toString(), conf)
        }
        else if(conf.equals("hamburger")) {
            GameScene.bonusID = R.drawable.hamburger
            GameScene.bonusSize = GSize(55f, 55f)
            GTools.savePref(R.string.bonusPref.toString(), conf)
        }
        else if(conf.equals("litchi")) {
            GameScene.bonusID = R.drawable.litchi
            GameScene.bonusSize = GameScene.BASE_SIZE
            GTools.savePref(R.string.bonusPref.toString(), conf)
        }
        else if(conf.equals("pork")) {
            GameScene.bonusID = R.drawable.pork
            GameScene.bonusSize = GSize(60f, 50f)
            GTools.savePref(R.string.bonusPref.toString(), conf)
        }
    }








}



