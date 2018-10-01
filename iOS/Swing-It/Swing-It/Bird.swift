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
    var enable = false
    var _scene: GameScene!
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    
    init(scene: GameScene) {
        _scene = scene
        directionVector = CGVector.zero
        super.init(texture: SKTexture(imageNamed: "bird"),
                   color: UIColor.white,
                   size: scene.CHARACTER_SIZE)
        reset()
    }
    
    
    func hide() {
        self.alpha = 0
    }
    
    
    func reset() {
        directionVector = CGVector.zero
        self.position = CGPoint(x: _scene.size.width * _scene.CHARACTER_XPOS,
                                y: (_scene.size.height - _scene.terrain.TERRAIN_BOTTOM_LAYER_HEIGHT) / 2)
        isFalling = false
    }
    
    
    func update(_ currentTime: TimeInterval) {
        
    }
}
