//
//  EndpointConfigurator.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import Foundation

class EndpointConfigurator {

    func configure(view: EndpointViewController) {

        let router = EndpointRouter(view)
        let presenter = EndpointPresenterImp(view, router)
        view.presenter = presenter
    }

}
