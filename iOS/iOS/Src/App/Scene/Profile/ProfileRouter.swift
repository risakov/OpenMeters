//
//  ProfileRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class ProfileRouter: BaseRouter {
    weak var view: UIViewController!


    init(_ view: ProfileViewController) {
        self.view = view
    }
    
    func close() {
        self.view.navigationController?.popViewController(animated: true)
    }
    
    func openEditProfileScene() {
        EditProfileConfigurator.open(navigationController: self.view.navigationController!)
    }
}
