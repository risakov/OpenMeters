//
//  DI.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import DITranquillity
import RxNetworkApiClient

class DI {
    
    private static let shared = DI()
    fileprivate(set) static var container = DIContainer()
    
    private init() {
        // No Constructor
    }

    
    static func initDependencies(_ appDelegate: AppDelegate) {
        
        DI.container = DIContainer()
        
        ApiEndpoint.baseEndpoint = ApiEndpoint.api

        // MARK: - ApiClient
        self.container.register { () -> ApiClientImp in
                    let config = URLSessionConfiguration.default
                    config.timeoutIntervalForRequest = 60 * 20
                    config.timeoutIntervalForResource = 60 * 20
                    let client = ApiClientImp(urlSessionConfiguration: config, completionHandlerQueue: .main)
                    return client
                }
                .as(ApiClient.self)
                .lifetime(.single)
        

        
        // MARK: - Gateways
        
        self.container.register(ApiImageGateway.init)
            .as(ImageGateway.self)
            .lifetime(.single)

        // MARK: - UseCases

        // MARK: - PaginationUseCase
        
        #if DEBUG
        if !self.container.validate() {
            fatalError("DI not configured successful.")
        }
        #endif
    }
    
    static func resolve<T>(name: String? = nil) -> T {
        if let name = name {
            return self.container.resolve(name: name)
        } else {
            return self.container.resolve()
        }
    }
}

