//
//  HistoryCellWithStackView.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

class HistoryCellWithStackView: UITableViewCell {

    @IBOutlet weak var coldWaterButton: DesignableUIButton!
    @IBOutlet weak var hotWaterButton: DesignableUIButton!
    @IBOutlet weak var electricityButton: DesignableUIButton!
    @IBOutlet weak var buttonsStackView: UIStackView!

    @IBAction func onColdWaterButtonTap(_ sender: DesignableUIButton) {
        coldWaterButton.toogleButton(true)
        hotWaterButton.toogleButton(false)
        electricityButton.toogleButton(false)
    }
    
    @IBAction func onHotWaterButtonTap(_ sender: DesignableUIButton) {
        coldWaterButton.toogleButton(false)
        hotWaterButton.toogleButton(true)
        electricityButton.toogleButton(false)
    }
    
    @IBAction func onElectricityButtonTap(_ sender: DesignableUIButton) {
        coldWaterButton.toogleButton(false)
        hotWaterButton.toogleButton(false)
        electricityButton.toogleButton(true)
    }

    
    func setup() {
        coldWaterButton.toogleButton(true)
        hotWaterButton.toogleButton(false)
        electricityButton.toogleButton(false)
    }
    
}

