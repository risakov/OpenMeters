//
//  RootPresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//


import UIKit
import RxSwift

protocol RootPresenter {
    func openHistoryScene()
    func openCameraScene()
    func openProfileScene()
}

class RootPresenterImp {
    
    private let view: RootView
    private let router: RootRouter

    init(_ view: RootView,
         _ router: RootRouter) {
        self.view = view
        self.router = router
    }

    func openHistoryScene() {
        router.openHistoryScene()
    }

    func openCameraScene() {
        router.openCameraScene()
    }

    func openProfileScene() {
        router.openProfileScene()
    }
}
