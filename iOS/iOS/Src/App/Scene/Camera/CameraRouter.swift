//
//  CameraRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import Foundation

class CameraRouter {
    
    private weak var view: CameraViewController!


    init(_ view: CameraViewController) {
        self.view = view
    }
    
    func openHistoryScene() {
        HistoryConfigurator.open(navigationController: self.view.navigationController!)
    }
    
    func close() {
        self.view.navigationController?.popToRootViewController(animated: true)
    }
    
    
}
