//
//  RootRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol RootRouter {
    func openHistoryScreen()
    func openCameraScreen()
    func openProfileScreen()
}

class RootRouterImp: RootRouter {

    private var view: RootViewController!

    init(_ view: RootViewController) {
        self.view = view
    }

    func openHistoryScreen() {

        let indexMainScreen = TabBarIndices.historyTab.rawValue
        let tb = view!
        tb.selectedIndex = indexMainScreen
        let navC = tb.viewControllers?[indexMainScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let mainVC = HistoryConfigurator.getVC()

        navC.setViewControllers([mainVC], animated: false)
    }

    func openCameraScreen() {

        let indexNewPhotoScreen = TabBarIndices.cameraTab.rawValue
        
        let tb = view!
        tb.selectedIndex = indexNewPhotoScreen
        let navC = tb.viewControllers?[indexNewPhotoScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let newPhotoVC = CameraConfigurator.getVC()
        navC.setViewControllers([newPhotoVC], animated: false)
    }

    func openProfileScreen() {
        
        let indexProfileScreen = TabBarIndices.profileTab.rawValue

        let tb = view!
        tb.selectedIndex = indexProfileScreen
        let navC = tb.viewControllers?[indexProfileScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let profileVC = ProfileConfigurator.getVC()

        navC.setViewControllers([profileVC], animated: false)
    }

}
