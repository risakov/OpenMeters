//
//  CameraRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class CameraRouter: BaseRouter {
    
    weak var view: UIViewController!

    init(_ view: CameraViewController) {
        self.view = view
    }
    
    func openHistoryScene() {
        let indexHistoryScreen = TabBarIndices.historyTab.rawValue
        
        let tb = self.view.tabBarController
        self.view.tabBarController?.selectedIndex = indexHistoryScreen
        let navC = tb?.viewControllers?[indexHistoryScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let historyVC = HistoryConfigurator.getVC()
        navC.setViewControllers([historyVC], animated: true)
    }
    
    func openReadyScreen() {

    }
    
    func close() {
        self.popToRoot()
    }
    
}
