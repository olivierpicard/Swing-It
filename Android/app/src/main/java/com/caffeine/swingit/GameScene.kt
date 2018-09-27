package com.caffeine.swingit

import android.graphics.Color
import com.caffeine.swingit.Graphics.*
import com.caffeine.swingit.Graphics.GPoint
import com.caffeine.swingit.Graphics.GTools
import com.caffeine.swingit.GameScene.GameState







class GameScene : GScene()
{
    enum class GameState{
        GAME_OVER,
        PLAY,
        WELCOME
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
    val RAIN_SPEED = 17f
    val RAIN_SIZE = GSize(2f, 20f)
    val RAIN_SIZE_SMALL = GSize(1f, 13f)
    val THUNDERSTORM_DELAY = GInterval(1500f, 3000f)

    var timelapseItemGeneration = 1000L
    var timelapseCloudGenerator = 2000L
    private var gameState = GameState.WELCOME
    private var isAccelerometerEnable = true

    lateinit var terrain: Terrain
    private lateinit var bonusGenerator: BonusGenerator
    private lateinit var character: Character
    private lateinit var cloudGenerator: CloudGenerator
    private lateinit var rainGenerator: RainGenerator
    private lateinit var thunderstorm: Thunderstorm
    private lateinit var welcomeScreen: WelcomeScreen
    private lateinit var score_label: GLabel
    private lateinit var gameOverScreen: GameOverScreen


    override fun didInitialized()
    {
        welcomeScreen = WelcomeScreen(this)
        terrain = Terrain(this)
        character = Character(this)
        bonusGenerator = BonusGenerator(this)
        cloudGenerator = CloudGenerator(this)
        rainGenerator = RainGenerator(this)
        thunderstorm = Thunderstorm(this)
        gameOverScreen = GameOverScreen(this)
        score_label = GLabel("0")
        score_label.fontSize = 55
        score_label.color = Color.WHITE
        score_label.alpha = 125
        score_label.isHidden = false
        score_label.position = GTools.fromSceneToScreenPos(this.size, GPoint(0.5f, 0.8f))
        setFlagGameState(GameState.WELCOME)

        addChild(thunderstorm)
        addChild(terrain)
        addChild(character)
        addChild(score_label)
    }


    override fun start()
    {

    }


    override fun update(currentTime: Long)
    {
        cloudGenerator.update(currentTime)
        rainGenerator.update(currentTime)
        if(gameState == GameState.PLAY) {
            bonusGenerator.update(currentTime)
        }

        for (child: GNode in children) {
            if (child !is IGUpdatable) continue
            child.update(currentTime)
        }
    }


    override fun touchSwipe(vectorIntermediate: GVector, startPos: GPoint, currentPos: GPoint)
    {
        super.touchSwipe(vectorIntermediate, startPos, currentPos)
        character.directionVector = GVector.normalize(vectorIntermediate)
    }


    override fun touchUp(pos: GPoint)
    {
        super.touchUp(pos)
        isAccelerometerEnable = true
        markAsAccelerometerReferencePosition()
        if(gameState == GameState.WELCOME) welcomeScreen.touchUp(pos)
        else if(gameState == GameState.GAME_OVER) gameOverScreen.touchUp(pos)
    }


    override fun touchDown(pos: GPoint) {
        super.touchDown(pos)
        isAccelerometerEnable = false
    }


    override fun onAccelerometerEvent(axisValues: FloatArray)
    {
        super.onAccelerometerEvent(axisValues)
        if(!isAccelerometerEnable || gameState != GameState.PLAY) return
        val directionVector = GVector(axisValues[1], axisValues[0])
        if(directionVector.dy != 0f)
            character.directionVector = GVector.normalize(directionVector)
    }


    fun increaseScore()
    {
        score_label.text = (score_label.text.toInt() + 1).toString()
    }


    fun setFlagGameState(_gameState: GameState)
    {
        println(_gameState)
        this.gameState = _gameState
        if(_gameState == GameState.PLAY) {
            character?.reset()
            score_label.text = "0"
            markAsAccelerometerReferencePosition()
            gameOverScreen.hide()
            welcomeScreen.hide()
            character.enable = true
            score_label.isHidden = false
        } else if(_gameState == GameState.WELCOME) {
            character.reset()
            score_label.text = "0"
            character.enable = false
            gameOverScreen.hide()
            welcomeScreen.show()
            score_label.isHidden = true
        } else if(_gameState == GameState.GAME_OVER) {
            welcomeScreen.hide()
            gameOverScreen.show()
        }
    }


    fun getGameState(): GameState
    {
        return gameState
    }
}



















