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
    let experience_button: Button
    let map_button: Button
    
    init(scene: GameScene) {
        self.scene = scene
        
        self.title_label = SKLabelNode.init(text: "Swing it")
        self.title_label.alpha = 0.5
        self.title_label.fontName = "HelveticaNeue-Light"
        self.title_label.fontSize = 55
        self.title_label.fontColor = UIColor.white
        
        self.play_button = Button(text: "Jouer")
        self.experience_button = Button(text: "Experience")
        self.map_button = Button(text: "Position")
        
        self.title_label.position = CGPoint(x: scene.frame.midX, y: scene.size.height - (scene.size.height * 0.3))
        self.play_button.position = CGPoint(x: scene.frame.midX, y: scene.size.height - (scene.size.height * 0.45))
        
        self.experience_button.position = CGPoint(x: self.play_button.position.x,
                                              y: self.play_button.position.y - self.play_button.size.height - 15)
        
        self.map_button.position = CGPoint(x: self.experience_button.position.x,
                                             y: self.experience_button.position.y - self.experience_button.size.height - 15)
        
    }
    
    func show() {
        self.scene.addChild(self.title_label)
        self.scene.addChild(self.play_button)
        self.scene.addChild(self.experience_button)
        self.scene.addChild(self.map_button)
    }
    
    func hide() {
        self.scene.removeChildren(in: [self.title_label,
                                       self.play_button,
                                       self.experience_button,
                                       self.map_button])
    }
    
    func touchUp(_ pos: CGPoint) {
        if self.play_button.isClicked(pos) {scene.setFlagGameState(_gameState: GameScene.GameState.PLAY) }
        else if self.experience_button.isClicked(pos) {
            scene.viewController.performSegue(withIdentifier: "Camera", sender: nil)
        }
        else if self.map_button.isClicked(pos) {
            scene.viewController.performSegue(withIdentifier: "Map", sender: nil)
        }
    }
}
