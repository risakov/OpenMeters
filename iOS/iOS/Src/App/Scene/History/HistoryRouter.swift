//
//  HistoryRouter.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class HistoryRouter: BaseRouter {
    
    weak var view: UIViewController!


    init(_ view: HistoryViewController) {
        self.view = view
    }
    
    func close() {
        self.view.navigationController?.popViewController(animated: true)
    }

}
