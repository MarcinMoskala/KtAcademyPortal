
import Foundation
import KotlinAcademyCommon
import AFNetworking

class NewsRepositoryImpl: KACNewsRepositoryIos {
    
    override func getNewsDataCallback(callback: KACStdlibContinuation) {
        let manager = AFHTTPSessionManager.init(sessionConfiguration: URLSessionConfiguration.default)
        manager.responseSerializer.acceptableContentTypes = nil
        manager.requestSerializer.httpMethodsEncodingParametersInURI = ["GET", "HEAD"]
        manager.get("http://portal.kotlin-academy.com/news", parameters: nil, success: { (task, response) in
            guard let res = response as? [AnyHashable: Any] else {
                callback.resumeWithException(exception: KACStdlibThrowable(message: "Error casting response"))
                return
            }
            guard let articlesAsHashes = res["articles"] as? [[AnyHashable: Any]] else {
                callback.resumeWithException(exception: KACStdlibThrowable(message: "Error casting articles"))
                return
            }
            let articles = articlesAsHashes.map({ a -> KACArticle in
                let d = a["data"] as! [AnyHashable: Any]
                let dateTime = KACOrgKotlinacademy.parseDateTime(a["dateTime"] as! String)
                return KACArticle(
                    id: a["id"] as! Int32,
                    data: KACArticleData(
                        title: d["title"] as! String,
                        subtitle: d["subtitle"] as! String,
                        imageUrl: d["imageUrl"] as! String,
                        url: d["url"] as? String? ?? nil,
                        occurrence: dateTime
                    ),
                    dateTime: dateTime
                )
            })
            guard let infosAsHashes = res["infos"] as? [[AnyHashable: Any]] else {
                callback.resumeWithException(exception: KACStdlibThrowable(message: "Error casting infos"))
                return
            }
            let infos = infosAsHashes.map({ i -> KACInfo in
                let d = i["data"] as! [AnyHashable: Any]
                let dateTime = KACOrgKotlinacademy.parseDateTime(i["dateTime"] as! String)
                return KACInfo(
                    id: i["id"] as! Int32,
                    data: KACInfoData(
                        title: d["title"] as! String,
                        imageUrl: d["imageUrl"] as! String,
                        description: d["description"] as! String,
                        sources: d["sources"] as! String,
                        url: d["url"] as? String? ?? nil,
                        author: d["author"] as? String? ?? nil,
                        authorUrl: d["authorUrl"] as? String? ?? nil
                    ),
                    dateTime: dateTime,
                    accepted: true
                )
            })
            guard let puzzlersAsHashes = res["puzzlers"] as? [[AnyHashable: Any]] else {
                callback.resumeWithException(exception: KACStdlibThrowable(message: "Error casting puzzlers"))
                return
            }
            let puzzlers = puzzlersAsHashes.map({ p -> KACPuzzler in
                let d = p["data"] as! [AnyHashable: Any]
                let dateTime = KACOrgKotlinacademy.parseDateTime(p["dateTime"] as! String)
                return KACPuzzler(
                    id: p["id"] as! Int32,
                    data: KACPuzzlerData(
                        title: d["title"] as! String,
                        level: d["level"] as? String? ?? nil,
                        actualQuestion: d["actualQuestion"] as! String,
                        codeQuestion: d["codeQuestion"] as! String,
                        question: d["question"] as! String,
                        answers: d["answers"] as! String,
                        correctAnswer: d["correctAnswer"] as? String ?? "",
                        explanation: d["explanation"] as! String,
                        author: d["author"] as? String? ?? nil,
                        authorUrl: d["authorUrl"] as? String? ?? nil
                    ),
                    dateTime: dateTime,
                    accepted: true
                )
            })
            let data = KACNewsData(articles: articles, infos: infos, puzzlers: puzzlers)
            callback.resume(value: data)
        }) { (task, erro) in
            callback.resumeWithException(exception: KACStdlibThrowable(message: erro.localizedDescription))
        }
    }
}
