package kr.foorun.uni_eat.feature.mypage.edit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentMyPageEditBinding
import kr.foorun.uni_eat.base.view.base.base_layout.BaseTextView
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.SearchTagViewModel

class MyPageEditFragment: BaseFragment<FragmentMyPageEditBinding, MyPageEditViewModel>(FragmentMyPageEditBinding::inflate){

    override val fragmentViewModel: MyPageEditViewModel by viewModels()
    private val tagViewModel: SearchTagViewModel by viewModels()
    private val tagAdapter by lazy { SearchTagAdapter(tagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tagHandleEvent(it)} } }) }
    private lateinit var selectPictureLauncher : ActivityResultLauncher<Intent>

    private fun initResLauncher() {
        selectPictureLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val container = result.data
                if (result.resultCode == AppCompatActivity.RESULT_OK)
                  container?.data?.let { fragmentViewModel.setImage(it.toString()) }
            }
    }

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            initResLauncher()

            tags.observe(this@MyPageEditFragment){
                tagAdapter.submitList(it)
            }

            image.observe(this@MyPageEditFragment){ image ->
                image?.let { setImage(it) }
            }

            nickStringCheck.observe(this@MyPageEditFragment){
                nickTextHandle(it, binding.nickAlert)
            }

            user.observe(this@MyPageEditFragment){
                binding.user = it
            }

            repeatOnStarted { eventFlow.collect{ handleEvent(it) }}
            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } }
        }
    }

    private fun handleEvent(event: MyPageEditViewModel.EditEvent) {
        when (event) {
            is MyPageEditViewModel.EditEvent.DoneClicked -> fragmentViewModel.postUser(
                onSuccess = { popUpBackStack() },
                onFailure = { toast("failed") }
            )
            is MyPageEditViewModel.EditEvent.ImageClicked -> selectPictureLauncher.launch(startGallery())
            is MyPageEditViewModel.EditEvent.DuplicateCheckClicked -> fragmentViewModel.duplicateCheck(true)
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        tagRecycler.run {
            adapter = tagAdapter
            addItemDecoration(TagDecorator(spanCount = 4, oriental = TagDecorator.GRID))
        }
    }

    private fun tagHandleEvent(event: SearchTagViewModel.TagEvent) {
        when (event) {
            is SearchTagViewModel.TagEvent.TagClick -> tagAdapter.tagClicked(event.idx)
        }
    }

    private fun nickTextHandle(case: MyPageEditViewModel.NickWrongCase, view: BaseTextView) = when(case) {
        is MyPageEditViewModel.NickWrongCase.OutOfSize -> view.setTextColor(getString(R.string.char_limit_5), R.color.red)
        is MyPageEditViewModel.NickWrongCase.Success -> view.setTextColor("", R.color.black)
        is MyPageEditViewModel.NickWrongCase.WrongFormat -> view.setTextColor(getString(R.string.included_sp), R.color.red)
        is MyPageEditViewModel.NickWrongCase.Nothing -> view.setTextColor("", R.color.black)
        is MyPageEditViewModel.NickWrongCase.Duplicated -> view.setTextColor(getString(R.string.duplicated_nick), R.color.red)
    }
}