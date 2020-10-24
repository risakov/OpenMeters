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
    fileprivate(set) static var backgroundContainer = DIContainer()
    
    private init() {
        // No Constructor
    }

    
    static func initDependencies(_ appDelegate: AppDelegate) {
        
        DI.container = DIContainer(parent: backgroundContainer)
        
        // MARK: - Gateways

        // MARK: - UseCases

        // MARK: - PaginationUseCase
        
        #if DEBUG
        if !self.container.validate() {
            fatalError("DI not configured successful.")
        }
        #endif
    }
    
    static func resolve<T>() -> T {
        return self.container.resolve()
    }
    
    static func resolveBackground<T>() -> T {
        return self.backgroundContainer.resolve()
    }
}

