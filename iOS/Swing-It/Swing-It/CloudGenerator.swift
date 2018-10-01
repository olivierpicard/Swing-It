//
//  CloudGenerator.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit


class CloudGenerator : IUpdatable
{
    let scene: GameScene
    let minBorne: Int
    let maxBorne: Int
    var lastGenerationTime: TimeInterval = 0
    
    init(scene: GameScene)
    {
        self.scene = scene
        let xStep = Int(scene.size.width / scene.CLOUD_START_NUMBER)
        maxBorne = Int(scene.size.height - scene.CLOUD_SIZE.height / 2)
        minBorne = Int(scene.terrain.TERRAIN_POSITION + scene.CLOUD_SIZE.height * 2)
        
        for i in stride(from: 0, to: Int(scene.size.width), by: xStep) {
            scene.addChild(Cloud(scene: scene,
                                 size: scene.CLOUD_SIZE,
                                 position: CGPoint(x: i,
                                                   y: Int.random(in: minBorne...maxBorne))))
        }
    }

    
    func update(_ currentTime: TimeInterval)
    {
        print("\(currentTime) --- \(lastGenerationTime) --- \(scene.timelapseCloudGenerator)")
        if (currentTime - lastGenerationTime > scene.timelapseCloudGenerator) {
            if (CGFloat.random(in: 0...1) > scene.CLOUD_PROBABILITY) { return }
            lastGenerationTime = currentTime
            scene.addChild(Cloud(scene: scene,
                                 size: scene.CLOUD_SIZE,
                                 position: CGPoint(x: Int(scene.size.width + scene.CLOUD_SIZE.width),
                                                   y: Int.random(in: minBorne...maxBorne))))
        }
    }
}
