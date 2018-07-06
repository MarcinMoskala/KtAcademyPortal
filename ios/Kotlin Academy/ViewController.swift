import UIKit
import AFNetworking
import SafariServices
import SVProgressHUD
import KotlinAcademyCommon

class ViewController: UIViewController, KACNewsView {
    static var shownIds = [Int32:Bool]()
    @IBOutlet weak var tableView: UITableView!
    var items = [KACNews]()
    private var refreshControl: UIRefreshControl?
    var noMatchesLabel: UILabel?
    
    var presenter: KACNewsPresenter!
    
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
        get { return SVProgressHUD.isVisible() }
        set(value) {
            if value {
                SVProgressHUD.show()
            } else {
                SVProgressHUD.dismiss()
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        presenter = KACNewsPresenter(
            uiContext: KACMainQueueDispatcher(),
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
        tableView.reloadRows(at: [indexPath], with: UITableViewRowAnimation.none)
    }
    
    func showList(news: [KACNews]) {
        items = news
        tableView.reloadData()
    }
    
    func logError(error: KACStdlibThrowable) {
        print(error.message ?? "Unknown error")
    }
    
    func showError(error: KACStdlibThrowable) {
        showError(error.message)
    }

    func showError(_ error: String?) {
        let alertController = UIAlertController(title: "Error", message: error, preferredStyle: .alert)
        alertController.popoverPresentationController?.sourceView = self.view
        alertController.popoverPresentationController?.sourceRect = CGRect(x: 0, y: UIScreen.main.bounds.size.height-20, width: UIScreen.main.bounds.size.width, height: 20)

        let okAction = UIAlertAction(title: "Ok", style: .default)
        alertController.addAction(okAction)
        present(alertController, animated: true, completion: nil)
        showNoItem()
        tableView.reloadData()
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
        tableView.register(UINib(nibName: "PuzzlerCell", bundle: nil), forCellReuseIdentifier: "PuzzlerCell")
        tableView.register(UINib(nibName: "PuzzlerShownCell", bundle: nil), forCellReuseIdentifier: "PuzzlerShownCell")
        tableView.register(cellType: ArticleCell.self)
        tableView.register(cellType: InfoCell.self)
        
        refreshControl = UIRefreshControl()
        refreshControl?.addTarget(self, action: #selector(self.refreshData), for: .valueChanged)
        tableView?.addSubview(refreshControl!)
        
        tableView.delegate = self
        tableView.dataSource = self
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
        switch item {
        case let item as KACArticle:
            let cell: ArticleCell = tableView.dequeueReusableCell(for: indexPath)
            cell.config(item)
            return cell
        case let item as KACInfo:
            let cell: InfoCell = tableView.dequeueReusableCell(for: indexPath)
            cell.config(item)
            return cell
        default:
            let item = item as! KACPuzzler
            let CellIdentifier = ViewController.shownIds[item.id] == true ? "PuzzlerShownCell" : "PuzzlerCell"
            let cell = self.tableView?.dequeueReusableCell(withIdentifier: CellIdentifier) as! PuzzlerCell
            cell.config(item)
            cell.selectionStyle = .none
            cell.row = indexPath.row
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        let item = items[indexPath.row]
        switch item {
        case let item as KACArticle:
            openUrl(item.url)
        case let item as KACInfo:
            openUrl(item.url)
        default: break
        }
    }
}

