//
//  ProfileViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol ProfileView: BaseView {
    
}

class ProfileViewController: UIViewController {

    var presenter: ProfilePresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedString.Key.font: UIFont(name: R.font.graphikRegular.fontName, size: 17)!]
    }
}

extension ProfileViewController: ProfileView {
    
}
