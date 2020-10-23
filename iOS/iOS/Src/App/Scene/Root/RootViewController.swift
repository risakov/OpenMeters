//
//  RootViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit


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

extension RootViewController {

    func openHistoryTab() {
        self.presenter.openMyVideosSceneFromScriptFinal()
    }
    func openMyVideosSceneFromScriptFinal(for product: ProductEntity) {
        self.presenter.openMyVideosSceneFromScriptFinal(for: product, state: .inProgressScripts)
    }
    
    func openProductListSceenOnCreateTab() {
        presenter.openProductListSceenOnCreateTab()
    }
    
    func openMenuSceenOnProfileTab() {
        presenter.openMenuSceenOnProfileTab()
    }
    
    func openVideoProductListSceenOnUserVideoTab() {
        presenter.openVideoProductListSceenOnUserVideoTab()
    }
}
