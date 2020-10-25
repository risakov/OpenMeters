//
//  ReadyRouter.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import UIKit

class ReadyRouter: BaseRouter {
    weak var view: UIViewController!

    init(_ view: ReadyViewController) {
        self.view = view
    }
    
    func openHistoryScene() {
        let indexHistoryScreen = TabBarIndices.historyTab.rawValue
        
        let tb = self.view.tabBarController
        self.view.tabBarController?.selectedIndex = indexHistoryScreen
        let navC = tb?.viewControllers?[indexHistoryScreen] as! UINavigationController
        navC.popToRootViewController(animated: false)
        let historyVC = HistoryConfigurator.getVC()
        navC.setViewControllers([historyVC], animated: false)
    }
    
    func close() {
        self.popToRoot()
    }
    
}
