package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.GPoint
import com.caffeine.swingit.Graphics.Shape.GRect
import com.caffeine.swingit.Graphics.GScene
import com.caffeine.swingit.Graphics.GSize

class GameScene : GScene()
{
    val SPEED: Float = 2f

    override fun didInitialized()
    {
        addChild(GRect(
                GPoint(50f, 50f),
                GSize(30f, 30f),
                Color.WHITE)
        )
    }

    override fun start()
    {

    }

    override fun update(currentTime: Long) {

    }
}