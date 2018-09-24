package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.GPoint
import com.caffeine.swingit.Graphics.GSprite
import com.caffeine.swingit.Graphics.IGDeletable
import com.caffeine.swingit.Graphics.IGUpdatable

class Bonus(val scene: GameScene, position: GPoint) :
        GSprite(null, Color.GREEN, scene.CHARACTER_SIZE, position),
        IGUpdatable,
        IGDeletable
{
    override fun update(currentTime: Long)
    {
        position.x -= scene.SPEED
    }


    override fun canBeDeleted(): Boolean
    {
        return (position.x - size.width / 2) < 0
    }
}