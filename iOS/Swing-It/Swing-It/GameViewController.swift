//
//  GameViewController.swift
//  Swing-It
//
//  Created by Olivier Picard on 01/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import UIKit
import SpriteKit
import GameplayKit

class GameViewController: UIViewController
{
    static var qrCodeMessage: String?
    
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        if let qrCode = GameViewController.qrCodeMessage { decryptString(qrCode) }
        else {
            decryptString(GameViewController.readWeather())
            decryptString(GameViewController.readBonus())
            decryptString(GameViewController.readEnnemy())
        }
        
        if let view = self.view as! SKView?
        {
            // Load the SKScene from 'GameScene.sks'
            if let scene = SKScene(fileNamed: "GameScene") {
                // Set the scale mode to scale to fit the window
                scene.scaleMode = .aspectFill
                (scene as! GameScene).viewController = self
                // Present the scene
                view.presentScene(scene)
            }
            
            view.ignoresSiblingOrder = true
            
            view.showsFPS = true
            view.showsNodeCount = true
        }
        
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    

    override var shouldAutorotate: Bool {
        return true
    }

    override var supportedInterfaceOrientations: UIInterfaceOrientationMask {
        if UIDevice.current.userInterfaceIdiom == .phone {
            return .allButUpsideDown
        } else {
            return .all
        }
    }
   
    
    override var prefersStatusBarHidden: Bool {
        return true
    }
    
    
    static func saveWeather(val: String){
        UserDefaults.standard.setValue(val, forKey: "weather")
    }
    
    
    static func saveBonus(val: String){
        UserDefaults.standard.setValue(val, forKey: "bonus")
    }
    
    
    static func saveEnnemy(val: String){
        UserDefaults.standard.setValue(val, forKey: "ennemy")
    }
    
    
    static func readWeather() -> String? {
        return UserDefaults.standard.value(forKey: "weather") as! String?
    }
    
    
    static func readBonus() -> String? {
        return UserDefaults.standard.value(forKey: "bonus") as! String?
    }
    
    
    static func readEnnemy() -> String? {
        return UserDefaults.standard.value(forKey: "ennemy") as! String?
    }
    
    
    func decryptString(_ conf: String?)
    {
        if(conf == nil) { return }
        let conf = conf!
        
        if(conf == "rainy") {
            GameScene.weather = GameScene.Weather.Rainy
            GameScene.cloudImageTexture = "cloud"
            GameViewController.saveWeather(val: conf)
        }
        else if(conf == "cloudy") {
            GameScene.weather = GameScene.Weather.Cloudy
            GameScene.cloudImageTexture = "cloud2"
            GameViewController.saveWeather(val: conf)
        }
        else if(conf == "cleared") {
            GameScene.weather = GameScene.Weather.Cleared
            GameScene.cloudImageTexture = "cloud2"
            GameViewController.saveWeather(val: conf)
        }
        else if(conf == "stormy") {
            GameScene.weather = GameScene.Weather.Stormy
            GameScene.cloudImageTexture = "cloud"
            GameViewController.saveWeather(val: conf)
        }
        else if(conf == "nobombs") {
            GameScene.ennemyProbability = 0
            GameViewController.saveEnnemy(val: conf)
        }
        else if(conf == "bombs") {
            GameScene.ennemyProbability = GameScene.ENNEMY_PROBABILITY
            GameViewController.saveEnnemy(val: conf)
        }
        else if(conf == "bread") {
            GameScene.bonusImageTexture = "bread"
            GameScene.bonusSize = CGSize(width: 60, height: 30)
            GameViewController.saveBonus(val: conf)
        }
        else if(conf == "hamburger") {
            GameScene.bonusImageTexture = "hamburger"
            GameScene.bonusSize = CGSize(width: 55, height: 55)
            GameViewController.saveBonus(val: conf)
        }
        else if(conf == "litchi") {
            GameScene.bonusImageTexture = "litchi"
            GameScene.bonusSize = GameScene.BASE_SIZE
            GameViewController.saveBonus(val: conf)
        }
        else if(conf == "pork") {
            GameScene.bonusImageTexture = "pork"
            GameScene.bonusSize = CGSize(width: 60, height: 50)
            GameViewController.saveBonus(val: conf)
        }
    }
    
}
