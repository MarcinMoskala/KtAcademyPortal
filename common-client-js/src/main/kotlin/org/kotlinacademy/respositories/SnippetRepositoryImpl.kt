package org.kotlinacademy.respositories

import org.kotlinacademy.Endpoints
import org.kotlinacademy.Endpoints.info
import org.kotlinacademy.Endpoints.propose
import org.kotlinacademy.Endpoints.snippet
import org.kotlinacademy.data.Info
import org.kotlinacademy.data.InfoData
import org.kotlinacademy.data.Snippet
import org.kotlinacademy.data.SnippetData
import org.kotlinacademy.httpPost
import org.kotlinacademy.json

class SnippetRepositoryImpl : SnippetRepository {

    override suspend fun propose(snippetData: SnippetData) {
        httpPost("$snippet/$propose", json.stringify(snippetData))
    }

    override suspend fun update(snippet: Snippet, secret: String) {
        httpPost("${Endpoints.snippet}?Secret-hash=$secret", json.stringify(snippet))
    }
}