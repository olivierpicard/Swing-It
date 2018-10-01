//
//  Terrain.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class Terrain {
    private let scene: SKScene!
    let TERRAIN_BOTTOM_LAYER_HEIGHT: CGFloat = 0.2
    let TERRAIN_TOP_LAYER_HEIGHT:  CGFloat = 0.2
    let TERRAIN_POSITION: CGFloat
    
    init(_ scene: SKScene) {
        self.scene = scene
        let realTerrainHeight = scene.size.height * TERRAIN_BOTTOM_LAYER_HEIGHT
        let realTopTerrainHeight = realTerrainHeight * TERRAIN_TOP_LAYER_HEIGHT
        
        TERRAIN_POSITION = realTerrainHeight/2
        
        let bottomLayer = SKSpriteNode(
            color: UIColor.init(red: 109/255.0, green: 94/255.0, blue: 77/255.0, alpha: 1),
            size: CGSize(width: scene.size.width, height: realTerrainHeight))
        
        let topLayer = SKSpriteNode(
            color: UIColor.init(red: 162/255.0, green: 183/255.0, blue: 115/255.0, alpha: 1),
            size: CGSize(width: scene.size.width, height: realTopTerrainHeight))
        
        bottomLayer.position = CGPoint.init(x: scene.frame.midX,
                                            y: TERRAIN_POSITION)
        
        topLayer.position = CGPoint.init(x: scene.frame.midX,
                                         y: TERRAIN_POSITION * 2 + topLayer.size.height/2)
        
        scene.addChild(bottomLayer)
        scene.addChild(topLayer)
    }
}
