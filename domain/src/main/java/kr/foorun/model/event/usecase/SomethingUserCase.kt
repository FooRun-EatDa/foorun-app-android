package kr.foorun.model.event.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SomethingUserCase(
    //add Repository
) {
    operator fun invoke(
        param: String,
        scope: CoroutineScope,
        onResult: (
            //return object
                ) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
//                repository.get(param)
            }
//            onResult(deferred.await()) return
        }
    }
}