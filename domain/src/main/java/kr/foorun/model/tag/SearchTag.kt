package kr.foorun.model.tag

data class SearchTag( //todo should be changed into interface, data class must be in data.model, this is for test
    val tagName: String,
    var isPicked: Boolean = false,
)

fun List<SearchTag>.getSelectedTags(): ArrayList<SearchTag> {
    val list = ArrayList<SearchTag>()
    this.map { if(it.isPicked) list.add(it) }
    return list
}
