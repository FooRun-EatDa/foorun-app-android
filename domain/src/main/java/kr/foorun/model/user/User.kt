package kr.foorun.model.user

import kr.foorun.model.article.Article
import java.io.Serializable

data class User(
    val name: String = "",
    val email: String = "",
    val tags: List<String> = listOf(),
    val articles: List<Article> = listOf(),
    val bookMarks: List<Article> = listOf(),
    val introduce: String = "",
): Serializable
