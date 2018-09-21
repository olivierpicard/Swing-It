package com.olivierpicard.smooth

import android.os.Bundle
import com.olivierpicard.smooth.Graphics.GActivity

class MainActivity : GActivity(R.id.viewController, R.layout.activity_main, GameScene::class.java) {
//class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
    }
}
