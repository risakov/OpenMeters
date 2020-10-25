//
//  HistoryPresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit
import RxSwift

class HistoryPresenter {
    
    private weak var view: HistoryView!
    private let router: HistoryRouter


    init(_ view: HistoryView,
         _ router: HistoryRouter) {
        self.view = view
        self.router = router
    }
}
