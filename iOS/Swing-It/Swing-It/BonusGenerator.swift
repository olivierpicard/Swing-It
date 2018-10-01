//
//  BonusGenerator.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit


class BonusGenerator : IUpdatable
{
    var scene: GameScene
    let minBorne: Int
    let maxBorne: Int
    var lastGenerationTime: TimeInterval = 0
    
    
    init(scene: GameScene)
    {
        self.scene = scene
        maxBorne = Int(scene.size.height - GameScene.bonusSize.height)
        minBorne = Int(scene.terrain.TERRAIN_POSITION + GameScene.bonusSize.height * 2)
    }
    
    
    func update(_ currentTime: TimeInterval) {
        if (currentTime - lastGenerationTime > scene.timelapseItemGeneration) {
            if (CGFloat.random(in: 0...1) > scene.BONUS_PROBABILITY) { return }
            lastGenerationTime = currentTime
            scene.addChild(Bonus(scene: scene,
                                 size: GameScene.bonusSize,
                                 position: CGPoint(x: Int(scene.size.width + GameScene.bonusSize.width),
                                                   y: Int.random(in: minBorne...maxBorne))))
        }
    }
}
