package com.caffeine.swingit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.caffeine.swingit.Graphics.GActivity

class MainActivity : GActivity(R.id.viewController, R.layout.activity_main, GameScene::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
