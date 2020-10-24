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
    func sendSingleImage(_ imageData: Data)
    func sendImages(_ imagesData: [Data])
    func openHistoryScene()
    func close()
}

class CameraPresenterImp: CameraPresenter {
    
    private weak var view: CameraView!
    private var router: CameraRouter
    private var minImageCount = 1
    private let disposeBag = DisposeBag()
    private let imageGateway: ImageGateway
    private var createdImages = [CreatedImageEntity]()

    
    init(_ view: CameraView,
         _ router: CameraRouter,
         _ imageGateway: ImageGateway) {
        self.view = view
        self.router = router
        self.imageGateway = imageGateway
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
        if let jpegData = image.jpegData(compressionQuality: 1.0) {
            self.sendSingleImage(jpegData)
        }
    }
    
    internal func convertAndSendArrayOfImages(_ arrayOfAssets: [PHAsset]) {
        var imagesData = [Data]()
        let arrayOfImages = getThumbnailForAssets(assets: arrayOfAssets)
        for image in arrayOfImages {
            if let jpegData = image.jpegData(compressionQuality: 1.0) {
                imagesData.append(jpegData)
            }
        }
        sendImages(imagesData)
    }
    
    func sendSingleImage(_ imageData: Data) {
        self.imageGateway.uploadSingleImage(imageData)
            .observeOn(MainScheduler.instance)
            .subscribe(onSuccess: {  [weak self] response in
                guard let self = self else { return }
                self.createdImages.append(response)
                self.view.showDialog(message: "Фото успешно отправлено")
                return
            }, onError: { [weak self] (error) in
                guard let self = self else { return }
                self.view.showErrorDialog(message: "Произошла ошибка \(error.localizedDescription)" )
            })
            .disposed(by: disposeBag)
    }
    
    func sendImages(_ imagesData: [Data]) {
        self.imageGateway.uploadImages(imagesData)
            .observeOn(MainScheduler.instance)
            .subscribe(onSuccess: {  [weak self] responses in
                guard let self = self else { return }
                self.createdImages.append(contentsOf: responses)
                self.view.showDialog(message: "Фотографии успешно отправлены")
                return
            }, onError: { [weak self] (error) in
                guard let self = self else { return }
                self.view.showErrorDialog(message: "Произошла ошибка \(error.localizedDescription)" )
            })
            .disposed(by: disposeBag)
        
    }
    
    func openHistoryScene() {
        self.router.openHistoryScene()
    }
    
    func close() {
        self.router.close()
    }
    
    
}
