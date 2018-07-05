
import Foundation
import SharediOS
import AFNetworking

class NewsRepositoryImpl: SOSNewsRepositoryIos {
    
    override func getNewsDataCallback(callback: SOSStdlibContinuation) {
        let manager = AFHTTPSessionManager.init(sessionConfiguration: URLSessionConfiguration.default)
        manager.responseSerializer.acceptableContentTypes = nil
        manager.requestSerializer.httpMethodsEncodingParametersInURI = ["GET", "HEAD"]
        manager.get("http://portal.kotlin-academy.com/news", parameters: nil, success: { (task, response) in
            guard let res = response as? [AnyHashable: Any] else {
                callback.resumeWithException(exception: SOSStdlibThrowable(message: "Error casting response"))
                return
            }
            guard let articlesAsHashes = res["articles"] as? [[AnyHashable: Any]] else {
                callback.resumeWithException(exception: SOSStdlibThrowable(message: "Error casting articles"))
                return
            }
            let articles = articlesAsHashes.map({ a -> SOSArticle in
                let d = a["data"] as! [AnyHashable: Any]
                return SOSArticle(
                    id: a["id"] as! Int32,
                    data: SOSArticleData(
                        title: d["title"] as! String,
                        subtitle: d["subtitle"] as! String,
                        imageUrl: d["imageUrl"] as! String,
                        url: d["url"] as? String? ?? nil,
                        occurrence: SOSDateTime()
                    ),
                    dateTime: SOSDateTime()
                )
            })
            guard let infosAsHashes = res["infos"] as? [[AnyHashable: Any]] else {
                callback.resumeWithException(exception: SOSStdlibThrowable(message: "Error casting infos"))
                return
            }
            let infos = infosAsHashes.map({ i -> SOSInfo in
                let d = i["data"] as! [AnyHashable: Any]
                return SOSInfo(
                    id: i["id"] as! Int32,
                    data: SOSInfoData(
                        title: d["title"] as! String,
                        imageUrl: d["imageUrl"] as! String,
                        description: d["description"] as! String,
                        sources: d["sources"] as! String,
                        url: d["url"] as? String? ?? nil,
                        author: d["author"] as? String? ?? nil,
                        authorUrl: d["authorUrl"] as? String? ?? nil
                    ),
                    dateTime: SOSDateTime(),
                    accepted: true
                )
            })
            guard let puzzlersAsHashes = res["puzzlers"] as? [[AnyHashable: Any]] else {
                callback.resumeWithException(exception: SOSStdlibThrowable(message: "Error casting puzzlers"))
                return
            }
            let puzzlers = puzzlersAsHashes.map({ p -> SOSPuzzler in
                let d = p["data"] as! [AnyHashable: Any]
                return SOSPuzzler(
                    id: p["id"] as! Int32,
                    data: SOSPuzzlerData(
                        title: d["title"] as! String,
                        level: d["level"] as? String? ?? nil,
                        actualQuestion: d["actualQuestion"] as! String,
                        codeQuestion: d["codeQuestion"] as! String,
                        question: d["question"] as! String,
                        answers: d["answers"] as! String,
                        correctAnswer: d["correctAnswer"] as! String,
                        explanation: d["explanation"] as! String,
                        author: d["author"] as? String? ?? nil,
                        authorUrl: d["authorUrl"] as? String? ?? nil
                    ),
                    dateTime: SOSDateTime(),
                    accepted: true
                )
            })
            let data = SOSNewsData(articles: articles, infos: infos, puzzlers: puzzlers)
            callback.resume(value: data)
        }) { (task, erro) in
            callback.resumeWithException(exception: SOSStdlibThrowable(message: erro.localizedDescription))
        }
    }
}
