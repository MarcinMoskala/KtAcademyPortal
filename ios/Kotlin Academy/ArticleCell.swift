import UIKit
import SDWebImage

class ArticleCell: UITableViewCell {

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
    
    func config(_ item: [AnyHashable: Any]) {
        if let dt = item["data"] as? [AnyHashable: Any] {
            imgV.sd_setImage(with: URL(string: dt["imageUrl"] as! String))
            titleLbl.text = dt["title"] as? String
            subtitleLbl.text = dt["subtitle"] as? String
        }
    }
    
}
