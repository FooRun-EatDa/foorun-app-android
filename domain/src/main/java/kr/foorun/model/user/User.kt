package kr.foorun.model.user

import kr.foorun.model.article.Article
import kr.foorun.model.tag.SearchTag
import java.io.Serializable

data class User(
    val name: String = "",
    val email: String = "",
    val tags: List<SearchTag> = listOf(SearchTag("분위기 있는"), SearchTag("감성카페")),
    val articles: List<Article> = listOf(),
    val bookMarks: List<Article> = listOf(),
    val introduce: String = "분위기 있는 감성 카페를 소개합니다.",
    val image: String = "https://picsum.photos/200"
): Serializable
