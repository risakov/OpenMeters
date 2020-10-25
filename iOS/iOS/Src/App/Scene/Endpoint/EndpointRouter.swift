//
//  EndpointRouter.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import UIKit


class EndpointRouter: BaseRouter {
    weak var view: UIViewController!
    
    init(_ view: EndpointViewController) {
        self.view = view
    }
    
    func openRootScene() {
        if let window = (UIApplication.shared.delegate?.window), window?.rootViewController == nil
            || !(window?.rootViewController?.restorationIdentifier == "firstNavigationViewController") {
            let vc = R.storyboard.root.rootVC()!
            window?.rootViewController = vc
        }
    }
}
