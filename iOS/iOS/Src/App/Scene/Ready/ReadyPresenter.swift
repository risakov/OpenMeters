//
//  ReadyPresenter.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import RxSwift

protocol ReadyPresenter {
    func openHistoryScene()
}

class ReadyPresenterImp {
    
    private weak var view: ReadyView!
    private let router: ReadyRouter


    init(_ view: ReadyView,
         _ router: ReadyRouter) {
        self.view = view
        self.router = router
    }
    
    func openHistoryScene() {
        self.router.openHistoryScene()
    }

    
    
}
