package kr.foorun.uni_eat.base.view.kakao_map

import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.MapView

class KakaoMapView(
    activity : AppCompatActivity
) : MapView(activity) {

    init {
        setCache(true)
    }

    // 지도화면에 추가된 POI Item 들 중에서 tag값이 일치하는 POI Item을 찾는다.
    fun finPOIListByTag(tag : Int){}
    // 지도화면에 추가된 POI Item 들 중에 포함되는 이름의 POI들을 찾는다
    fun findPOIList(shopName : String){}

    fun setCache(boolean: Boolean)
    = setMapTilePersistentCacheEnabled(boolean)
}