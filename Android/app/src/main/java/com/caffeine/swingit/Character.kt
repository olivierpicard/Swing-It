package com.caffeine.swingit

import android.graphics.Color
import android.graphics.Rect
import com.caffeine.swingit.Graphics.*

class Character(val scene: GameScene) :
        GSprite(null, Color.WHITE, scene.CHARACTER_SIZE),
        IGUpdatable, IGCollisionListener
{
    var swipeVector: GVector
    var isInCollision = mutableListOf<IGCollisionable>()
    var isFalling = false
    val lifebar: ProgressBar

    init
    {
        swipeVector = GVector.zero()
        position = GPoint(scene.size.width * scene.CHARACTER_XPOS,
                (scene.size.height - scene.terrain.terrainHeightPixel) / 2)

        lifebar = ProgressBar(scene.CHARACTER_LIFE, GSize(50f, 6f))
        lifebar.position = GPoint(0f, -size.height/2 - lifebar.size.height * 2f)
        addChild(lifebar)
    }


    override fun update(currentTime: Long)
    {
        lifebar.value -= scene.CHARACTER_LIFE_DECREASE

        if(lifebar.value <= 0) isFalling = true
        else if(position.y + size.height < 0) isFalling = true

        if(!isFalling) {
            val yDirection = GVector.normalize(swipeVector).dy
            position.y += scene.CHARACTER_SPEED * yDirection
            zRotation = if (yDirection > 0) scene.CHARACTER_ROTATION / 2 else -scene.CHARACTER_ROTATION / 2
        }
        else {
            lifebar.value = 0f
            // Is Falling
            zRotation = -70f
            if(position.y >= scene.terrain.terrainTopPos - size.height / 2)
                position.y = scene.terrain.terrainTopPos - size.height / 2
            position.y += scene.CHARACTER_SPEED * 2f
        }
    }


    override fun collisionEnter(collisionable: IGCollisionable)
    {
        if(collisionable == scene.terrain) {
            isFalling = true
            scene.gameState = GameScene.GameState.GAME_OVER
        } else {
            scene.removeChild(collisionable as GNode)
            lifebar.value += scene.BONUS_VALUE
        }
    }


    override fun collisionExit(collisionable: IGCollisionable) {}
    override fun getCollisionItems(): MutableList<IGCollisionable>? { return isInCollision }
    override fun setCollisionItems(itemInCollision: MutableList<IGCollisionable>) { this.isInCollision = itemInCollision }
    override fun getBound(): Rect { return GTools.getRectFromSizeAndPos(position, size) }
}