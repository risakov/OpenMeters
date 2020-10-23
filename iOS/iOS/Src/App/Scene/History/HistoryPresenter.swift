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
//    private let gateway: HistoryGateway
//    private var paginationUseCase: HistoryPaginationUseCase
    private let limitPagination: Int
    private var isLoading = false
    private var disposeBag = DisposeBag()

    init(_ view: HistoryView,
         _ router: HistoryRouter) {
//         _ gateway: HistoryGateway,
//         _ paginationUseCase: HistoryPaginationUseCase) {
        self.view = view
        self.router = router
//        self.gateway = gateway
//        self.paginationUseCase = paginationUseCase

        self.limitPagination = 10
//        self.paginationUseCase.limit = self.limitPagination
        
//        _ = paginationUseCase.source
//                .observeOn(MainScheduler.instance)
//                .subscribe(onNext: { [weak self] (result: [HistoryEntity]) in
//                    guard let self = self else {
//                        return
//                    }
//                    self.images = result
//                    self.isLoading = false
//                    self.view.reloadCollection()
//                })
        
    }
}
