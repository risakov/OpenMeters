//
//  ErrorResponseHandler.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import RxSwift
import RxNetworkApiClient

open class ErrorResponseHandler: ResponseHandler {

    private let jsonDecoder = JSONDecoder()

    public func handle<T: Codable>(observer: @escaping SingleObserver<T>,
                                   request: ApiRequest<T>,
                                   response: NetworkResponse) -> Bool {

        if let urlResponse = response.urlResponse,
           let nsHttpUrlResponse = urlResponse as? HTTPURLResponse,
           (300..<600).contains(nsHttpUrlResponse.statusCode) {
            let errorEntity = ResponseErrorEntity(response.urlResponse)
            #if DEBUG
                errorEntity.errors.append("|| \(nsHttpUrlResponse.statusCode) ||\n")

                switch nsHttpUrlResponse.statusCode {
                    case (300..<400):
                        errorEntity.errors.append("Неверный редирект.\n")
                    case (400..<500):
                        errorEntity.errors.append("Неверный запрос.\n")
                    case (500..<600):
                        errorEntity.errors.append("Ошибка сервера.\n")
                    default:
                        errorEntity.errors.append("Неизвестная ошибка.\n")
                }
            #endif

            observer(.error(errorEntity))
            return true
        }
        return false
    }
}
