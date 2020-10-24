//
//  CreatedImageEntity.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import RxNetworkApiClient
import RxSwift


class CreatedImageEntity: Codable {
    var value: String?
    var serialNumber: String
    let photoPath: String
    var isSelected: Bool = false

}
