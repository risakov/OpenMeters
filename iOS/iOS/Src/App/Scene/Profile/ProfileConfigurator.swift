//
//  ProfileConfigurator.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class ProfileConfigurator {
    func configure(view: ProfileViewController) {
        let router = ProfileRouter(view)
        let presenter = ProfilePresenterImp(view, router)
        view.presenter = presenter
    }

    static func open(navigationController: UINavigationController) {
        let view = ProfileConfigurator.getVC()
        ProfileConfigurator().configure(view: view)
        navigationController.pushViewController(view, animated: true)
    }

    static func getVC() -> ProfileViewController {
        let view = R.storyboard.profile.profileVC()!
        ProfileConfigurator().configure(view: view)
        return view
    }
}
