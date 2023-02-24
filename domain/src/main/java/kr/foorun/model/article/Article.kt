package kr.foorun.model.article

import java.io.Serializable

data class Article( //todo should be changed into interface, data class must be in data.model, this is for test
    val shopName : String,
    val title : String,
    val tags : String,
    val shopImages : List<String>,
): Serializable
