//
//  HistoryViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol HistoryView: BaseView {
    func reloadCollection()
    func endRefreshing()
}

class HistoryViewController: UIViewController {

    @IBOutlet weak var historyTableView: UITableView!
    
    var presenter: HistoryPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.registerCells()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.titleTextAttributes = [NSAttributedString.Key.font: UIFont(name: R.font.graphikRegular.fontName, size: 17)!]

    }
    
    func registerCells() {
        historyTableView.register(UINib(resource: R.nib.historyCell), forCellReuseIdentifier: R.reuseIdentifier.historyCell.identifier)
        historyTableView.register(UINib(resource: R.nib.historyCellWithStackView), forCellReuseIdentifier: R.reuseIdentifier.historyCellWithStackView.identifier)

        self.historyTableView.delegate = self
        self.historyTableView.dataSource = self

    }
    
}

extension HistoryViewController: UITableViewDelegate {
    
}

extension HistoryViewController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 91
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 10
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row == 0 {
            let firstCell =  tableView.dequeueReusableCell(withIdentifier: R.reuseIdentifier.historyCellWithStackView, for: indexPath)!
            firstCell.setup()
            return firstCell
        }
        
        let cell =  tableView.dequeueReusableCell(withIdentifier: R.reuseIdentifier.historyCell, for: indexPath)!
        cell.setup()
        
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        
    }
}

extension HistoryViewController : HistoryView {
    func reloadCollection() {
    }
    
    func endRefreshing() {
    }
}
