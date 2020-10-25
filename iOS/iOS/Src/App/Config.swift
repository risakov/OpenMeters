//
//  Config.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxNetworkApiClient

class Config {
    
    static var apiEndpoint = "https://young-meters.herokuapp.com/api"
    
    static func setState(_ endpoints: (main: String, userArea: String)) {
        let prefix = "https://"
        Config.apiEndpoint = endpoints.main.contains(prefix) ? endpoints.main : prefix + endpoints.main
        ApiEndpoint.updateEndpoint()
    }
}
