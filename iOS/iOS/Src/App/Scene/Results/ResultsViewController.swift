//
//  ResultsViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol ResultsView: BaseView {
    
}

class ResultsViewController: UIViewController {

    var presenter: ResultsPresenter!
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
}

extension ResultsViewController: ResultsView {
    
}
