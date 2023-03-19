package kr.foorun.uni_eat.feature.map.search.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentMapSearchBinding
import kr.foorun.uni_eat.base.viewmodel.nonEmptyObserver
import kr.foorun.uni_eat.base.viewmodel.nonNullObserver
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.search.adapter.RecentSearchAdapter
import kr.foorun.uni_eat.feature.map.search.adapter.RecentSearchViewModel

@AndroidEntryPoint
class MapSearchFragment(val searchDone : (word : String) -> Unit): DialogFragment() {
    private lateinit var binding: FragmentMapSearchBinding
    private val searchViewModel: MapSearchViewModel by viewModels()
    private val recentSearchViewModel: RecentSearchViewModel by viewModels()
    private val recentSearchAdapter by lazy { RecentSearchAdapter(recentSearchViewModel.apply {
        repeatOnStarted { recentSearchViewModel.eventFlow.collect{ handleAdapterEvent(it) } } }) }

    override fun getTheme(): Int = R.style.FullDialogFragment

    @SuppressLint("UseGetLayoutInflater", "NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_map_search, container, false)
        isCancelable = false

        setRecycler()

        binding.viewModel = searchViewModel.apply {
            searchWord.nonEmptyObserver(this@MapSearchFragment){ //when search done
                //todo if there is only one result: pass word back ( _isNonSearch(false) )
                //todo if there are results: show list of shops ( _isNonSearch(false) )
                //todo if there isn't any result: show no result ( _isNonSearch(true) )
                searchDone(it)
                dismiss()
            }

            recentWords.observe(this@MapSearchFragment){
                recentSearchAdapter.submitList(ArrayList(it))
                recentSearchAdapter.notifyDataSetChanged()
            }

            repeatOnStarted { viewEvent.collect{ dismiss() } }
        }

        return binding.root
    }

    private fun setRecycler() {
        binding.recentWordsRecycler.adapter = recentSearchAdapter
    }

    private fun handleAdapterEvent(event: RecentSearchViewModel.RecentSearchEvent) = when(event) {
        is RecentSearchViewModel.RecentSearchEvent.Remove -> searchViewModel.removeRecentWord(event.word)
        is RecentSearchViewModel.RecentSearchEvent.Clicked -> searchViewModel.setSearchWord(event.word)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            @Deprecated("Deprecated in Java", ReplaceWith("dismiss()"))
            override fun onBackPressed() { dismiss() }
        }
    }

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return if (!this.isAdded) super.show(transaction, tag)
        else 0
    }
}