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

    @IBOutlet weak var meterTypeCollectionView: UICollectionView!
    @IBOutlet weak var historyTableView: UITableView!
    
    var presenter: HistoryPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
}

extension HistoryViewController: UITableViewDelegate {
    
}

//extension HistoryViewController: UITableViewDataSource {
//    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
//        return 10
//    }
//
//    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        
//    }
//
//
//}

extension HistoryViewController : HistoryView {
    func reloadCollection() {
    }
    
    func endRefreshing() {
    }
}
