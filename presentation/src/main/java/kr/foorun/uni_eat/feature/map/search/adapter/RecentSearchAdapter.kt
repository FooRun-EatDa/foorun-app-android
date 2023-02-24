package kr.foorun.uni_eat.feature.map.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.presentation.databinding.ItemRecentWrodsBinding

class ItemRecentSearchViewHolder(val binding: ItemRecentWrodsBinding) : RecyclerView.ViewHolder(binding.root)

class RecentSearchAdapter (
    private val adapterViewModel: RecentSearchViewModel
) : ListAdapter<String, ItemRecentSearchViewHolder>(object : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecentSearchViewHolder {
        val binding = ItemRecentWrodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemRecentSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemRecentSearchViewHolder, position: Int) {
        holder.run {
            binding.word = getItem(position)
            binding.viewModel = adapterViewModel
        }
    }
}