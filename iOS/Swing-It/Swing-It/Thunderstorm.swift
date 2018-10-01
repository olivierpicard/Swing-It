//
//  Thunderstorm.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class Thunderstorm : SKSpriteNode, IUpdatable
{
    var _scene: GameScene!
    var randomFlashDelay: TimeInterval!
    var lastGenerationTime: TimeInterval = 0
    var isFlashing = false
    var currentX: Double = 0
    var intervalFlash = 1...3
    var counterFlash = 0
    
    
    required init?(coder aDecoder: NSCoder) {
        _scene = nil
        randomFlashDelay = nil
        super.init(coder: aDecoder)
    }
    
    
    init(scene: GameScene)
    {
        self._scene = scene
        randomFlashDelay = Double.random(in: _scene.THUNDERSTORM_DELAY)
        super.init(texture: nil,
                   color: UIColor.white,
                   size: _scene.size)
        self.position = CGPoint(x: _scene.frame.midX, y: _scene.frame.midY)
        self.zPosition = 10
    }
    
    
    func update(_ currentTime: TimeInterval)
    {
        if(!isFlashing)
        {
            if (currentTime - lastGenerationTime > randomFlashDelay) {
                randomFlashDelay = Double.random(in: _scene.THUNDERSTORM_DELAY)
                isFlashing = true
                lastGenerationTime = currentTime
                counterFlash = Int.random(in: intervalFlash)
            }
        } else {
            lastGenerationTime = currentTime
            flash()
        }
    }
    
    
    func flash()
    {
        let y = sin(0.45*currentX)
        self.alpha = CGFloat(y)
        currentX += 0.9
        if(y < 0) {
            currentX = 0
            counterFlash -= 1
            if(counterFlash == 0) {isFlashing = false ;  self.alpha = 0}
        }
    }
}
