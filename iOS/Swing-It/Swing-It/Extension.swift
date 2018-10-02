//
//  Extension.swift
//  Swing-It
//
//  Created by Olivier Picard on 02/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

extension CGVector {
    init(p1: CGPoint, p2: CGPoint) {
        let dx = p2.x - p1.x
        let dy = p2.y - p1.y
        self.init(dx: dx, dy: dy)
    }
    
    func normalize() -> CGVector {
        var normX = self.dx
        var normY = self.dy
        
        if(self.dx > 0){normX = 1}
        else if(self.dx < 0) {normX = -1}
        
        if(self.dy > 0){normY = 1}
        else if(self.dy < 0) {normY = -1}
        
        return CGVector(dx: normX, dy: normY)
    }
}

extension CGPoint {
    func distance(p: CGPoint) -> CGFloat {
        return sqrt(pow(self.x - p.x, 2) + pow(self.y - p.y, 2))
    }
}
