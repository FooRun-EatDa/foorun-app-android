package kr.foorun.model.tag

data class SearchTag( //todo should be changed into interface, data class must be in data.model, this is for test
    val tagName: String,
    var isPicked: Boolean = false,
)
