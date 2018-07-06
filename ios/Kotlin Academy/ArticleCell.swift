import UIKit
import SDWebImage
import KotlinAcademyCommon
import Reusable

class ArticleCell: UITableViewCell, NibReusable {
    static var reuseIdentifier: String { return "ArticleCell" }
    
    @IBOutlet weak var imgV: UIImageView!
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var subtitleLbl: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func config(_ item: KACArticle) {
        imgV.sd_setImage(with: URL(string: item.imageUrl))
        titleLbl.text = item.title
        subtitleLbl.text = item.subtitle
    }
    
}
