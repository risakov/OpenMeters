//
//  CameraViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol CameraView: BaseView {
    
}

class CameraViewController: UIViewController {

    var presenter: CameraPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

}

extension CameraViewController: CameraView {
    
}
