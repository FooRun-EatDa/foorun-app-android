package kr.foorun.data.shop

import com.google.gson.annotations.SerializedName
import kr.foorun.data.article.Article
import java.io.Serializable

data class ShopDetail(
    val mainImages : List<String>?,
    val name : String,
    val articles : List<Article>?,
) : Serializable
