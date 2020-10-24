//
//  ApiEndpoint.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxNetworkApiClient

extension ApiEndpoint {

    private(set) static var api = ApiEndpoint(Config.apiEndpoint)
    
    static func updateEndpoint() {
        ApiEndpoint.api = ApiEndpoint(Config.apiEndpoint)
    }
}
