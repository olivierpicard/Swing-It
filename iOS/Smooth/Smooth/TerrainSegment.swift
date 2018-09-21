//
//  TerrainSegment.swift
//  Smooth
//
//  Created by Olivier Picard on 21/09/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class TerrainSegment : SKShapeNode
{
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    
    init(_ x: Int, _ y: Int, _ lastPoint: inout CGPoint?) {
        super.init()
        
        let path = CGMutablePath()
        if let lastPoint = lastPoint {
            path.move(to: CGPoint(x: lastPoint.x, y: lastPoint.y))
        } else {
            path.move(to: CGPoint(x: x, y: y))
        }
        path.addLine(to: CGPoint(x: x, y: y))
        
        self.path = path
        self.strokeColor = UIColor.white
        self.lineWidth = 3
        lastPoint = CGPoint(x: x, y: y)
    }
}
