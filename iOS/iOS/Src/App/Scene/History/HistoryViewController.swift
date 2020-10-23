//
//  HistoryViewController.swift
//  iOS
//
//  Created by Роман on 23.10.2020.
//

import UIKit

protocol HistoryView: BaseView
{
    func reloadCollection()
    func endRefreshing()
}

class HistoryViewController: UIViewController {

    @IBOutlet weak var meterTypeCollectionView: UICollectionView!
    @IBOutlet weak var historyTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    

}
