//
//  ProfilePresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import Foundation

protocol ProfilePresenter {
    
}

class ProfilePresenterImp: ProfilePresenter {
    private weak var view: ProfileView!
    private let router: ProfileRouter
    
    init(_ view: ProfileView,
         _ router: ProfileRouter) {
        
        self.view = view
        self.router = router
        
    }
}

