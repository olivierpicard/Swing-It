//
//  GameOverScreen.swift
//  Swing-It
//
//  Created by Olivier Picard on 03/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import SpriteKit

class GameOverScreen {
    let scene: GameScene
    var retry_button:Button
    var menu_button:Button
    private var isHidden: Bool;
    
    
    init(scene: GameScene) {
        self.scene = scene
        isHidden = true
        self.retry_button = Button(text: "Recommencer")
        self.retry_button.position = CGPoint(x: scene.frame.midX, y: scene.size.height * 0.6)
        
        self.menu_button = Button(text: "Accueil")
        self.menu_button.position = self.retry_button.position
        self.menu_button.position.y -= self.retry_button.size.height + 30
    }
    
    
    func hide() {
        if(isHidden) { return }
        isHidden = true
        self.scene.removeChildren(in: [self.retry_button,
                                       self.menu_button])
    }
    
    
    func show() {
        if(!isHidden) { return }
        isHidden = false
        self.scene.addChild(self.retry_button)
        self.scene.addChild(self.menu_button)
    }
    
    
    func touchUp(_ pos: CGPoint) {
        if(retry_button.isClicked(pos)) {
            scene.setFlagGameState(_gameState: GameScene.GameState.PLAY)
        } else if(menu_button.isClicked(pos)) {
            scene.setFlagGameState(_gameState: GameScene.GameState.WELCOME)
        }
    }
}
