//
//  CameraPresenter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit
import Photos
import RxSwift
import RxNetworkApiClient


protocol CameraPresenter {
    func convertAndSendSingleImage(_ imageAsset: PHAsset)
    func convertAndSendArrayOfImages(_ arrayOfAssets: [PHAsset])
    func sendSingleImage(_ image: UIImage)
    func sendImages(_ images: [UIImage])
    func openHistoryScene()
    func close()
}

class CameraPresenterImp: CameraPresenter {
    
    private weak var view: CameraView!
    private var router: CameraRouter
    private var minImageCount = 1
    private let disposeBag = DisposeBag()
    
    init(_ view: CameraView,
         _ router: CameraRouter) {
        self.view = view
        self.router = router
    }
    
    private func getThumbnailForAssets(assets: [PHAsset]) -> [UIImage] {
        var arrayOfImages = [UIImage]()
        for asset in assets {
            let manager = PHImageManager.default()
            let option = PHImageRequestOptions()
            var image = UIImage()
            option.isSynchronous = true
            manager.requestImage(for: asset, targetSize: CGSize(width: 100, height: 100), contentMode: .aspectFit, options: option, resultHandler: {(result, info)->Void in
                image = result!
                arrayOfImages.append(image)
            })
        }

        return arrayOfImages
    }
    
    private func getThumbnailForSingleAsset(_ imageAsset: PHAsset) -> UIImage {
        let manager = PHImageManager.default()
        let option = PHImageRequestOptions()
        var image = UIImage()
        option.isSynchronous = true
        manager.requestImage(for: imageAsset, targetSize: CGSize(width: 100, height: 100), contentMode: .aspectFit, options: option, resultHandler: {(result, info)->Void in
            image = result!
        })
        return image
    }
    
    internal func convertAndSendSingleImage(_ imageAsset: PHAsset)  {
        let image = getThumbnailForSingleAsset(imageAsset)
        print(image)
    }
    
    internal func convertAndSendArrayOfImages(_ arrayOfAssets: [PHAsset]) {
        let arrayOfImages = getThumbnailForAssets(assets: arrayOfAssets)
        print(arrayOfImages)
    }
    
    func sendSingleImage(_ image: UIImage) {
        
    }
    
    func sendImages(_ images: [UIImage]) {
        
    }
    
    func openHistoryScene() {
        self.router.openHistoryScene()
    }
    
    func close() {
        self.router.close()
    }
    
    
}
