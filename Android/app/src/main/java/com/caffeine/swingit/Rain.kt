package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.*

class Rain(val speed: Float, size: GSize, pos: GPoint) :
        GSprite(null, Color.WHITE, size, pos),
        IGUpdatable,
        IGDeletable
{
    override fun update(currentTime: Long)
    {
        position.y += speed
    }


    override fun canBeDeleted(): Boolean
    {
        return position.y - size.height / 2 >= scene.size.height
    }
}