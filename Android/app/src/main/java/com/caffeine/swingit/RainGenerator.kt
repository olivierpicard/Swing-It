package com.caffeine.swingit

import com.caffeine.swingit.Graphics.GInterval
import com.caffeine.swingit.Graphics.GPoint
import com.caffeine.swingit.Graphics.IGUpdatable

class RainGenerator(val scene: GameScene) : IGUpdatable
{
    private var lastGenerationTime = 0L
    private val intervalGeneration = GInterval(0f, scene.size.width)


    init {

    }


    override fun update(currentTime: Long)
    {
        if(currentTime - lastGenerationTime > 0L) {
            lastGenerationTime = currentTime
            scene.addChild(Rain(
                    scene.RAIN_SPEED,
                    scene.RAIN_SIZE,
                    GPoint(intervalGeneration.random(), -scene.RAIN_SIZE.height/2)
            ))

            scene.addChild(Rain(
                    scene.RAIN_SPEED * .8f,
                    scene.RAIN_SIZE_SMALL,
                    GPoint(intervalGeneration.random(), -scene.RAIN_SIZE_SMALL.height/2)
            ))
        }
    }
}