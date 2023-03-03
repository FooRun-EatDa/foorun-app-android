package kr.foorun.uni_eat.feature.map.shop_detail.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.presentation.databinding.ItemDetailImageBinding

class ItemDetailImageViewHolder(val binding: ItemDetailImageBinding) : RecyclerView.ViewHolder(binding.root)

class ShopImageAdapter : ListAdapter<String, ItemDetailImageViewHolder>(object : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailImageViewHolder {
        val binding = ItemDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemDetailImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemDetailImageViewHolder, position: Int) {
        holder.run {
            binding.image = getItem(position)
        }
    }
}