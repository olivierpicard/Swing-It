//
//  EnnemiesGenerator.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit


class EnnemiesGenerator : IUpdatable
{
    var scene: GameScene
    let minBorne: Int
    let maxBorne: Int
    var lastGenerationTime: TimeInterval = 0
    
    
    init(scene: GameScene)
    {
        self.scene = scene
        maxBorne = Int(scene.size.height - GameScene.BASE_SIZE.height)
        minBorne = Int(scene.terrain.TERRAIN_POSITION + GameScene.BASE_SIZE.height * 2)
    }
    
    
    func update(_ currentTime: TimeInterval) {
        if (currentTime - lastGenerationTime > scene.timelapseItemGeneration) {
            if (CGFloat.random(in: 0...1) > GameScene.ennemyProbability) { return }
            lastGenerationTime = currentTime
            scene.addChild(Ennemy(scene: scene,
                                 size: GameScene.BASE_SIZE,
                                 position: CGPoint(x: Int(scene.size.width + GameScene.BASE_SIZE.width),
                                                   y: Int.random(in: minBorne...maxBorne))))
        }
    }
}
