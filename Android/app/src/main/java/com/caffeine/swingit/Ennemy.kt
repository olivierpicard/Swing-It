package com.caffeine.swingit

import android.graphics.Color
import android.graphics.Rect
import com.caffeine.swingit.Graphics.*

class Ennemy(val scene: GameScene, position: GPoint) :
        GSprite(R.drawable.litchi, Color.GREEN, scene.BONUS_SIZE, position),
        IGUpdatable,
        IGDeletable,
        IGCollisionable
{
    override fun update(currentTime: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canBeDeleted(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBound(): Rect {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}