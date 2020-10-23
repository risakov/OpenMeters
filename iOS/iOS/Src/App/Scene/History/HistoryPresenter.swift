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
    private let gateway: HistoryGateway
    private var paginationUseCase: HistoryPaginationUseCase
    private let limitPagination: Int
    private var isLoading = false
    private var disposeBag = DisposeBag()

    init(_ view: ImagesView,
         _ router: ImagesRouter,
         _ gateway: ImagesGateway,
         _ paginationUseCase: ImagesPaginationUseCase) {
        self.view = view
        self.router = router
        self.gateway = gateway
        self.paginationUseCase = paginationUseCase

        self.limitPagination = 10
        self.paginationUseCase.limit = self.limitPagination
        self.currentSegmentedStateIndex = 0
        
        _ = paginationUseCase.source
                .observeOn(MainScheduler.instance)
                .subscribe(onNext: { [weak self] (result: [ImageEntity]) in
                    guard let self = self else {
                        return
                    }
                    self.images = result
                    self.isLoading = false
                    self.view.reloadCollection()
                })
        
    }
}
