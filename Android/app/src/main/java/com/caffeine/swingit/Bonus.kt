package com.caffeine.swingit

import android.graphics.Color
import android.graphics.Rect
import com.caffeine.swingit.Graphics.*

class Bonus(val scene: GameScene, position: GPoint) :
        GSprite(null, Color.GREEN, scene.CHARACTER_SIZE, position),
        IGUpdatable,
        IGDeletable,
        IGCollisionable
{
    override fun update(currentTime: Long)
    {
        position.x -= scene.SPEED
    }


    override fun canBeDeleted(): Boolean
    {
        return (position.x + size.width / 2 + 5) < 0
    }


    override fun getBound(): Rect { return GTools.getRectFromSizeAndPos(position, size) }
}