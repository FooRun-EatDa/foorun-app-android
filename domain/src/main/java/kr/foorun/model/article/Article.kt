package kr.foorun.model.article

import java.io.Serializable

data class Article(
    val shopName : String,
    val title : String,
    val tags : String,
    val shopImages : List<String>,
): Serializable
