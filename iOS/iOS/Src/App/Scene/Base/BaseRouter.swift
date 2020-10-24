//
//  BaseRouter.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

protocol BaseRouter {

    var view: UIViewController! { get }
}

extension BaseRouter {

    func dismiss(completion: (() -> Void)? = nil) {
        guard let view = self.view else { return }

        view.dismiss(animated: true, completion: completion)
    }

    func pop(completion: (() -> Void)? = nil) {
        guard let view = self.view else { return }
        view.navigationController?.popViewController(animated: true)
    }

    func popToRoot() {
        guard let view = self.view else { return }

        view.navigationController?.popToRootViewController(animated: true)
    }
}
