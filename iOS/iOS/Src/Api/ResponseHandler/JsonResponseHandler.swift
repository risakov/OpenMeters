//
//  JsonResponseHandler.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import RxSwift
import SwiftyJSON
import RxNetworkApiClient

open class JsonResponseHandler: ResponseHandler {

    public init() {
    }

    private let decoder = JSONDecoder()

    public func handle<T: Codable>(observer: @escaping SingleObserver<T>,
                                   request: ApiRequest<T>,
                                   response: NetworkResponse) -> Bool {
        if let data = response.data {
            do {
                if T.self == JSON.self {
                    let json = try JSON(data: data)
                    observer(.success(json as! T))
                } else if T.self == Data.self {
                    observer(.success(data as! T))
                } else {
                    let result = try decoder.decode(T.self, from: data)
                    observer(.success(result))
                }
            } catch {
                observer(.error(error))
            }
            return true
        }
        return false
    }
}
