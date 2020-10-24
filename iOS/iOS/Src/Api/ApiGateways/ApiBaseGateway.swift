//
//  ApiBaseGateway.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxNetworkApiClient

class ApiBaseGateway {

    let apiClient: ApiClient

    init(_ apiClient: ApiClient) {
        self.apiClient = apiClient
    }
}
