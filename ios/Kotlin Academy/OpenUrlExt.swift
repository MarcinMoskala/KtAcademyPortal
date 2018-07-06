import Foundation
import UIKit
import SDWebImage
import SafariServices

extension UITableViewCell {
    
    func openUrl(_ url: String?) {
        guard let url = url else {
            return
        }
        let vc = SFSafariViewController(url: URL(string: url)!)
        self.topMostController().present(vc, animated: true, completion: nil)
    }
    
    func topMostController() -> UIViewController {
        var topController: UIViewController = (UIApplication.shared.keyWindow?.rootViewController)!
        while (topController.presentedViewController != nil) && !(topController.presentedViewController is UIAlertController) {
            topController = topController.presentedViewController!
        }
        return topController
    }
}

extension ViewController {
    func openUrl(_ url: String?) {
        guard let url = url else {
            return
        }
        let vc = SFSafariViewController(url: URL(string: url)!)
        present(vc, animated: true, completion: nil)
    }
}
