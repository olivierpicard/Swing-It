//
//  WelcomScreen.swift
//  Swing-It
//
//  Created by Olivier Picard on 03/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class WelcomeScreen {
    let scene: GameScene
    let title_label: SKLabelNode
    let play_button: Button
    let resume_button: Button
    let score_button: Button
    
    init(scene: GameScene) {
        self.scene = scene
        
        self.title_label = SKLabelNode.init(text: "Swing it")
        self.title_label.alpha = 0.5
        self.title_label.fontName = "HelveticaNeue-Light"
        self.title_label.fontSize = 55
        self.title_label.fontColor = UIColor.white
        
        self.play_button = Button(text: "Jouer")
        self.resume_button = Button(text: "Reprendre")
        self.score_button = Button(text: "Scores")
        
        self.title_label.position = CGPoint(x: scene.frame.midX, y: scene.size.height - (scene.size.height * 0.3))
        self.play_button.position = CGPoint(x: scene.frame.midX, y: scene.size.height - (scene.size.height * 0.5))
        
        self.resume_button.position = CGPoint(x: self.play_button.position.x,
                                              y: self.play_button.position.y - self.play_button.size.height - 30)
        
    }
    
    func show() {
        self.scene.addChild(self.title_label)
        self.scene.addChild(self.play_button)
        self.scene.addChild(self.resume_button)
    }
    
    func hide() {
        self.scene.removeChildren(in: [self.title_label,
                                       self.play_button,
                                       self.resume_button])
    }
    
    func touchUp(_ pos: CGPoint) {
        if self.play_button.isClicked(pos) {scene.setFlagGameState(_gameState: GameScene.GameState.PLAY) }
//        else if self.resume_button.isClicked(pos) {
//            scene.view_Controller.performSegue(withIdentifier: "Resume", sender: nil)
//        }
    }
}
