//
//  RootViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit


protocol RootView {

}


class RootViewController: UITabBarController {

    var presenter: RootPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
    


}

extension RootViewController: RootView {

    func openHistoryTab() {
        self.presenter.openHistoryScene()
    }
    
    func openCameraTab() {
        self.presenter.openCameraScene()
    }
    
    func openProfileTab() {
        self.presenter.openProfileScene()
    }

}
