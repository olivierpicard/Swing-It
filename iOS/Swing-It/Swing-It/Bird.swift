//
//  Chacracter.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class Bird : SKSpriteNode, IUpdatable
{
    var directionVector: CGVector!
    var isFalling = false
    var enable = true
    var _scene: GameScene!
    var lifeBar: ProgressBar!
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    
    init(scene: GameScene) {
        _scene = scene
        directionVector = CGVector.zero
        lifeBar = ProgressBar(maxValue: scene.CHARACTER_LIFE, size: CGSize(width: 50, height: 6))
        super.init(texture: SKTexture(imageNamed: "bird"),
                   color: UIColor.white,
                   size: scene.CHARACTER_SIZE)
        lifeBar.position = CGPoint(x: 0, y: -size.height/2 - lifeBar.size.height * 2)
        reset()
//        hide()
        addChild(lifeBar)
    }
    
    
    func hide() {
        self.alpha = 0
        lifeBar.alpha = 0
    }
    
    
    func reset() {
        directionVector = CGVector.zero
        self.position = CGPoint(x: _scene.size.width * _scene.CHARACTER_XPOS,
                                y: (_scene.size.height - _scene.terrain.TERRAIN_BOTTOM_LAYER_HEIGHT) / 2)
        lifeBar?.value = lifeBar.maxValue
        isFalling = false
    }
    
    
    func update(_ currentTime: TimeInterval) {
        if(!enable) { alpha = 0 ; lifeBar.alpha = 0 ; return }
        
        alpha = 1
        lifeBar.alpha = 1
        lifeBar.value -= _scene.CHARACTER_LIFE_DECREASE
        
        if(lifeBar.value <= 0) { isFalling = true }
        else if(position.y - size.height > _scene.size.height) { isFalling = true }
        
        if(!isFalling) {
            let yDirection = directionVector.dy
            position.y += _scene.CHARACTER_SPEED * yDirection
            zRotation = (yDirection > 0)
                ? CGFloat(GLKMathDegreesToRadians(Float(_scene.CHARACTER_ROTATION) / 2))
                : CGFloat(GLKMathDegreesToRadians(Float(-_scene.CHARACTER_ROTATION) / 2))
            if(yDirection == 0) { zRotation = 0 }
        }
        else {
            lifeBar.value = 0
            // Is Falling
            zRotation = CGFloat(GLKMathDegreesToRadians(-50))
            if(position.y <= _scene.terrain.TERRAIN_TOP_POS + size.height / 2) {
                position.y = _scene.terrain.TERRAIN_TOP_POS + size.height / 2
            }
            position.y -= _scene.CHARACTER_SPEED * 2
        }
    }
}
