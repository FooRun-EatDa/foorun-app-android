package kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.presentation.databinding.ItemDetailImageBinding
import kr.foorun.presentation.databinding.ItemPostImageBinding

sealed class ShopImageViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun binding(item: String)

    class ItemDetailImageViewHolder(
        val binding: ItemDetailImageBinding,
        private val adapterViewModel: ShopImageViewModel
    ) : ShopImageViewHolder(binding) {
        override fun binding(item: String) = binding.run {
            image = item
            viewModel = adapterViewModel
        }
    }

    class ItemPostImageViewHolder(
        val binding: ItemPostImageBinding,
        private val adapterViewModel: ShopImageViewModel
    ) : ShopImageViewHolder(binding) {
        override fun binding(item: String) = binding.run {
            image = item
            viewModel = adapterViewModel
        }
    }

    sealed class ViewType{
        data class Post(val unit: Unit? = null): ViewType()
        data class Detail(val unit: Unit? = null): ViewType()
    }
}

class ShopImageAdapter(
    private val shopImageViewModel: ShopImageViewModel,
    private val type: ShopImageViewHolder.ViewType = ShopImageViewHolder.ViewType.Post()
) : ListAdapter<String, ShopImageViewHolder>(object : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopImageViewHolder = when(type){
        is ShopImageViewHolder.ViewType.Post -> {
            val binding = ItemPostImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ShopImageViewHolder.ItemPostImageViewHolder(binding,shopImageViewModel)
        }
        is ShopImageViewHolder.ViewType.Detail -> {
            val binding = ItemDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ShopImageViewHolder.ItemDetailImageViewHolder(binding,shopImageViewModel)
        }
    }

    override fun onBindViewHolder(holder: ShopImageViewHolder, position: Int) = holder.run {
        binding(getItem(position))
    }
}