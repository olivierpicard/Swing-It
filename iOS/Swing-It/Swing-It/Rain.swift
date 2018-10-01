//
//  Rain.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class Rain : SKSpriteNode, IUpdatable, IDeletable
{
    let _scene: GameScene!
    let _speed: CGFloat!
    
    required init?(coder aDecoder: NSCoder) {
        _scene = nil
        _speed = nil
        super.init(coder: aDecoder)
    }
    
    
    init(scene: GameScene, size: CGSize, position: CGPoint, speed: CGFloat, opacity: CGFloat)
    {
        _scene = scene
        _speed = speed
        super.init(texture: nil,
                   color: UIColor.white,
                   size: size)
        self.position = position
        self.alpha = opacity
    }
    
    
    func update(_ currentTime: TimeInterval) { position.y -= _speed}
    func canBeDeleted() -> Bool { return (position.y + size.width/2 + 5 < 0) }
}
