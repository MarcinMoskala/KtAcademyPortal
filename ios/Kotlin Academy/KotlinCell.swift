import UIKit
import SDWebImage
import SafariServices
import SharediOS

class KotlinCell: UITableViewCell  {
    
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    @IBOutlet weak var authBtn: UIButton!
    @IBOutlet weak var authLbl: UILabel!
    
    @IBOutlet weak var answersLbl: UILabel!
    @IBOutlet weak var answerLbl: UILabel!
    @IBOutlet weak var explanationLbl: UILabel!
    
    @IBOutlet weak var showBtn: UIButton!
    var item: SOSPuzzler?
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
        if let item = item {
            let idx = Int(item.id)
            ViewController.shownIds[idx] = true
            NotificationCenter.default.post(name: NSNotification.Name(rawValue: "UpdateTableView"), object: row)
        }
    }
    @IBAction func authBtnTouched(_ sender: Any) {
        if let url = item?.authorUrl {
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
    
    func config(_ item: SOSPuzzler) {
        self.item = item
        titleLbl.text = item.title
        subtitleLbl.text = item.codeQuestion
        answersLbl.text = item.answers
        authBtn.isHidden = true
        authLbl.isHidden = true
        if let authName = item.author as? String {
            authBtn.isHidden = false
            authLbl.isHidden = false
            
            let textRange = NSRange(location: 0, length: authName.count)
            let attributedText = NSMutableAttributedString(string: authName)
            attributedText.addAttribute(NSAttributedStringKey.foregroundColor, value: UIColor(red: 244.0/255.0, green: 120.0/255.0, blue: 39.0/255.0, alpha: 1.0) , range: textRange)
            attributedText.addAttribute(NSAttributedStringKey.underlineStyle, value: NSUnderlineStyle.styleSingle.rawValue, range: textRange)
            
            authBtn.setAttributedTitle(attributedText, for: .normal)
        }
        let idx = Int(item.id)
        if ViewController.shownIds[idx] == true {
            answerLbl.text = item.correctAnswer
            explanationLbl.text = item.explanation
        }
    }
    
}
