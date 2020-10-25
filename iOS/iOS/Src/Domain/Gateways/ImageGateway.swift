//
//  ImageGateway.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxSwift
import RxNetworkApiClient

protocol ImageGateway {
    func uploadImages(_ data: [Data]) -> Single<[CreatedImageEntity]>
    func uploadSingleImage(_ data: Data) -> Single<CreatedImageEntity>
}
