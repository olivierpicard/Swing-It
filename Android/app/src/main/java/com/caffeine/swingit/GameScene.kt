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
    val CHARACTER_LIFE = 100 // Max life that player can have
    val BONUS_VALUE = 18 // Value that bonus can get you if you take it
    val BONUS_PROBABILITY = 0.75
    val CHARACTER_LIFE_DECREASE = 0.5f // character's life will be decreased each frame with this value
    val CLOUD_SIZE = GSize(70f, 40f) // Cloud size in the background
    val CLOUD_START_NUMBER = 4 // Number of cloud on the screen at start up
    val CLOUD_PROBABILITY = 0.5f

    var timelapseItemGeneration = 1000L
    var timelapseCloudGenerator = 2000L
    var gameState = GameState.PLAY

    lateinit var terrain: Terrain
    lateinit var bonusGenerator: BonusGenerator
    lateinit var character: Character
    lateinit var cloudGenerator: CloudGenerator


    override fun didInitialized()
    {
        terrain = Terrain(this)
        character = Character(this)
        bonusGenerator = BonusGenerator(this)
        cloudGenerator = CloudGenerator(this)

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
            cloudGenerator.update(currentTime)
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



















