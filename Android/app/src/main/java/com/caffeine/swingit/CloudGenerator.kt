package com.caffeine.swingit

import com.caffeine.swingit.Graphics.GInterval
import com.caffeine.swingit.Graphics.GPoint
import com.caffeine.swingit.Graphics.IGUpdatable

class CloudGenerator(val scene : GameScene) : IGUpdatable
{
    val intervalWidth = GInterval(0f, scene.size.width + scene.CLOUD_SIZE.width / 2)
    val intervalHeight = GInterval(scene.CLOUD_SIZE.height / 2, scene.terrain.terrainTopPos - scene.CLOUD_SIZE.height * 2)
    var lastGenerationTime: Long = 0L

    init {
        val xStep = (scene.size.width / scene.CLOUD_START_NUMBER).toInt()
        for(i in 0..scene.size.width.toInt() step xStep) {
            scene.addChild(Cloud(scene, scene.CLOUD_SIZE, GPoint(i.toFloat(), intervalHeight.random())))
        }
    }


    override fun update(currentTime: Long)
    {
        if (currentTime - lastGenerationTime > scene.timelapseCloudGenerator) {
            if (GInterval.random(0f, 1f) > scene.CLOUD_PROBABILITY) return
            lastGenerationTime = currentTime
            scene.addChild(Cloud(
                    scene,
                    scene.CLOUD_SIZE,
                    GPoint(scene.size.width + scene.CLOUD_SIZE.width,
                            intervalHeight.random())
            ))
        }
    }
}