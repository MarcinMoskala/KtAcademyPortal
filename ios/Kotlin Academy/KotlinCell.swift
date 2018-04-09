import UIKit
import SDWebImage
import SafariServices

class KotlinCell: UITableViewCell  {
    
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    @IBOutlet weak var authBtn: UIButton!
    @IBOutlet weak var authLbl: UILabel!
    
    @IBOutlet weak var answersLbl: UILabel!
    @IBOutlet weak var answerLbl: UILabel!
    @IBOutlet weak var explanationLbl: UILabel!
    
    @IBOutlet weak var showBtn: UIButton!
    var item:[AnyHashable: Any]?
    var row = 0
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
    @IBAction func showBtnTouched(_ sender: Any) {
        if let idx = item!["id"] as? Int {
            ViewController.shownIds[idx] = true
            NotificationCenter.default.post(name: NSNotification.Name(rawValue: "UpdateTableView"), object: row)
        }
    }
    @IBAction func authBtnTouched(_ sender: Any) {
        let dt = item!["data"] as! [AnyHashable: Any]
        if let url = dt["authorUrl"] as? String {
            print("\(url)")
            let vc = SFSafariViewController(url: URL(string: url)!)
            self.topMostController().present(vc, animated: true, completion: nil)
        }
    }
    
    func topMostController() -> UIViewController {
        var topController: UIViewController = (UIApplication.shared.keyWindow?.rootViewController)!
        while (topController.presentedViewController != nil) && !(topController.presentedViewController is UIAlertController) {
            topController = topController.presentedViewController!
        }
        return topController
    }
    
    func config(_ item: [AnyHashable: Any]) {
        self.item = item
        if let dt = item["data"] as? [AnyHashable: Any] {
            titleLbl.text = dt["title"] as? String
            subtitleLbl.text = dt["question"] as? String
            answersLbl.text = dt["answers"] as? String
            authBtn.isHidden = true
            authLbl.isHidden = true
            if let authName = dt["author"] as? String {
                authBtn.isHidden = false
                authLbl.isHidden = false
                
                let textRange = NSRange(location: 0, length: authName.count)
                let attributedText = NSMutableAttributedString(string: authName)
                attributedText.addAttribute(NSAttributedStringKey.foregroundColor, value: UIColor(red: 244.0/255.0, green: 120.0/255.0, blue: 39.0/255.0, alpha: 1.0) , range: textRange)
                attributedText.addAttribute(NSAttributedStringKey.underlineStyle, value: NSUnderlineStyle.styleSingle.rawValue, range: textRange)
                
                authBtn.setAttributedTitle(attributedText, for: .normal)
            }
            let idx = item["id"] as! Int
            if ViewController.shownIds[idx] == true {
                answerLbl.text = dt["correctAnswer"] as? String
                explanationLbl.text = dt["explanation"] as? String
            } else {

            }
        }
    }
    
}
