//
//  GameScene.swift
//  Smooth
//
//  Created by Olivier Picard on 20/09/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import SpriteKit
import GameplayKit

class GameScene: SKScene {
    private var terrainGenerator: TerrainGenerator!
    
    
    override func didMove(to view: SKView) {
        terrainGenerator = TerrainGenerator(self)
    }
    
    
    func touchDown(atPoint pos : CGPoint) {
       
    }
    
    
    func touchMoved(toPoint pos : CGPoint) {
       
    }
    
    func touchUp(atPoint pos : CGPoint) {
       
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
    
    
    override func update(_ currentTime: TimeInterval) {
        // Called before each frame is rendered
    }
}
