//
//  TerrainCreator.swift
//  Smooth
//
//  Created by Olivier Picard on 20/09/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class TerrainGenerator {
    let scene: SKScene!
    var lasPoint: CGPoint?

    
    
    required init(_ scene: SKScene) {
        self.scene = scene
        currentX = 0;
        generate()
    }
    
    
    func generate() {
        for i in 0...Int(scene.size.width) {
            scene.addChild(TerrainSegment(i,
                                          Int( cos(0.01 * Double(i)) * 100.0),
                                          &lasPoint))
            currentX = i;
        }
    }
    
    func move()
    {
        
    }
    
    private func addPoint()
    {
        
    }
    
}
