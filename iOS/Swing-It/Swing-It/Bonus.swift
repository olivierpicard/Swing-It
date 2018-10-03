//
//  Bonus.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class Bonus : SKSpriteNode, IUpdatable, IDeletable, ICollisionable
{
    let _scene: GameScene!
    static let contactTestBitMask = 3
    
    
    required init?(coder aDecoder: NSCoder) {
        _scene = nil
        super.init(coder: aDecoder)
    }
    
    
    init(scene: GameScene, size: CGSize, position: CGPoint)
    {
        _scene = scene
        super.init(texture: SKTexture(imageNamed: GameScene.bonusImageTexture),
                   color: UIColor.white,
                   size: size)
        self.position = position
    }
    
    
    func update(_ currentTime: TimeInterval) { position.x -= _scene.SPEED }
    func canBeDeleted() -> Bool { return (position.x + size.width/2 + 5 < 0) }
    func getFrame() -> CGRect { return frame }
}

