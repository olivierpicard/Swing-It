package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.*
import kotlin.math.sin

class Thunderstorm(val scene: GameScene) :
        GSprite(null, Color.WHITE, scene.size, GPoint.center(scene.size)),
        IGUpdatable
{
    var lastTimeFlashed = 0L
    var randomFlashedDelay = scene.THUNDERSTORM_DELAY.random()
    var isFlashing = false
    var currentX = 0f
    val intervalFlash = GInterval(1, 4)
    var counterFlash = 0


    init {
        alpha = 0
    }


    override fun update(currentTime: Long)
    {
        if(!isFlashing) {
            if(currentTime - lastTimeFlashed > randomFlashedDelay) {
                randomFlashedDelay = scene.THUNDERSTORM_DELAY.random()
                isFlashing = true
                lastTimeFlashed = currentTime
                counterFlash = intervalFlash.randomInt()
                return
            }
        } else {
            lastTimeFlashed = currentTime
            flash()
        }
    }


    fun flash()
    {
        val y = (sin(currentX)*255f).toInt()
        alpha = y
        currentX += 0.9f
        if(y < 0) {
            currentX = 0f
            counterFlash--
            if(counterFlash == 0) isFlashing = false ;  alpha = 0
        }
    }
}