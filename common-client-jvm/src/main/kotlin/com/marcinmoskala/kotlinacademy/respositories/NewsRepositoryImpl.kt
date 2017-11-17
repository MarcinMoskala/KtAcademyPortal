package com.marcinmoskala.kotlinacademy.respositories

import com.marcinmoskala.kotlinacademy.data.News

class NewsRepositoryImpl : NewsRepository {

    override fun getNews(callback: (List<News>) -> Unit, onError: (Throwable) -> Unit, onFinish: () -> Unit) {
        callback(listOf(
                News("Alalalalala", "Bkokokokokokokokokko", "https://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/R-Z/Wacky%20Weekend/Wild%20Cats/ww-wild-cats-tiger.adapt.945.1.jpg", "www.marcinmoskala.com"),
                News("Alalalalala", "Bkokokokokokokokokko", "https://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/R-Z/Wacky%20Weekend/Wild%20Cats/ww-wild-cats-tiger.adapt.945.1.jpg", "www.marcinmoskala.com"),
                News("Alalalalala", "Bkokokokokokokokokko", "https://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/R-Z/Wacky%20Weekend/Wild%20Cats/ww-wild-cats-tiger.adapt.945.1.jpg", "www.marcinmoskala.com"),
                News("Alalalalala", "Bkokokokokokokokokko", "https://kids.nationalgeographic.com/content/dam/kids/photos/articles/Other%20Explore%20Photos/R-Z/Wacky%20Weekend/Wild%20Cats/ww-wild-cats-tiger.adapt.945.1.jpg", "www.marcinmoskala.com")
        ))
    }
}