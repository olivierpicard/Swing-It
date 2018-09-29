package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.*

class Cloud(val scene: GameScene, size: GSize, position: GPoint) :
        GSprite(if(GameScene.weather == GameScene.Weather.Cleared || GameScene.weather == GameScene.Weather.Cloudy) R.drawable.cloud2 else R.drawable.cloud, Color.WHITE, size, position),
        IGUpdatable,
        IGDeletable
{
    init {
        alpha = 200
    }


    override fun update(currentTime: Long)
    {
        position.x -= scene.SPEED * 0.5f
    }


    override fun canBeDeleted(): Boolean { return (position.x + size.width / 2 + 5) < 0 }
}