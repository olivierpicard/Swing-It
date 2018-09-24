package com.caffeine.swingit

import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import com.caffeine.swingit.Graphics.*
import com.caffeine.swingit.Graphics.Shape.GRect

class Terrain(val scene: GameScene) :
        GSprite(null, Color.TRANSPARENT, GSize.zero()),
        IGCollisionable
{
    val terrainHeightPixel : Float
    val terrainTopPos : Float

    init {
        terrainHeightPixel = (scene.size.height * scene.BASE_TERRAIN_HEIGHT)
        terrainTopPos = scene.size.height - terrainHeightPixel

        addChild(GRect(RectF(
                0f,
                terrainTopPos,
                scene.size.width,
                scene.size.height
        ), Color.YELLOW))

        addChild(GRect(RectF(
                0f,
                terrainTopPos,
                scene.size.width,
                terrainTopPos + (terrainHeightPixel * scene.UPPER_TERRAIN_HEIGHT)
        ), Color.GREEN))
    }

    override fun getBound(): Rect
    {
        return Rect(0, terrainTopPos.toInt(),
                scene.size.width.toInt(), scene.size.height.toInt())
    }

}