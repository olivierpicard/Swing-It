package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.GPoint
import com.caffeine.swingit.Graphics.GSize
import com.caffeine.swingit.Graphics.GSprite
import com.caffeine.swingit.Graphics.IGUpdatable

class Cloud(val scene: GameScene, size: GSize, position: GPoint) :
        GSprite(null, Color.WHITE, size, position),
        IGUpdatable
{
    init {
        alpha = 80
    }


    override fun update(currentTime: Long)
    {
        position.x -= scene.SPEED * 0.5f
    }

}