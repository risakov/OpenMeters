//
//  TabBar + visibility.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

extension UITabBar {
    func tabsVisiblty(_ isVisiblty: Bool = true){
        if isVisiblty {
            self.isHidden = false
            self.layer.zPosition = 0
        } else {
            self.isHidden = true
            self.layer.zPosition = -1
        }
    }
}
