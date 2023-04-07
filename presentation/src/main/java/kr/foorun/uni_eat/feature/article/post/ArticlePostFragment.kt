package kr.foorun.uni_eat.feature.article.post

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.const.Constant.Companion.INDICATOR_COUNT
import kr.foorun.const.Constant.Companion.SPAN_COUNT
import kr.foorun.model.tag.SearchTag
import kr.foorun.presentation.databinding.FragmentArticlePostBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator.Companion.GRID
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.article.adapter.ArticleAdapter
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.SearchTagViewModel
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager.ShopImageAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager.ShopImageViewModel

@AndroidEntryPoint
class ArticlePostFragment: BaseFragment<FragmentArticlePostBinding, ArticlePostViewModel>(FragmentArticlePostBinding::inflate){

    override val fragmentViewModel: ArticlePostViewModel by viewModels()
    private val searchTagViewModel: SearchTagViewModel by viewModels()
    private val shopImageViewModel: ShopImageViewModel by viewModels()
    private val searchTagAdapter by lazy { SearchTagAdapter(searchTagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tagHandleEvent(it)} } }) }
    private val articleImageAdapter by lazy { ShopImageAdapter(shopImageViewModel.apply {
        repeatOnStarted { eventFlow.collect{ handleShopImageEvent(it) } } }) }

    private lateinit var selectPictureLauncher : ActivityResultLauncher<Intent>

    private fun initResLauncher() {
        selectPictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val container = result.data
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    if (container?.clipData != null) {
                        val count = container.clipData!!.itemCount
                        val imageList = mutableListOf<String>()
                        fragmentViewModel.articleImageList.value?.let { imageList.addAll(it) }
                        for (i in 0 until count) imageList.add(container.clipData!!.getItemAt(i).uri.toString())
                        fragmentViewModel.setArticleImage(imageList)

                    } else container?.data?.let { fragmentViewModel.setArticleImage(mutableListOf(it.toString())) }
                }
            }
    }

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            initResLauncher()

            searchTags.observe(this@ArticlePostFragment){
                searchTagAdapter.submitList(it.toList())
            }

            articleImageList.observe(this@ArticlePostFragment){
                it?.let {
                    if (it.size > INDICATOR_COUNT) addImagePager.setIndicator(INDICATOR_COUNT)
                    else addImagePager.setIndicator(it.size)
                    articleImageAdapter.submitList(it.toList())
                }
            }

            repeatOnStarted { eventFlow.collect{ handlePostEvent(it) } }
            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        tagRecycler.run{
            adapter = searchTagAdapter
            addItemDecoration(TagDecorator(spanCount = 4, oriental = GRID))
        }

        addImagePager.run {
            setPager(articleImageAdapter)
        }
    }

    private fun handlePostEvent(event: ArticlePostViewModel.PostEvent) = when(event){
        is ArticlePostViewModel.PostEvent.ImageClicked -> imagePermission(
            onGranted = { selectPictureLauncher.launch(startGallery(true)) },
            onDenied = {}
        )
        is ArticlePostViewModel.PostEvent.DoneClicked -> { toast("done") }
    }

    private fun handleShopImageEvent(event: ShopImageViewModel.ShopImageEvent) = when(event) {
        is ShopImageViewModel.ShopImageEvent.ImageClicked -> fragmentViewModel.imageClicked()
    }

    private fun tagHandleEvent(event: SearchTagViewModel.TagEvent) {
        when (event) {
            is SearchTagViewModel.TagEvent.TagClick -> searchTagAdapter.tagClicked(event.idx)
        }
    }

}