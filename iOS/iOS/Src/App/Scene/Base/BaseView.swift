//
//  BaseView.swift
//  iOS
//
//  Created by Роман on 24.10.2020.
//

import UIKit

typealias ActionChoiceDialog = (title: String, action: () -> Void)

protocol BaseView: class {
}

extension BaseView {

    func showDialog(message: String, action: ((UIAlertAction) -> Void)? = nil) {
        let alert = UIAlertController(title: nil, message: message, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "Хорошо", style: .default, handler: action)
        alert.addAction(okAction)
        (self as? UIViewController)?.present(alert, animated: true)
    }
    
    func showWarningDialog(message: String, action: ((UIAlertAction) -> Void)? = nil) {
        let alert = UIAlertController(title: "Внимание", message: message, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "Хорошо", style: .default, handler: action)
        alert.addAction(okAction)
        okAction.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1), forKey: "titleTextColor")
        
        (self as? UIViewController)?.present(alert, animated: true)
    }

    func showChoiceDialog(message: String,
                          positiveMessage: String,
                          negativeMessage: String,
                          onChoice: @escaping (_ isPositive: Bool) -> Void) {
        let alert = UIAlertController(title: nil,
                                      message: message,
                                      preferredStyle: .alert)
        let positiveAction = UIAlertAction(title: positiveMessage,
                                           style: .default,
                                           handler: { _ in
                                               onChoice(true)
                                           })
        positiveAction.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1),
                                forKey: "titleTextColor")
        let negativeAction = UIAlertAction(title: negativeMessage,
                                           style: .cancel,
                                           handler: { _ in
                                               onChoice(false)
                                           })
        negativeAction.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1),
                                forKey: "titleTextColor")

        alert.addAction(negativeAction)
        alert.addAction(positiveAction)
        (self as? UIViewController)?.present(alert, animated: true)
    }

    func showMultiplyChoiceDialog(message: String?,
                                  actionsChoice: [ActionChoiceDialog],
                                  preferredStyle: UIAlertController.Style = .alert,
                                  sender: UIView? = nil,
                                  withoutCancel: Bool? = false) {

        let sheet = UIAlertController(title: nil,
                                      message: message,
                                      preferredStyle: preferredStyle)

        actionsChoice.forEach { (title, action) in

            let button = UIAlertAction(title: title,
                                       style: .default,
                                       handler: { _ in action() })
            button.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1), forKey: "titleTextColor")
            sheet.addAction(button)
        }

        if withoutCancel != true {
            let cancelButton = UIAlertAction(title: "Отмена",
                                             style: .cancel,
                                             handler: nil)
            cancelButton.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1), forKey: "titleTextColor")
            sheet.addAction(cancelButton)

        }
        if preferredStyle == .actionSheet {

            if let sender = sender, let alert = sheet.popoverPresentationController {
                alert.sourceView = sender
                alert.sourceRect = CGRect(x: sender.bounds.midX, y: sender.bounds.midY, width: 0, height: 0)
            }
        }

        (self as? UIViewController)?.present(sheet, animated: true)
    }


    func showErrorDialog(message: String,
                         action: ((UIAlertAction) -> Void)? = nil,
                         onShow: (() -> Void)? = nil) {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
            let alert = UIAlertController(title: "Ошибка",
                                          message: message,
                                          preferredStyle: .alert)
            let okAction = UIAlertAction(title: "Продолжить",
                                         style: .default,
                                         handler: action)
            alert.addAction(okAction)
            (self as? UIViewController)?.present(alert, animated: true, completion: onShow)
        }
    }

    func showInputDialog(title: String? = nil,
                         subtitle: String? = nil,
                         actionTitle: String? = "ОК",
                         cancelTitle: String? = "Отмена",
                         inputPlaceholder: String? = nil,
                         inputKeyboardType: UIKeyboardType = UIKeyboardType.default,
                         cancelHandler: ((UIAlertAction) -> Void)? = nil,
                         actionHandler: ((_ text: String?) -> Void)? = nil) {

        let alert = UIAlertController(title: title, message: subtitle, preferredStyle: .alert)
        alert.addTextField { (textField: UITextField) in
            textField.placeholder = inputPlaceholder
            textField.keyboardType = inputKeyboardType
        }

        let mainAction = UIAlertAction(title: actionTitle, style: .default, handler: { _ in
            guard let textField = alert.textFields?.first else {
                fatalError("Not found text fields in Input dialog!")
            }
            actionHandler?(textField.text)
        })
        let cancelAction = UIAlertAction(title: cancelTitle, style: .cancel, handler: cancelHandler)

        mainAction.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1), forKey: "titleTextColor")
        cancelAction.setValue(#colorLiteral(red: 0, green: 0, blue: 0, alpha: 1), forKey: "titleTextColor")

        alert.addAction(cancelAction)
        alert.addAction(mainAction)

        (self as? UIViewController)?.present(alert, animated: true)
    }

}
