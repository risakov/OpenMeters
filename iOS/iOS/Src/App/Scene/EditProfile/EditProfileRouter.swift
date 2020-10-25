//
//  EditProfileRouter.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

class EditProfileRouter: BaseRouter {
    
    weak var view: UIViewController!

    init(_ view: EditProfileViewController) {
        self.view = view
    }
    
}
