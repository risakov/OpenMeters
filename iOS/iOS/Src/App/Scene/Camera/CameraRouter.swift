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
    
    func close() {
        self.view.navigationController?.popViewController(animated: true)
    }
    
    
}
