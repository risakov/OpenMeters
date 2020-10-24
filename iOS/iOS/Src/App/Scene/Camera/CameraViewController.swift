//
//  CameraViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit
import AVFoundation
import Photos


protocol CameraView: BaseView {
    
}

class CameraViewController: UIViewController {
    
    @IBOutlet weak var previewView: UIView!
    
    var presenter: CameraPresenter!
    
    override var prefersStatusBarHidden: Bool {
        return true
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        navigationController?.setNavigationBarHidden(true, animated: animated)
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        navigationController?.setNavigationBarHidden(false, animated: animated)
    }
}

extension CameraViewController: CameraView {
    
}
