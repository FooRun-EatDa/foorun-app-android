package kr.foorun.model.event

import java.io.Serializable

data class EventCoupon(
    val name : String = "노랑통닭 1000원 할인 쿠폰",
    val shopName : String = "노랑통닭 수원점",
    val Discription : String = "",
    val Notice : String = "",
    val image : String,
    val startDate : String = "12.12.12",
    val endDate : String = "12.12.12",
    val content: String = ""
) : Serializable
