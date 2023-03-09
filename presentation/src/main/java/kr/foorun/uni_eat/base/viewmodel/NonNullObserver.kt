package kr.foorun.uni_eat.base.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun LiveData<String?>.nonNullObserver(owner: LifecycleOwner, action: (value: String) -> Unit)
{ this.observe(owner){ if(!it.isNullOrEmpty()) action(it) } }

fun LiveData<String>.nonEmptyObserver(owner: LifecycleOwner, action: (value: String) -> Unit)
{ this.observe(owner){ if(!it.isNullOrEmpty()) action(it) } }