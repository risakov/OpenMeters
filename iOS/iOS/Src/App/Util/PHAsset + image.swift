//
//  PHAsset + image.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import Foundation
import Photos
import UIKit

extension PHAsset {
    var image : UIImage {
        var thumbnail = UIImage()
        let imageManager = PHCachingImageManager()
        imageManager.requestImage(for: self, targetSize: CGSize(width: 100, height: 100), contentMode: .aspectFit, options: nil, resultHandler: { image, _ in
            thumbnail = image!
        })
        return thumbnail
    }
}
