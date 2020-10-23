//
//  CameraPresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import Foundation


protocol CameraPresenter {
    
}

class CameraPresenterImp: CameraPresenter {
    
    private weak var view: CameraView!
    private var router: CameraRouter
    
    init(_ view: CameraView,
         _ router: CameraRouter) {
        self.view = view
        self.router = router
    }
    
}
