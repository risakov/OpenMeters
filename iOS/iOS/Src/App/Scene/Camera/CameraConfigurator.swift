//
//  CameraConfigurator.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class CameraConfigurator {
    func configure(view: CameraViewController) {
        let router = CameraRouter(view)
        let presenter = CameraPresenterImp(view, router)
        view.presenter = presenter
    }

    static func open(navigationController: UINavigationController) {
        let view = CameraConfigurator.getVC()
        CameraConfigurator().configure(view: view)
        navigationController.pushViewController(view, animated: true)
    }

    static func getVC() -> CameraViewController {
        let view = R.storyboard.camera.cameraVC()!
        CameraConfigurator().configure(view: view)
        return view
    }
}
