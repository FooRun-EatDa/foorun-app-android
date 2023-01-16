package kr.foorun.data.const

class Constant {

    companion object{
        const val LANGUAGE = "language"
        const val DELAY_TIME = 1500.toLong()

        const val PASS_PATTERN = "^(?=.*[A-Za-z])(?=.*[0-9]).{5,12}.\$"
        const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        const val BACK = -1
    }
}