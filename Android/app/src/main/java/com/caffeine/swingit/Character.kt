package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.*

class Character(val scene: GameScene, groundHeight: Float) :
        GSprite(null, Color.WHITE, scene.CHARACTER_SIZE),
        IGUpdatable
{
    var swipeVector: GVector


    init {
        swipeVector = GVector.zero()
        position = GPoint(scene.size.width * scene.CHARACTER_XPOS,
                (scene.size.height - scene.terrainMaker.terrainHeightPixel) / 2)
    }


    override fun update(currentTime: Long)
    {
        val yDirection = GVector.normalize(swipeVector).dy
        position.y += scene.CHARACTER_SPEED * yDirection
        zRotation = if(yDirection > 0) scene.CHARACTER_ROTATION / 2 else -scene.CHARACTER_ROTATION / 2
        println("$yDirection --- $zRotation")
    }

}