//
//  EditProfileConfigurator.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

class EditProfileConfigurator {
    func configure(view: EditProfileViewController) {
        let router = EditProfileRouter(view)
        let presenter = EditProfilePresenter(view, router)
        view.presenter = presenter
    }

    static func open(navigationController: UINavigationController) {
        let view = EditProfileConfigurator.getVC()
        EditProfileConfigurator().configure(view: view)
        navigationController.pushViewController(view, animated: true)
    }

    static func getVC() -> EditProfileViewController {
        let view = R.storyboard.editProfile.editProfileVC()!
        EditProfileConfigurator().configure(view: view)
        return view
    }
}
