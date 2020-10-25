//
//  HistoryConfigurator.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit


class HistoryConfigurator {

    func configure(view: HistoryViewController) {
        let router = HistoryRouter(view)
        let presenter = HistoryPresenter(view, router)
        view.presenter = presenter
    }

    static func open(navigationController: UINavigationController) {
        let view = HistoryConfigurator.getVC()
        HistoryConfigurator().configure(view: view)
        navigationController.pushViewController(view, animated: true)

    }

    static func getVC() -> HistoryViewController {
        let view = R.storyboard.history.historyVC()!
        return view
    }

}
