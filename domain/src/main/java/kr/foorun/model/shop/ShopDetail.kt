package kr.foorun.model.shop

import kr.foorun.model.article.Article
import java.io.Serializable

data class ShopDetail( //todo should be changed into interface, data class must be in data.model, this is for test
    val mainImages : List<String>?,
    val name : String,
    val articles : List<Article>?,
) : Serializable
