//
//  GameScene.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import SpriteKit
import GameplayKit
import CoreMotion

class GameScene: SKScene
{
    enum GameState{
        case GAME_OVER
        case PLAY
        case WELCOME
        case NOT_INIT
    }
    
    enum Weather {
        case Rainy
        case Cloudy
        case Cleared
        case Stormy
    }
    
    
    static let ENNEMY_PROBABILITY: CGFloat = 0.005
    static let BASE_SIZE = CGSize(width: 60.0, height: 50.0)
    static var weather = Weather.Cleared
    static var ennemyProbability = ENNEMY_PROBABILITY
    static var bonusSize = BASE_SIZE
    static var cloudImageTexture = "cloud2"
    static var bonusImageTexture = "litchi"
    
    
    let SPEED: CGFloat = 5
    let BASE_TERRAIN_HEIGHT: CGFloat = 0.2 // Percentage of the screen that terrain take
    let UPPER_TERRAIN_HEIGHT: CGFloat = 0.25 // Percentage of base terrain that the upper terrain take
    let CHARACTER_SIZE = CGSize(width: 60, height: 40)
    let CHARACTER_XPOS: CGFloat = 0.2 // Percentage of screenWidth where character will be put on
    let CHARACTER_SPEED: CGFloat = 5 // Character displacement in pixel
    let CHARACTER_ROTATION: CGFloat = 25 // Rotation in degrees of the character when he moves
    let CHARACTER_LIFE: CGFloat = 200 // Max life that player can have
    let BONUS_VALUE: CGFloat = 17 // letue that bonus can get you if you take it
    let BONUS_PROBABILITY: CGFloat = 0.6
    let CHARACTER_LIFE_DECREASE: CGFloat = 0.5 // character's life will be decreased each frame with this letue
    let CLOUD_SIZE = CGSize(width: 140, height: 80) // Cloud size in the background
    let CLOUD_START_NUMBER: CGFloat = 4 // Number of cloud on the screen at start up
    var CLOUD_PROBABILITY: CGFloat = 0.5
    let RAIN_SPEED: CGFloat = 17
    let RAIN_SIZE = CGSize(width: 2, height: 20)
    let RAIN_SIZE_SMALL = CGSize(width: 1, height: 13)
    let THUNDERSTORM_DELAY = 1.5...3.0
    
    var timelapseItemGeneration: TimeInterval = 0.6
    var timelapseCloudGenerator: TimeInterval = 1
    private var swipeController: SwipeController!
    private var gameState = GameState.NOT_INIT
    private var isAccelerometerEnable = true
    private var itemCollisionable: [ICollisionableListener] = []
    private var motionManager: CMMotionManager!
    private var referenceMotionX: Double? = nil
    private var _isAccelerometerEnable = false
    
