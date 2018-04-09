import UIKit
import AFNetworking
import SafariServices
import SVProgressHUD

class ViewController: UIViewController {
    static var shownIds = [Int:Bool]()
    
    @IBOutlet weak var tableView: UITableView!
    
    var items = [[AnyHashable: Any]]()
    
    
    private var refreshControl: UIRefreshControl?
    var noMatchesLabel: UILabel?

    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        if #available(iOS 11.0, *) {
            self.navigationController?.navigationBar.prefersLargeTitles = true
        } else {
            // Fallback on earlier versions
        }
        self.initTableView()
        SVProgressHUD.show()
        self.getItems()
        NotificationCenter.default.addObserver(self, selector: #selector(updateTableView(_:)), name: NSNotification.Name(rawValue: "UpdateTableView"), object: nil)
    }
    
    @objc func updateTableView(_ not:Notification) {
        let row = not.object as! Int
        let indexPath = IndexPath(row: row, section: 0)
        self.tableView.reloadRows(at: [indexPath], with: UITableViewRowAnimation.none)
    }
    
    func getItems() {
        let manager = AFHTTPSessionManager.init(sessionConfiguration: URLSessionConfiguration.default)
        manager.responseSerializer.acceptableContentTypes = nil
        manager.requestSerializer.httpMethodsEncodingParametersInURI = ["GET", "HEAD"]
        
        manager.get("http://portal.kotlin-academy.com/news", parameters: nil, progress: nil, success: { (task, response) in
            self.items = [[AnyHashable: Any]]()
            if let res = response as? [AnyHashable: Any] {
                if let articles = res["articles"] as? [[AnyHashable: Any]] {
                    for article in articles {
                        var newArt = article
                        newArt["type"] = "article"
                        self.items.append(newArt)
                    }
                }
                if let articles = res["infos"] as? [[AnyHashable: Any]] {
                    for article in articles {
                        var newArt = article
                        newArt["type"] = "info"
                        self.items.append(newArt)
                    }
                }
                if let articles = res["puzzlers"] as? [[AnyHashable: Any]] {
                    for article in articles {
                        var newArt = article
                        newArt["type"] = "puzzler"
                        self.items.append(newArt)
                    }
                }
                
                self.items.sort(by: { (item1, item2) -> Bool in
                    let dts1 = item1["dateTime"] as! String
                    let dts2 = item2["dateTime"] as! String
                    
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
                    
                    let dt1 = dateFormatter.date(from: dts1)!
                    let dt2 = dateFormatter.date(from: dts2)!
                    switch dt1.compare(dt2) {
                    case .orderedAscending:
                        return false
                    case .orderedSame:
                        return true
                    case .orderedDescending:
                        return true
                    }
                })
                SVProgressHUD.dismiss()
                self.refreshControl?.endRefreshing()
                self.tableView.reloadData()
                
                for item in self.items {
                    let dts = item["dateTime"] as! String
                    
                    let dateFormatter = DateFormatter()
                    dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
                    
                    let dt = dateFormatter.date(from: dts)
                    print(dt)
                }
            }
            else {
                SVProgressHUD.dismiss()
                self.showError("Something went wrong")
            }
        }) { (task, erro) in
            SVProgressHUD.dismiss()
            self.showError(erro.localizedDescription)
        }
    }

    func showError(_ error: String?) {
        let alertController = UIAlertController(title: "Error", message: error, preferredStyle: .alert)
        alertController.popoverPresentationController?.sourceView = self.view
        alertController.popoverPresentationController?.sourceRect = CGRect(x: 0, y: UIScreen.main.bounds.size.height-20, width: UIScreen.main.bounds.size.width, height: 20)

        let okAction = UIAlertAction(title: "Ok", style: .default)
        alertController.addAction(okAction)
        self.present(alertController, animated: true, completion: nil)
        self.showNoItem()
        self.tableView.reloadData()
    }

    func showNoItem() {
        if noMatchesLabel == nil {
            noMatchesLabel = UILabel()
            noMatchesLabel?.textColor = UIColor.lightGray
            noMatchesLabel?.text = "No items available"
            noMatchesLabel?.font = UIFont.systemFont(ofSize: 15)
            noMatchesLabel?.sizeToFit()
        }
        noMatchesLabel?.center = CGPoint(x:UIScreen.main.bounds.size.width/2.0, y:tableView.frame.size.height/2.0)
        tableView.insertSubview(noMatchesLabel!, at: 0)
    }
    func hideNoItem() {
        if noMatchesLabel != nil {
            noMatchesLabel?.removeFromSuperview()
        }
    }
    
    func initTableView() {
        self.tableView.register(UINib(nibName: "KotlinCell", bundle: nil), forCellReuseIdentifier: "KotlinCell")
        self.tableView.register(UINib(nibName: "KotlinCellShown", bundle: nil), forCellReuseIdentifier: "KotlinCellShown")
        self.tableView.register(UINib(nibName: "ArticleCell", bundle: nil), forCellReuseIdentifier: "ArticleCell")
        self.tableView.register(UINib(nibName: "InfoCell", bundle: nil), forCellReuseIdentifier: "InfoCell")
        refreshControl = UIRefreshControl()
        refreshControl?.addTarget(self, action: #selector(self.refreshData), for: .valueChanged)
        tableView?.addSubview(refreshControl!)
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        tableView.separatorStyle = .none
    }
    @objc func refreshData(refControl: Any?) {
        self.getItems()
    }

}


extension ViewController: UITableViewDataSource,UITableViewDelegate {
    // MARK: - table view delegate
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if items.count == 0 {
            
        }
        else {
            self.hideNoItem()
        }
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let tp = items[indexPath.row]["type"] as! String
        if tp == "article" {
            let CellIdentifier = "ArticleCell"
            let cell = self.tableView?.dequeueReusableCell(withIdentifier: CellIdentifier) as! ArticleCell
            cell.config(items[indexPath.row] )
            return cell
        }
        if tp == "info" {
            let CellIdentifier = "InfoCell"
            let cell = self.tableView?.dequeueReusableCell(withIdentifier: CellIdentifier) as! InfoCell
            cell.config(items[indexPath.row] )
            return cell
        }
        
        let idx = items[indexPath.row]["id"] as! Int
        let CellIdentifier = ViewController.shownIds[idx] == true ? "KotlinCellShown" : "KotlinCell"
        let cell = self.tableView?.dequeueReusableCell(withIdentifier: CellIdentifier) as! KotlinCell
        cell.config(items[indexPath.row] )
        cell.selectionStyle = .none
        cell.row = indexPath.row
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let tp = items[indexPath.row]["type"] as! String
        if tp == "article" || tp == "info" {
            let dt = items[indexPath.row]["data"] as! [AnyHashable: Any]
            if let url = dt["url"] as? String {
                let vc = SFSafariViewController(url: URL(string: url)!)
                present(vc, animated: true, completion: nil)
            }
        }
    }
}

