//
//  ResultsPresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import Foundation

protocol ResultsPresenter {
    
}

class ResultsPresenterImp: ResultsPresenter {
    private weak var view: ResultsView!
    private let router: ResultsRouter
    
    init(_ view: ResultsView,
         _ router: ResultsRouter) {
        
        self.view = view
        self.router = router
        
    }
}
