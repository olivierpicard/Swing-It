//
//  MapController.swift
//  Swing-It
//
//  Created by Olivier Picard on 04/10/2018.
//  Copyright © 2018 Caffeine. All rights reserved.
//

import Foundation
import UIKit
import MapKit
import CoreLocation

class MapController : UIViewController, CLLocationManagerDelegate
{
    @IBOutlet weak var mapView: MKMapView!
    
    let manager = CLLocationManager()
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(false, animated: true)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        manager.delegate = self
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.requestWhenInUseAuthorization()
        manager.startUpdatingLocation()
    }
    
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let userLocation = locations[0]
        
        manager.stopUpdatingLocation()
        
        let span = MKCoordinateSpan(latitudeDelta: 0.01,longitudeDelta: 0.01)
        let coordinations = CLLocationCoordinate2DMake(userLocation.coordinate.latitude,
                                                       userLocation.coordinate.longitude)
        
        let region = MKCoordinateRegion(center: coordinations, span: span)
        
        mapView.setRegion(region, animated: true)
        self.mapView.showsUserLocation = true
    }
}