    private var cloudGenerator: CloudGenerator!
    private var bonusGenerator: BonusGenerator!
    private var ennemiesGenerator: EnnemiesGenerator!
    private var rainGenerator: RainGenerator!
    private var thunderstorm: Thunderstorm!
    private var character: Bird!
    private var score_label: SKLabelNode!
    private var welcomeScreen: WelcomeScreen!
    private var gameOverScreen: GameOverScreen!
    var terrain: Terrain!
    
    
    override func didMove(to view: SKView) {
        backgroundColor = skyColor()
        motionManager = CMMotionManager()
        motionManager.startAccelerometerUpdates()
        swipeController = SwipeController(callback: swipe)
        welcomeScreen = WelcomeScreen(scene: self)
        gameOverScreen = GameOverScreen(scene: self)
        terrain = Terrain.init(self)
        bonusGenerator = BonusGenerator(scene: self)
        cloudGenerator = CloudGenerator(scene: self)
        ennemiesGenerator = EnnemiesGenerator(scene: self)
        rainGenerator = RainGenerator(scene: self)
        thunderstorm = Thunderstorm(scene: self)
        character = Bird(scene: self)
        itemCollisionable.append(character as ICollisionableListener)
        
        score_label = SKLabelNode(text: "0")
        self.score_label.alpha = 0.8
        self.score_label.fontName = "HelveticaNeue-Light"
        self.score_label.fontSize = 55
        self.score_label.fontColor = UIColor.white
        self.score_label.isHidden = false
        score_label.position = CGPoint(x: frame.midX, y: size.height - (size.height * 0.3))
        setFlagGameState(_gameState: GameState.WELCOME)
        
        addChild(score_label)
        addChild(character)
        if(GameScene.weather == GameScene.Weather.Stormy) { addChild(thunderstorm) }
    }
    
    
    func skyColor() -> UIColor
    {
        var color: UIColor!
        if(GameScene.weather == GameScene.Weather.Cleared) {
            color = UIColor.init(red: 122/255.0, green: 221/255.0, blue: 1, alpha: 1)
            CLOUD_PROBABILITY = 0.005
        }
        else if(GameScene.weather == GameScene.Weather.Rainy) {
            color = UIColor.init(red: 140/255.0, green: 157/255.0, blue: 163/255.0, alpha: 1)
            CLOUD_PROBABILITY = 0.3
        }
        else if(GameScene.weather == GameScene.Weather.Cloudy) {
            color = UIColor.init(red: 174/255.0, green: 212/255.0, blue: 226/255.0, alpha: 1)
            CLOUD_PROBABILITY = 0.1
        }
        else if(GameScene.weather == GameScene.Weather.Stormy) {
            color = UIColor.init(red: 99/255.0, green: 110/255.0, blue: 119/255.0, alpha: 1)
            CLOUD_PROBABILITY = 0.4
        }
        return color
    }
    
    
    override func update(_ currentTime: TimeInterval) {
        cloudGenerator.update(currentTime)
        if(self.referenceMotionX == nil) { self.referenceMotionX = motionManager.accelerometerData?.acceleration.x }
        else if(_isAccelerometerEnable) { onAccelerometerEvent() }
        
        if(GameScene.weather == Weather.Stormy || GameScene.weather == Weather.Rainy) {
            rainGenerator.update(currentTime)
        }
        if(gameState == GameState.PLAY) {
            bonusGenerator.update(currentTime)
            ennemiesGenerator.update(currentTime)
        }
        
        for child in children {
            if (child as? IDeletable)?.canBeDeleted() ?? false { removeChildren(in: [child]); continue }
            (child as? IUpdatable)?.update(currentTime)
            if(child is ICollisionable) { collisionDetection(node: child as! ICollisionable) }
        }
    }
        
        
    func onAccelerometerEvent() {
        let xAclrt = motionManager.accelerometerData?.acceleration.x
        let sub = referenceMotionX! - xAclrt!
        let vector = CGVector(dx: 0, dy: sub)
        if(vector.dy != 0) { character.directionVector = vector.normalize() }
    }
    
    
    func collisionDetection(node: ICollisionable) {
        for indice in itemCollisionable.indices {
            var listener = itemCollisionable[indice]
            if(node is ICollisionableListener) { continue }
            if(listener.getFrame().intersects(node.getFrame()) && !listener.itemInCollisionWith.contains(node as! SKNode)) {
                listener.collisionEnter(node: node as! SKNode)
                listener.itemInCollisionWith.append(node as! SKNode)
            }
            else if(!listener.getFrame().intersects(node.getFrame()) && listener.itemInCollisionWith.contains(node as! SKNode)) {
                listener.itemInCollisionWith.remove(at: listener.itemInCollisionWith.firstIndex(of: node as! SKNode)!)
                listener.collisionExit(node: node as! SKNode)
            }
        }
    }
    
    
    func swipe(vectorIntermediate: CGVector, startPos: CGPoint, currentPos: CGPoint) {
        character.directionVector = vectorIntermediate.normalize()
    }
    
    
    func touchDown(atPoint pos : CGPoint) {
        swipeController.start(pos: pos)
        _isAccelerometerEnable = false
    }
    
    func touchMoved(toPoint pos : CGPoint) {
        swipeController.compute(newPos: pos)
    }
    
    func touchUp(atPoint pos : CGPoint) {
        _isAccelerometerEnable = true
        referenceMotionX = nil // Mark as reference
        if gameState == GameState.PLAY { swipeController.reset() }
        else if gameState == GameState.WELCOME { welcomeScreen.touchUp(pos) }
        else if gameState == GameState.GAME_OVER { gameOverScreen.touchUp(pos) }
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchDown(atPoint: t.location(in: self)) }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchMoved(toPoint: t.location(in: self)) }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchUp(atPoint: t.location(in: self)) }
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchUp(atPoint: t.location(in: self)) }
    }
    
    
    func getFlagGameState() -> GameState{
        return gameState
    }
    
    
    func setFlagGameState(_gameState: GameState)
    {
        if(gameState != GameState.NOT_INIT && gameState == _gameState) { return }
        gameState = _gameState
        if(_gameState == GameState.PLAY) {
            character?.reset()
            score_label.text = "0"
//            markAsAccelerometerReferencePosition()
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
}
