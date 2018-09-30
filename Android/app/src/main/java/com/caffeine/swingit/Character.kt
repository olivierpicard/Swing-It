package com.caffeine.swingit

import android.graphics.Color
import android.graphics.Rect
import com.caffeine.swingit.Graphics.*

class Character(val scene: GameScene) :
        GSprite(R.drawable.bird, Color.WHITE, scene.CHARACTER_SIZE),
        IGUpdatable, IGCollisionListener
{
    var directionVector: GVector
    var isInCollision = mutableListOf<IGCollisionable>()
    var isFalling = false
    val lifebar: ProgressBar
    var enable = false

    init
    {
        directionVector = GVector.zero()
        reset()
        lifebar = ProgressBar(scene.CHARACTER_LIFE, GSize(50f, 6f))
        lifebar.position = GPoint(0f, -size.height/2 - lifebar.size.height * 2f)
        hide()
        addChild(lifebar)
    }


    override fun update(currentTime: Long)
    {
        if(!enable) { alpha = 0 ; lifebar.alpha = 0 ; return }

        alpha = 255
        lifebar.alpha = 255
        lifebar.value -= scene.CHARACTER_LIFE_DECREASE

        if(lifebar.value <= 0) isFalling = true
        else if(position.y + size.height < 0) isFalling = true

        if(!isFalling) {
            val yDirection = directionVector.dy
            position.y += scene.CHARACTER_SPEED * yDirection
            zRotation = if (yDirection > 0) scene.CHARACTER_ROTATION / 2 else -scene.CHARACTER_ROTATION / 2
        }
        else {
            lifebar.value = 0f
            // Is Falling
            zRotation = 50f
            if(position.y >= scene.terrain.terrainTopPos - size.height / 2)
                position.y = scene.terrain.terrainTopPos - size.height / 2
            position.y += scene.CHARACTER_SPEED * 2f
        }
    }


    override fun collisionEnter(collisionable: IGCollisionable)
    {
        if(collisionable == scene.terrain || collisionable is Ennemy) {
            if(collisionable is Ennemy) scene.removeChild(collisionable)
            isFalling = true
            if(scene.getGameState() != GameScene.GameState.GAME_OVER)
                scene.setFlagGameState(GameScene.GameState.GAME_OVER)
        } else if (collisionable is Bonus){
            scene.removeChild(collisionable as GNode)
            lifebar.value += scene.BONUS_VALUE
            scene.increaseScore()
        }
    }


    fun hide()
    {
        alpha = 0
        lifebar.alpha = 0
    }


    fun show()
    {
        alpha = 255
        lifebar.alpha = 255
    }


    fun reset()
    {
        directionVector = GVector.zero()
        position = GPoint(scene.size.width * scene.CHARACTER_XPOS,
                (scene.size.height - scene.terrain.terrainHeightPixel) / 2)
        lifebar?.value = lifebar.maxValue.toFloat()
        isFalling = false
    }


    override fun collisionExit(collisionable: IGCollisionable) {}
    override fun getCollisionItems(): MutableList<IGCollisionable>? { return isInCollision }
    override fun setCollisionItems(itemInCollision: MutableList<IGCollisionable>) { this.isInCollision = itemInCollision }
    override fun getBound(): Rect { return GTools.getRectFromSizeAndPos(position, size) }
}