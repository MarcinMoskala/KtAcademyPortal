import UIKit
import SDWebImage
import SafariServices
import KotlinAcademyCommon
import Reusable

class InfoCell: UITableViewCell, NibReusable {
    static var reuseIdentifier: String { return "InfoCell" }

    @IBOutlet weak var imgV: UIImageView!
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    @IBOutlet weak var authBtn: UIButton!
    
    @IBOutlet weak var authLbl: UILabel!
    
    var item: KACInfo!
    
    @IBAction func authBtnTouched(_ sender: Any) {
        openUrl(item?.authorUrl)
    }
    
    func config(_ item: KACInfo) {
        self.item = item
        imgV.sd_setImage(with: URL(string: item.imageUrl))
        titleLbl.text = item.title
        subtitleLbl.text = item.desc as String
        displayAuthor(authBtn, authLbl, item.author, item.authorUrl)
    }
}
