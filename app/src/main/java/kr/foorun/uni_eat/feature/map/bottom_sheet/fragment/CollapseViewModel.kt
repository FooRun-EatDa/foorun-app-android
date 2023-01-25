package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment

import kr.foorun.uni_eat.base.mvvm.BaseViewModel

class CollapseViewModel : BaseViewModel(){

    fun arrowClicked() = viewEvent(ARROW_CLICKED)

    companion object{
        const val ARROW_CLICKED = 0
    }
}