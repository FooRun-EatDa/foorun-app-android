package kr.foorun.uni_eat.base.view.base.loading

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.LayoutLoadingBinding

class LoadingFragment : DialogFragment(){
    companion object {
        private const val TIME_OUT: Long = 200
    }

    private lateinit var binding: LayoutLoadingBinding

    override fun show(transaction: FragmentTransaction, tag: String?): Int {
        return if (!this.isAdded) super.show(transaction, tag)
        else 0
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_loading, container, false)
        isCancelable = false

        lifecycleScope.launch(Main) {
            withContext(Dispatchers.Default) {
                delay(TIME_OUT)
                dismiss()
            }
        }

        return binding.root
    }

    override fun getTheme(): Int = R.style.TranslucentDialog

    override fun onDestroy() {
        super.onDestroy()
    }
}