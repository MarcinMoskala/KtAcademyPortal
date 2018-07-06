import UIKit
import SDWebImage
import SafariServices
import KotlinAcademyCommon

class PuzzlerCell: UITableViewCell  {
    
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    @IBOutlet weak var authBtn: UIButton!
    @IBOutlet weak var authLbl: UILabel!
    
    @IBOutlet weak var answersLbl: UILabel!
    @IBOutlet weak var answerLbl: UILabel!
    @IBOutlet weak var explanationLbl: UILabel!
    
    @IBOutlet weak var showBtn: UIButton!
    var item: KACPuzzler!
    var row = 0
    
    @IBAction func showBtnTouched(_ sender: Any) {
        ViewController.shownIds[item.id] = true
        NotificationCenter.default.post(name: NSNotification.Name(rawValue: "UpdateTableView"), object: row)
    }
    
    @IBAction func authBtnTouched(_ sender: Any) {
        openUrl(item?.authorUrl)
    }
    
    func config(_ item: KACPuzzler) {
        self.item = item
        titleLbl.text = item.title
        subtitleLbl.text = item.codeQuestion
        answersLbl.text = item.answers
        displayAuthor(authBtn, authLbl, item.author, item.authorUrl)
        if ViewController.shownIds[item.id] == true {
            answerLbl.text = item.correctAnswer
            explanationLbl.text = item.explanation
        }
    }
    
}
