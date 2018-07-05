import UIKit
import AFNetworking
import SafariServices
import SVProgressHUD
import SharediOS

class ViewController: UIViewController, SOSNewsView {
    static var shownIds = [Int:Bool]()
    @IBOutlet weak var tableView: UITableView!
    var items = [SOSNews]()
    private var refreshControl: UIRefreshControl?
    var noMatchesLabel: UILabel?
    
    var loading: Bool {
        get { return self.refreshControl!.isRefreshing }
        set(value) {
            if value {
                
            } else {
                self.refreshControl?.endRefreshing()
            }
        }
    }
    
    var refresh: Bool {
        get { return false }
        set(value) {
            if value {
                SVProgressHUD.show()
            } else {
                SVProgressHUD.dismiss()
            }
        }
    }
    
    var presenter: SOSNewsPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        presenter = SOSNewsPresenter(
            uiContext: SOSMainQueueDispatcher(),
            view: self,
            newsRepository: NewsRepositoryImpl()
        )
        
        if #available(iOS 11.0, *) {
            self.navigationController?.navigationBar.prefersLargeTitles = true
        }
        self.initTableView()
        
        presenter.onCreate()
        NotificationCenter.default.addObserver(self, selector: #selector(updateTableView(_:)), name: NSNotification.Name(rawValue: "UpdateTableView"), object: nil)
    }
    
    @objc func refreshData(refControl: Any?) {
        presenter.onRefresh()
    }
    
    @objc func updateTableView(_ not:Notification) {
        let row = not.object as! Int
        let indexPath = IndexPath(row: row, section: 0)
        self.tableView.reloadRows(at: [indexPath], with: UITableViewRowAnimation.none)
    }
    
    func showList(news: [SOSNews]) {
        self.items = news
        self.tableView.reloadData()
    }
    
    func logError(error: SOSStdlibThrowable) {
        
    }
    
    func showError(error: SOSStdlibThrowable) {
        
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
}


extension ViewController: UITableViewDataSource, UITableViewDelegate {
    // MARK: - table view delegate
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if items.count != 0 {
            self.hideNoItem()
        }
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let item = items[indexPath.row]
        if let article = item as? SOSArticle {
            let cell = self.tableView?.dequeueReusableCell(withIdentifier: "ArticleCell") as! ArticleCell
            cell.config(article)
            return cell
        }
        if let info = item as? SOSInfo {
            let cell = self.tableView?.dequeueReusableCell(withIdentifier: "InfoCell") as! InfoCell
            cell.config(info)
            return cell
        }
        let puzzler = item as! SOSPuzzler
        let cell = self.tableView?.dequeueReusableCell(withIdentifier: "KotlinCell") as! KotlinCell
        cell.config(puzzler)
        return cell
//        if tp == "info" {
//            let CellIdentifier = "InfoCell"
//            let cell = self.tableView?.dequeueReusableCell(withIdentifier: CellIdentifier) as! InfoCell
//            cell.config(items[indexPath.row] )
//            return cell
//        }
//
//        let idx = items[indexPath.row]["id"] as! Int
//        let CellIdentifier = ViewController.shownIds[idx] == true ? "KotlinCellShown" : "KotlinCell"
//        let cell = self.tableView?.dequeueReusableCell(withIdentifier: CellIdentifier) as! KotlinCell
//        cell.config(items[indexPath.row] )
//        cell.selectionStyle = .none
//        cell.row = indexPath.row
//        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
//        let tp = items[indexPath.row]["type"] as! String
//        if tp == "article" || tp == "info" {
//            let dt = items[indexPath.row]["data"] as! [AnyHashable: Any]
//            if let url = dt["url"] as? String {
//                let vc = SFSafariViewController(url: URL(string: url)!)
//                present(vc, animated: true, completion: nil)
//            }
//        }
    }
}

