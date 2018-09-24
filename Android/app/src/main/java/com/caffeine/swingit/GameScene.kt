package com.caffeine.swingit

import com.caffeine.swingit.Graphics.*

class GameScene : GScene()
{
    enum class GameState{
        GAME_OVER,
        PLAY,
        START
    }

    val SPEED: Float = 5f
    val BASE_TERRAIN_HEIGHT = 0.2f // Percentage of the screen that terrain take
    val UPPER_TERRAIN_HEIGHT = 0.25f // Percentage of base terrain that the upper terrain take
    val CHARACTER_SIZE = GSize(30f, 30f)
    val CHARACTER_XPOS = 0.2f // Percentage of screenWidth where character will be put on
    val CHARACTER_SPEED = 5f // Character displacement in pixel
    val CHARACTER_ROTATION = 25f // Rotation in degrees of the character when he moves

    var timelapseItemGeneration = 1000L
    var gameState = GameState.PLAY

    lateinit var terrain: Terrain
    lateinit var bonusGenerator: BonusGenerator
    lateinit var character: Character


    override fun didInitialized()
    {
        terrain = Terrain(this)
        bonusGenerator = BonusGenerator(this)
        character = Character(this, terrain.terrainTopPos)
        addChild(terrain)
        addChild(character)

    }


    override fun start()
    {

    }


    override fun update(currentTime: Long)
    {
        if(gameState == GameState.PLAY) {
            bonusGenerator.update(currentTime)
            for (child: GNode in children) {
                if (child !is IGUpdatable) continue
                child.update(currentTime)
            }
        } else if(gameState == GameState.GAME_OVER) {

        }
    }


    override fun touchSwipe(vectorIntermediate: GVector, startPos: GPoint, currentPos: GPoint)
    {
        super.touchSwipe(vectorIntermediate, startPos, currentPos)
        character.swipeVector = vectorIntermediate
    }
}



















