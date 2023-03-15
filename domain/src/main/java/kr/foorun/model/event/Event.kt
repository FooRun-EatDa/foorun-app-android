package kr.foorun.model.event

import java.io.Serializable

data class Event(
    val name : String,
    val image : String,
    val startDate : String,
    val endDate : String,
    val content: String = ""
) : Serializable
