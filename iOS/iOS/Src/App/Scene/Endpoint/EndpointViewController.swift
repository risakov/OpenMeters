//
//  EndpointViewController.swift
//  iOS
//
//  Created by Роман on 25.10.2020.
//

import UIKit

protocol EndpointView: BaseView {
    
}

class EndpointViewController: UIViewController {

    var presenter: EndpointPresenter!

    @IBOutlet weak var endpointTextField: UITextField!
    
    @IBAction func onReadyButtonTap(_ sender: UIButton) {
        guard let text = self.endpointTextField.text else {
            showWarningDialog(message: "Вы должны ввести адрес сервера.")
            return
        }
        self.presenter.setEndpoint(text)
        self.presenter.openRootScene()
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        self.endpointTextField.delegate = self
        EndpointConfigurator().configure(view: self)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
    }

}

extension EndpointViewController: EndpointView {

    func open() {
        self.presenter.openRootScene()
    }

}

extension EndpointViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
}
