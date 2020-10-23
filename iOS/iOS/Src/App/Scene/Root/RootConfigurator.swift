//
//  RootConfigurator.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import Foundation

protocol RootConfigurator {

    func configure(view: RootViewController)
}

class RootConfiguratorImp: RootConfigurator {

    func configure(view: RootViewController) {

        let router = RootRouterImp(view)
        let presenter = RootPresenterImp(view, router, DI.resolve())
        view.presenter = presenter
    }
}
