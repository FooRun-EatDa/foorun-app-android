package kr.foorun.model.shop

import kr.foorun.model.article.Article
import java.io.Serializable

data class ShopDetail(
    val mainImages : List<String>?,
    val name : String,
    val articles : List<Article>?,
) : Serializable
