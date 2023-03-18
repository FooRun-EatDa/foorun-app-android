package kr.foorun.const

class Constant {

    companion object{
        const val SPAN_COUNT = 2
        const val INDICATOR_COUNT = 4

        const val TAG_ITEM_MARGIN_GAP = 11
        const val TAG_ITEM_MARGIN_BOTTOM = 16

        const val EVENT_ITEM_MARGIN_SIDE = 21
        const val EVENT_ITEM_MARGIN_GAP = 7
        const val EVENT_ITEM_MARGIN_BOTTOM = 29

        const val EVENT_SORT_LATEST = 2
        const val EVENT_SORT_DEADLINE = 3

        const val POSITION = "position"
        const val ITEM_HEIGHT = 238
        const val ITEM_HEIGHT_LARGE = 471
        const val ARTICLE_PREVIEW_MARGIN = 14
        const val SEARCH_WORD = "searchWord"
        const val LANGUAGE = "language"
        const val DELAY_TIME = 500.toLong()

        const val PASS_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9]).{5,12}.\$"
        const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    }
}