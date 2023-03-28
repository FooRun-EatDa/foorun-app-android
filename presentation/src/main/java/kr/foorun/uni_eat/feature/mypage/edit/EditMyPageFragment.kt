package kr.foorun.uni_eat.feature.mypage.edit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.test.core.app.launchActivity
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEditMyPageBinding
import kr.foorun.uni_eat.base.view.base.base_layout.BaseTextView
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.SearchTagViewModel

class EditMyPageFragment: BaseFragment<FragmentEditMyPageBinding, EditMyPageViewModel>(FragmentEditMyPageBinding::inflate){

    override val fragmentViewModel: EditMyPageViewModel by viewModels()
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

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            initResLauncher()

            tags.observe(this@EditMyPageFragment){
                tagAdapter.submitList(it)
                tagAdapter.notifyDataSetChanged()
            }

            image.observe(this@EditMyPageFragment){ image ->
                image?.let { setImage(it) }
            }

            nickCheck.observe(this@EditMyPageFragment){
                textHandle(it, binding.nickAlert)
            }

            repeatOnStarted { eventFlow.collect{ handleEvent(it) }}
            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } }
        }
    }

    private fun handleEvent(event: EditMyPageViewModel.EditEvent) {
        when (event) {
            is EditMyPageViewModel.EditEvent.DoneClicked -> fragmentViewModel.postUser(
                onSuccess = { popUpBackStack() },
                onFailure = { toast("failed") }
            )
            is EditMyPageViewModel.EditEvent.ImageClicked -> selectPictureLauncher.launch(startGallery())
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
            is SearchTagViewModel.TagEvent.TagClick -> {
                val tag = event.searchTag
                val index = event.idx
                tag.isPicked = !tag.isPicked
                tagAdapter.notifyItemChanged(index)
            }
        }
    }

    private fun textHandle(case: EditMyPageViewModel.WrongCase, view: BaseTextView) = when(case) {
        is EditMyPageViewModel.WrongCase.OutOfSize ->
            setTextColor(view,ContextCompat.getColor(requireContext(), R.color.red),"5글자 이내로 작성해주세요.")
        is EditMyPageViewModel.WrongCase.Success ->
            setTextColor(view,ContextCompat.getColor(requireContext(), R.color.black), "사용 가능한 닉네임입니다.")
        is EditMyPageViewModel.WrongCase.WrongFormat ->
            setTextColor(view,ContextCompat.getColor(requireContext(), R.color.red), "특수문자가 포함되어 있습니다.")
        is EditMyPageViewModel.WrongCase.Nothing ->
            setTextColor(view,ContextCompat.getColor(requireContext(), R.color.black), "")
    }

    private fun setTextColor(view: BaseTextView, color: Int, text: String){
        view.setTextColor(color)
        view.text = text
    }

}