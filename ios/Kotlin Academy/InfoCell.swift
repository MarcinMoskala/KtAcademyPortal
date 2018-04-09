import UIKit
import SDWebImage
import SafariServices

class InfoCell: UITableViewCell {
    
    @IBOutlet weak var imgV: UIImageView!
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    @IBOutlet weak var authBtn: UIButton!
    
    @IBOutlet weak var authLbl: UILabel!
    
    var item:[AnyHashable: Any]?
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
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
            imgV.sd_setImage(with: URL(string: dt["imageUrl"] as! String))
            titleLbl.text = dt["title"] as? String
            subtitleLbl.text = dt["description"] as? String
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
        }
    }
    
}
