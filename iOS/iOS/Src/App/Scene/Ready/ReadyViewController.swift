//
//  ReadyViewController.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import UIKit

protocol ReadyView: BaseView {
    
}

class ReadyViewController: UIViewController {

    var presenter: ReadyPresenter!
    
    @IBAction func onHistoryButtonTap(_ sender: UIButton) {
        self.presenter.openHistoryScene()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
}

extension ReadyViewController: ReadyView {
    
}
