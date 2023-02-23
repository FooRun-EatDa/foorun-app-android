package kr.foorun.const

import android.util.Log
import kotlinx.coroutines.*

/**
 * created jaden 22 12 06
 */

class CoroutineUtil {
    companion object{
        suspend fun awaitFuncs(vararg blocks: suspend () -> Unit) = coroutineScope {
            blocks.forEach { async {it()}.await() }
        }

        inline fun coroutineMain(
            crossinline body: suspend CoroutineScope.() -> Unit
        ) = CoroutineScope(Dispatchers.Main).launch {
            runCatching {
                body(this)
            }.onFailure { Log.e("coroutineMain","${it.message}") }
        }

        inline fun coroutineIO(
            crossinline body: suspend CoroutineScope.() -> Unit
        ) = CoroutineScope(Dispatchers.IO).launch {
                body(this)
        }

        inline fun coroutineDefault(
            crossinline body: suspend CoroutineScope.() -> Unit
        ) = CoroutineScope(Dispatchers.Default).launch {
            runCatching {
                body(this)
            }.onFailure { Log.e("coroutineDefault","${it.message}") }
        }

    }
}