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
    func getThumbnailForSingleAsset(_ imageAsset: PHAsset) -> UIImage?
    func getThumbnailForAssets(assets: [PHAsset])
    func sendSingleImage(_ imageData: Data)
    func sendImages(_ imagesData: [Data])
    func openHistoryScene()
    func close()
}

class CameraPresenterImp: CameraPresenter {
    
    private weak var view: CameraView!
    private var router: CameraRouter
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
    
    
    internal func getThumbnailForAssets(assets: [PHAsset]) {
        var arrayOfImagesData = [Data]()
        for asset in assets {
            arrayOfImagesData.append(asset.image.pngData() ?? Data())
        }
        self.sendImages(arrayOfImagesData)
    }
    
    internal func getThumbnailForSingleAsset(_ imageAsset: PHAsset) -> UIImage?{
            return nil
    }
    
    func sendSingleImage(_ imageData: Data) {
        self.imageGateway.uploadSingleImage(imageData)
            .observeOn(MainScheduler.instance)
            .do(onSubscribe: { [weak self] in
                self?.view.showPreloaderView()
                })
            .subscribe(onSuccess: { [weak self] createdImage in
                self?.createdImages.append(createdImage)
                self?.router.openReadyScreen()
                }, onError: { [weak self] error in
                    self?.view.showErrorDialog(message: "Произошла ошибка отправки: \(error.localizedDescription)", action: { [weak self] _ in
                        self?.view.hidePreloaderView()
                        self?.openHistoryScene()
                    })
            })
            .disposed(by: disposeBag)
    }
    
    func sendImages(_ imagesData: [Data]) {
        self.imageGateway.uploadImages(imagesData)
            .observeOn(MainScheduler.instance)
            .do(onSubscribe: { [weak self] in
                self?.view.showPreloaderView()
                })
            .subscribe(onSuccess: { [weak self] createdImage in
                self?.createdImages.append(contentsOf: createdImage)
                self?.router.openReadyScreen()
                }, onError: { [weak self] error in
                    self?.view.showErrorDialog(message: "Произошла ошибка отправки: \(error.localizedDescription)", action: { [weak self] _ in
                        self?.view.hidePreloaderView()
                        self?.openHistoryScene()
                    })
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
