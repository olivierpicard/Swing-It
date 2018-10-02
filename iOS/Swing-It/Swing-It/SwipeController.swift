//
//  SwipeGesture.swift
//  Swing-It
//
//  Created by Olivier Picard on 02/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class SwipeController {
    let THRESHOLD_DISTANCE: CGFloat = 10 // Distance que le doight parcours avant qu'un point intermédiaire est ajouté
    let callback: (CGVector, CGPoint, CGPoint)->()
    var firstPos = CGPoint.zero
    var intermediatePos = CGPoint.zero
    var vectorSwipe = CGVector.zero
    
    init(callback: @escaping (CGVector, CGPoint, CGPoint)->()) {
        self.callback = callback
    }
    
    
    func compute(newPos: CGPoint) {
        if(intermediatePos == CGPoint.zero) { return }
        vectorSwipe = CGVector(p1: intermediatePos, p2: newPos)
        if intermediatePos.distance(p: newPos) > THRESHOLD_DISTANCE {
            intermediatePos = newPos;
        }
        self.callback(vectorSwipe, firstPos, newPos)
    }
    
    
    func reset() {
        firstPos = CGPoint.zero
        vectorSwipe = CGVector.zero
        intermediatePos = CGPoint.zero
    }
    
    
    func start(pos: CGPoint) {
        firstPos = pos
        intermediatePos = pos
    }
}
