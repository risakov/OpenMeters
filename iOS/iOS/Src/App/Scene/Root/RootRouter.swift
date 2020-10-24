//
//  RootRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol RootRouter {
    func openHistoryScene()
    func openCameraScene()
    func openProfileScene()
}

class RootRouterImp: RootRouter {

    private var view: RootViewController!

    init(_ view: RootViewController) {
        self.view = view
    }

    func openHistoryScene() {

        let indexHistoryScreen = TabBarIndices.historyTab.rawValue
        let tb = view!
        tb.selectedIndex = indexHistoryScreen
        let navC = tb.viewControllers?[indexHistoryScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let historyVC = HistoryConfigurator.getVC()

        navC.setViewControllers([historyVC], animated: false)
    }

    func openCameraScene() {

        let indexCameraScreen = TabBarIndices.cameraTab.rawValue
        
        let tb = view!
        tb.selectedIndex = indexCameraScreen
        let navC = tb.viewControllers?[indexCameraScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let cameraVC = CameraConfigurator.getVC()
        navC.setViewControllers([cameraVC], animated: false)
    }

    func openProfileScene() {
        
        let indexProfileScreen = TabBarIndices.profileTab.rawValue

        let tb = view!
        tb.selectedIndex = indexProfileScreen
        let navC = tb.viewControllers?[indexProfileScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let profileVC = ProfileConfigurator.getVC()

        navC.setViewControllers([profileVC], animated: false)
    }

}
