package com.caffeine.swingit

import android.graphics.Color
import android.graphics.RectF
import com.caffeine.swingit.Graphics.Shape.GRect

class TerrainMaker(val scene: GameScene)
{
    val terrainHeightPixel : Float
    val terrainTopPos : Float

    init {
        terrainHeightPixel = (scene.size.height * scene.BASE_TERRAIN_HEIGHT)
        terrainTopPos = scene.size.height - terrainHeightPixel

        scene.addChild(GRect(RectF(
                0f,
                terrainTopPos,
                scene.size.width,
                scene.size.height
        ), Color.YELLOW))

        scene.addChild(GRect(RectF(
                0f,
                terrainTopPos,
                scene.size.width,
                terrainTopPos + (terrainHeightPixel * scene.UPPER_TERRAIN_HEIGHT)
        ), Color.GREEN))
    }
}