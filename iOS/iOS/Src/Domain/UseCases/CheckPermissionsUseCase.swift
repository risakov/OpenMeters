//
//  CheckPermissionsUseCase.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import Foundation
import AVFoundation
import Photos

protocol CheckPermissionsUseCase {
    func hasAccessToCamera() -> Bool
    func hasAccessToGallery() -> Bool
    func requestAccessToGallery(onResult: @escaping (_ isSuccess: Bool) -> Void)
    func requestAccessToCamera(onResult: @escaping (_ isSuccess: Bool) -> Void)
    func getErrorMessage(for permission: PrivatePermissionType) -> (title: String, message: String)
}

enum PrivatePermissionType {
    case camera
    case gallery
}

class CheckPermissionsUseCaseImp: CheckPermissionsUseCase {
        
    func hasAccessToCamera() -> Bool {
        if AVCaptureDevice.authorizationStatus(for: .video) ==  .authorized {
            return true
        } else {
            return false
        }
    }
    
    
    func hasAccessToGallery() -> Bool {
        let status = PHPhotoLibrary.authorizationStatus()
        switch status {
        case .authorized:
            return true

        default:
            return false
        }
    }
    
    func requestAccessToGallery(onResult: @escaping (_ isSuccess: Bool) -> Void) {
        let status = PHPhotoLibrary.authorizationStatus()
        switch status {
            
        case .authorized:
            onResult(true)
            
        case .notDetermined:
            PHPhotoLibrary.requestAuthorization({ (status) in
                if status == PHAuthorizationStatus.authorized {
                    onResult(true)
                } else {
                    onResult(false)
                }
            })
            
        default:
            onResult(false)
        }
    }
    
    
    func requestAccessToCamera(onResult: @escaping (_ isSuccess: Bool) -> Void) {
        if AVCaptureDevice.authorizationStatus(for: .video) ==  .authorized {
            onResult(true)
        } else {
            AVCaptureDevice.requestAccess(for: .video) { (granted: Bool) in
                if granted {
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
        }
    }
    
    func getErrorMessage(for permission: PrivatePermissionType) -> (title: String, message: String) {
        var title = "Ранее вы запретили доступ к "
        var message = "Перейти в настройки чтобы изменить разрешения для "
        
        switch permission {
        case .camera:
            title.append(contentsOf: "камере.")
            message.append(contentsOf: "камеры?")
            return (title, message)
            
        case .gallery:
            title.append(contentsOf: "галерее.")
            message.append(contentsOf: "галереи?")
            return (title, message)
        }
    }
}

