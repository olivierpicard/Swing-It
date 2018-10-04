//
//  CameraController.swift
//  Swing-It
//
//  Created by Olivier Picard on 04/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import UIKit
import AVFoundation

class CameraController: UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    
    @IBOutlet weak var square: UIImageView!
    @IBOutlet weak var preview: UIView!
    @IBOutlet weak var menu: UIButton!
    var video = AVCaptureVideoPreviewLayer()
    var session: AVCaptureSession!
    
    
    override var prefersStatusBarHidden: Bool { return false }
    override var preferredStatusBarStyle: UIStatusBarStyle { return .default }
    
    
    override func viewWillAppear(_ animated: Bool) {
        //Creating session
        session = AVCaptureSession()
        
        //Define capture devcie
        let captureDevice = AVCaptureDevice.default(for: AVMediaType.video)
        do
        {
            let input = try AVCaptureDeviceInput(device: captureDevice!)
            session.addInput(input)
        }
        catch
        {
            print ("ERROR")
        }
        
        let output = AVCaptureMetadataOutput()
        session.addOutput(output)
        
        output.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
        
        output.metadataObjectTypes = [AVMetadataObject.ObjectType.qr]
        
        video = AVCaptureVideoPreviewLayer(session: session)
        video.connection?.videoOrientation = AVCaptureVideoOrientation.landscapeLeft
        video.frame = preview.layer.bounds
        preview.layer.addSublayer(video)
        session.startRunning()
        self.view.bringSubviewToFront(self.navigationController!.navigationBar)
        self.view.bringSubviewToFront(square)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    
    
    
    func metadataOutput(_ output: AVCaptureMetadataOutput,
                        didOutput metadataObjects: [AVMetadataObject],
                        from connection: AVCaptureConnection)
    {
        
        if metadataObjects.count != 0 {
            if let object = metadataObjects[0] as? AVMetadataMachineReadableCodeObject {
                if object.type == AVMetadataObject.ObjectType.qr {
                    GameViewController.qrCodeMessage = object.stringValue
                    session.stopRunning()
                    performSegue(withIdentifier: "Game", sender: nil)
                }
            }
        }
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}
