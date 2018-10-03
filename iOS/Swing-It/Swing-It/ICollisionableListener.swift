//
//  ICollisionable.swift
//  Swing-It
//
//  Created by Olivier Picard on 02/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

protocol ICollisionableListener {
    var itemInCollisionWith: [SKNode] {get set}
    func collisionEnter(node: SKNode)
    func collisionExit(node: SKNode)
    func getFrame() -> CGRect
}
