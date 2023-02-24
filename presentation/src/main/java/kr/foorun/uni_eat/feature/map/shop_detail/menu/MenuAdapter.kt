package kr.foorun.uni_eat.feature.map.shop_detail.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.presentation.databinding.ItemMenuBinding

class ItemMenuViewHolder(val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root)

class MenuAdapter (
) : ListAdapter<Pair<String,String>, ItemMenuViewHolder>(object : DiffUtil.ItemCallback<Pair<String,String>>(){
    override fun areItemsTheSame(oldItem: Pair<String,String>, newItem: Pair<String,String>): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Pair<String,String>, newItem: Pair<String,String>): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMenuViewHolder, position: Int) {
        holder.run {
            val menu = getItem(position).first
            val price = getItem(position).second
            binding.menu = menu
            binding.price = price
        }
    }
}