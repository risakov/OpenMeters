//
//  CameraViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit
import AVFoundation
import RxSwift
import Photos
import BSImagePicker


protocol CameraView: BaseView {
    func showPreloaderView()
    func hidePreloaderView()
}

class CameraViewController: UIViewController, UINavigationControllerDelegate {
    
    @IBOutlet weak var previewView: UIView!
    @IBOutlet weak var flashlightButton: UIButton!
    @IBOutlet weak var preloaderView: UIView!
    @IBOutlet weak var customActivityIndicator: UIView!
    var captureSession: AVCaptureSession!
    var videoPreviewLayer: AVCaptureVideoPreviewLayer!
    
    var activityIndicator: MyIndicator!
    
    override var prefersStatusBarHidden: Bool {
        return true
    }
    
    var presenter: CameraPresenter!
    
    var cameraOutput = AVCapturePhotoOutput()
    var prevLayer: AVCaptureVideoPreviewLayer?
    
    
    @IBAction func onFlashlightButtonTap(_ sender: Any) {
        self.toggleFlash()
    }
    
    @IBAction func onImagesToPickButtonTap(_ sender: Any) {
        let imagePicker = ImagePickerController()
        imagePicker.settings.selection.max = 5
        imagePicker.settings.theme.selectionStyle = .numbered
        imagePicker.settings.fetch.assets.supportedMediaTypes = [.image, .video]
        imagePicker.settings.selection.unselectOnReachingMax = true
        
        self.presentImagePicker(imagePicker, select: { (asset) in
            print("Selected: \(asset)")
        }, deselect: { (asset) in
            print("Deselected: \(asset)")
        }, cancel: { (assets) in
            print("Canceled with selections: \(assets)")
        }, finish: { (assets) in
            print("Finished with selections: \(assets)")
            self.presenter.getThumbnailForAssets(assets: assets)
        }, completion: {
        })
    }
    
    @IBAction func backButton(_ sender: Any) {
        
        self.tabBarController?.tabBar.tabsVisiblty(true)
        self.navigationController?.setNavigationBarHidden(false, animated: false)
        self.presenter.openHistoryScene()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        CameraConfigurator().configure(view: self)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        let activityInd = MyIndicator(frame: customActivityIndicator.frame, image: R.image.loader()!)
        self.activityIndicator = activityInd
        self.customActivityIndicator.addSubview(activityIndicator)
        self.tabBarController?.tabBar.tabsVisiblty(false)
        self.navigationController?.setNavigationBarHidden(true, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        self.tabBarController?.tabBar.tabsVisiblty(true)
        self.navigationController?.setNavigationBarHidden(false, animated: animated)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.startCamera()
    }
    
    func startCamera() {
        captureSession = AVCaptureSession()
        captureSession.sessionPreset = AVCaptureSession.Preset.photo
        cameraOutput = AVCapturePhotoOutput()
        
        if let device = AVCaptureDevice.default(for: .video),
           let input = try? AVCaptureDeviceInput(device: device) {
            if (captureSession.canAddInput(input)) {
                captureSession.addInput(input)
                if (captureSession.canAddOutput(cameraOutput)) {
                    captureSession.addOutput(cameraOutput)
                    prevLayer = AVCaptureVideoPreviewLayer(session: captureSession)
                    prevLayer?.frame.size = previewView.frame.size
                    prevLayer?.videoGravity = AVLayerVideoGravity.resizeAspectFill
                    previewView.layer.addSublayer(prevLayer!)
                    captureSession.startRunning()
                }
            }
        }
    }
    
    func capturePhoto() {
        let settings = AVCapturePhotoSettings()
        let previewPixelType = settings.availablePreviewPhotoPixelFormatTypes.first!
        let previewFormat = [
            kCVPixelBufferPixelFormatTypeKey as String: previewPixelType,
            kCVPixelBufferWidthKey as String: 160,
            kCVPixelBufferHeightKey as String: 160
        ]
        settings.previewPhotoFormat = previewFormat
        cameraOutput.capturePhoto(with: settings, delegate: self)
    }
    
    func toggleFlash() {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return }
        guard device.hasTorch else { return }
        
        do {
            try device.lockForConfiguration()
            
            if (device.torchMode == AVCaptureDevice.TorchMode.on) {
                device.torchMode = AVCaptureDevice.TorchMode.off
                flashlightButton.setImage(R.image.flashlightOff(), for: .normal)
                flashlightButton.tintColor = .white
                
            } else {
                do {
                    flashlightButton.setImage(R.image.flashlightOn(), for: .normal)
                    flashlightButton.tintColor = R.color.blue()
                    try device.setTorchModeOn(level: 1.0)
                } catch {
                    print(error)
                }
            }
            device.unlockForConfiguration()
        } catch {
            print(error)
        }
    }
}

extension CameraViewController: AVCapturePhotoCaptureDelegate {
    
    @IBAction func didTakePhoto(_ sender: Any) {
        self.capturePhoto()
    }
    
    func photoOutput(_ output: AVCapturePhotoOutput, didFinishProcessingPhoto photo: AVCapturePhoto, error: Error?) {
        
        let imageData = photo.fileDataRepresentation()
        if let data = imageData {
            self.presenter.sendSingleImage(data)
        }
        
        //        let image = UIImage(data: imageData)
    }
}

extension CameraViewController: CameraView {
    func showPreloaderView() {
        self.preloaderView.isHidden = false
        self.activityIndicator.startAnimating()
    }
    
    func hidePreloaderView() {
        self.preloaderView.isHidden = true
        self.activityIndicator.stopAnimating()
    }
    
}



