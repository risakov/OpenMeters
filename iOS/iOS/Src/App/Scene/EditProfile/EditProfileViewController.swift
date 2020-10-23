//
//  EditProfileViewController.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

protocol EditProfileView: BaseView {
}


class EditProfileViewController: UIViewController {

    var presenter: EditProfilePresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

}

extension EditProfileViewController: EditProfileView {
    
}
