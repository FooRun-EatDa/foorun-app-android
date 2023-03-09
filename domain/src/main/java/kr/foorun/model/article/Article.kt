package kr.foorun.model.article

import java.io.Serializable

data class Article( //todo should be changed into interface, data class must be in data.model, this is for test
    val shopName: String = "아나브린",
    val title : String = "",
    val tags : String = "",
    val shopImages : List<String> = listOf("https://picsum.photos/200"),
    val area: String = "은평구 카페",
    val user: String = "JIWON",
    val nick: String = "정건",
    val content: String = "",
    val kind: String = "레스토랑"
): Serializable
