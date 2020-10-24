//
//  EditProfilePresenter.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit
import RxSwift

class EditProfilePresenter {
    
    private weak var view: EditProfileView!
    private let router: EditProfileRouter

    private var disposeBag = DisposeBag()
    
    init(_ view: EditProfileView,
         _ router: EditProfileRouter) {

        self.view = view
        self.router = router

    }
}
