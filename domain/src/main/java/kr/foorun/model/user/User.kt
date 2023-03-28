package kr.foorun.model.user

import kr.foorun.model.article.Article
import kr.foorun.model.tag.SearchTag
import java.io.Serializable

data class User(
    var name: String = "",
    var email: String = "",
    var tags: List<SearchTag> = listOf(SearchTag("분위기 있는"), SearchTag("감성카페")),
    var articles: List<Article> = listOf(),
    var bookMarks: List<Article> = listOf(),
    var introduce: String = "분위기 있는 감성 카페를 소개합니다.",
    var image: String = "https://picsum.photos/200"
): Serializable
