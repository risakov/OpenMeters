//
//  EndpointPresenter.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//


import UIKit
import RxSwift
import RxNetworkApiClient

protocol EndpointPresenter {
    func setEndpoint(_ url: String)
    func openRootScene()
}

class EndpointPresenterImp: EndpointPresenter {
    func setEndpoint(_ url: String) {
        ApiEndpoint.baseEndpoint = ApiEndpoint(url)
    }
    
    
    private let view: EndpointView
    private let router: EndpointRouter

    init(_ view: EndpointView,
         _ router: EndpointRouter) {
        self.view = view
        self.router = router
    }
    
    func openRootScene() {
        self.router.openRootScene()
    }
    
}
