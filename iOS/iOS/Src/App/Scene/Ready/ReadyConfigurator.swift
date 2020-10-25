//
//  ReadyConfigurator.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import UIKit

class ReadyConfigurator {

    func configure(view: ReadyViewController) {
        let router = ReadyRouter(view)
        let presenter = ReadyPresenterImp(view, router)
        view.presenter = presenter
    }

    static func open(navigationController: UINavigationController) {
        let view = ReadyConfigurator.getVC()
        ReadyConfigurator().configure(view: view)
        navigationController.pushViewController(view, animated: true)
    }

    static func getVC() -> ReadyViewController {
        let view = R.storyboard.ready.readyVC()!
        return view
    }

}
