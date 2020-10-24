//
//  PHImageManager + requestImageDataSynchronously.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Photos

extension PHImageManager {

    func requestImageDataSynchronously(for asset: PHAsset) throws -> Data {

        let options = PHImageRequestOptions()
        options.isNetworkAccessAllowed = true

        let dispatchGroup = DispatchGroup()
        dispatchGroup.enter()
        var imageData: Data?

        PHImageManager.default()
                .requestImageData(for: asset, options: options) { data, _, _, _ in
                    imageData = data
                    dispatchGroup.leave()
                }

        dispatchGroup.wait()

        guard let data = imageData else {
            throw NSError(domain: "Not found data for PHAsset: \(asset)", code: 0)
        }

        return data
    }
}
