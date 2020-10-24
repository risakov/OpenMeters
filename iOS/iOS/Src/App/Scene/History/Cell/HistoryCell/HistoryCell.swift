//
//  HistoryCell.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

class HistoryCell: UITableViewCell {
    @IBOutlet weak var valueLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var serialNumberLabel: UILabel!

    func setup() {
        self.selectionStyle = .none;
        self.valueLabel.text = "8766"
        self.dateLabel.text = "Дата: 12 дек. 2019"
        self.serialNumberLabel.text = "Серийный номер: 67157717"
    }
    
    func createTopBorder() {
        // добавление верхнего сепаратора
        let upperBorder = CALayer()
        upperBorder.frame = CGRect(x: 0, y: 0, width: self.contentView.frame.size.width - 16, height: 1.0)
        upperBorder.backgroundColor = R.color.graySeparator()!.cgColor
        self.contentView.layer.addSublayer(upperBorder)
    }
}
