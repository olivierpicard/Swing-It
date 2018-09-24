package com.caffeine.swingit

import android.graphics.Color
import android.graphics.Rect
import com.caffeine.swingit.Graphics.*

class Character(val scene: GameScene, groundHeight: Float) :
        GSprite(null, Color.WHITE, scene.CHARACTER_SIZE),
        IGUpdatable, IGCollisionListener
{
    var swipeVector: GVector
    var isInCollision = mutableListOf<IGCollisionable>()

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
    }


    override fun collisionEnter(collisionable: IGCollisionable?)
    {

    }


    override fun collisionExit(collisionable: IGCollisionable?)
    {

    }


    override fun getCollisionItems(): MutableList<IGCollisionable>? { return isInCollision }
    override fun setCollisionItems(itemInCollision: MutableList<IGCollisionable>) { this.isInCollision = itemInCollision }
    override fun getBound(): Rect { return GTools.getRectFromSizeAndPos(position, size) }
}