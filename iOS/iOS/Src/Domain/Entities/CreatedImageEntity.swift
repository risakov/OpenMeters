//
//  CreatedImageEntity.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation

class CreatedImageEntity: Codable {

    let localIdentifier: String?
    let url: URL

    init(localIdentifier: String, url: URL) {
        self.localIdentifier = localIdentifier
        self.url = url
    }

    init(url: URL) {
        self.localIdentifier = nil
        self.url = url
    }
}
