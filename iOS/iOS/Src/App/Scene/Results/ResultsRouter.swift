//
//  ResultsRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class ResultsRouter: BaseRouter {
    weak var view: UIViewController!


    init(_ view: ResultsViewController) {
        self.view = view
    }
    
    func openCameraScene() {
        CameraConfigurator.open(navigationController: self.view.navigationController!)
    }
    
    func close() {
        self.popToRoot()
    }
}
