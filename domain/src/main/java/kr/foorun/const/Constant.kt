package kr.foorun.const

class Constant {

    companion object{
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