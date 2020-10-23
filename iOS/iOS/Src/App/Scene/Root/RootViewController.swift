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
        RootConfiguratorImp().configure(view: self)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }
    


}

extension RootViewController: RootView {

    func openHistoryTab() {
        self.presenter.openHistoryTab()
    }
    
    func openCameraTab() {
        self.presenter.openCameraTab()
    }
    
    func openProfileTab() {
        self.presenter.openProfileTab()
    }

}
