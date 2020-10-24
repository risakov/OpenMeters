//
//  ApiImageGateway.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxSwift
import RxNetworkApiClient

class ApiImageGateway: ApiBaseGateway, ImageGateway {
    func uploadImages(_ data: [Data]) -> Single<Data> {
        let request: ExtendedApiRequest<Data> = ExtendedApiRequest.uploadImages(data)
        request.responseTimeout = Double(30 * data.count)
        return self.apiClient.execute(request: request)
    }
    
    func uploadSingleImage(_ data: Data) -> Single<Data> {
        let request: ExtendedApiRequest<Data> = ExtendedApiRequest.uploadSingleImage(data)
        request.responseTimeout = 30
        return self.apiClient.execute(request: request)
    }
    
}
