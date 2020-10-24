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

}

extension ProfileViewController: ProfileView {
    
}
