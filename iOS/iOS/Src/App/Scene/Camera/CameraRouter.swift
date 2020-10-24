//
//  CameraRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class CameraRouter {
    
    private weak var view: CameraViewController!


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
        navC.setViewControllers([historyVC], animated: false)    }
    
    func close() {
        self.view.navigationController?.popToRootViewController(animated: true)
    }
    
    
}
