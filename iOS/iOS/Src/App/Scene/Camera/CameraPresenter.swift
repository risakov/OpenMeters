//
//  CameraPresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import Foundation


protocol CameraPresenter {
    func openHistoryScene()
}

class CameraPresenterImp: CameraPresenter {
    
    private weak var view: CameraView!
    private var router: CameraRouter
    private let checkPermissionsUseCase: CheckPermissionsUseCase
    
    
    init(_ view: CameraView,
         _ router: CameraRouter,
         _ checkPermissionsUseCase: CheckPermissionsUseCase) {
        
        self.view = view
        self.router = router
        self.checkPermissionsUseCase = checkPermissionsUseCase
        
    }
    
    func openHistoryScene() {
        self.router.openHistoryScene()
    }
    
    
    func onCameraCaptureReady(_ isReady: Bool) {
        
        if isReady {

        } else {
            if !self.checkPermissionsUseCase.hasAccessToCamera() {
            } else {
                self.router.close()
            }
        }
    }
    
}
