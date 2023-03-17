package kr.foorun.uni_eat.feature.article.post

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.model.tag.SearchTag
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentArticlePostBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator.Companion.GRID
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.SearchTagViewModel

@AndroidEntryPoint
class ArticlePostFragment: BaseFragment<FragmentArticlePostBinding, ArticlePostViewModel>(FragmentArticlePostBinding::inflate){

    override val fragmentViewModel: ArticlePostViewModel by viewModels()
    private val searchTagViewModel: SearchTagViewModel by viewModels()
    private val searchTagAdapter by lazy { SearchTagAdapter(searchTagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tagHandleEvent(it)} } }) }

    private lateinit var selectPictureLauncher : ActivityResultLauncher<Intent>

    private fun initResLauncher() {
        selectPictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val container = result.data

                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    if (container?.clipData != null) {
                        val count = container.clipData!!.itemCount

                        for (i in 0 until count) {
                            val imageUri = container.clipData!!.getItemAt(i).uri
                        }
                    } else container?.data?.let { fragmentViewModel.setArticleImage(it.toString()) }
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            initResLauncher()

            searchTags.observe(this@ArticlePostFragment){
                searchTagAdapter.submitList(it)
                searchTagAdapter.notifyDataSetChanged()
            }

            repeatOnStarted { eventFlow.collect{ handlePostEvent(it) } }
            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } }
        }
    }

    private fun handlePostEvent(event: ArticlePostViewModel.PostEvent) = when(event){
        is ArticlePostViewModel.PostEvent.ImageClicked -> imagePermission(
            onGranted = { selectPictureLauncher.launch(startGallery(true)) },
            onDenied = {}
        )
        is ArticlePostViewModel.PostEvent.DoneClicked -> {}
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        tagRecycler.run{
            adapter = searchTagAdapter
            addItemDecoration(TagDecorator(spanCount = 4, oriental = GRID))
        }
    }

    private fun tagHandleEvent(event: SearchTagViewModel.TagEvent) = when (event) {
        is SearchTagViewModel.TagEvent.TagClick -> {
            val tag = event.searchTag
            val arr = ArrayList<SearchTag>()
            fragmentViewModel.searchTags.value?.map { arr.add(it) }
            for( i in arr.indices ) if(arr[i].tagName == tag.tagName) arr[i] = SearchTag(tag.tagName,!tag.isPicked)
            fragmentViewModel.setTags(arr)
        }
    }

}