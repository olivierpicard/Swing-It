//
//  RainGenerator.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class RainGenerator : IUpdatable
{
    var scene: GameScene
    var lastGenerationTime: TimeInterval = 0
    
    
    init(scene: GameScene)
    {
        self.scene = scene
    }
    
    
    func update(_ currentTime: TimeInterval) {
        lastGenerationTime = currentTime
        scene.addChild(Rain(scene: scene,
                             size: scene.RAIN_SIZE,
                             position: CGPoint(x: CGFloat.random(in: 0...scene.size.width),
                                               y: scene.size.height + scene.RAIN_SIZE.height),
                             speed: scene.RAIN_SPEED,
                             opacity: 1))
        
        scene.addChild(Rain(scene: scene,
                            size: scene.RAIN_SIZE_SMALL,
                            position: CGPoint(x: CGFloat.random(in: 0...scene.size.width),
                                              y: scene.size.height + scene.RAIN_SIZE_SMALL.height),
                            speed: scene.RAIN_SPEED * 0.8,
                            opacity: 0.7))
    }
}
