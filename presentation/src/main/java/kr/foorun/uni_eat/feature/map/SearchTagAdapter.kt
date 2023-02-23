package kr.foorun.uni_eat.feature.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.tag.SearchTag
import kr.foorun.presentation.databinding.ItemSearchTagBinding

class ItemSearchTagViewHolder(val binding: ItemSearchTagBinding) : RecyclerView.ViewHolder(binding.root)

class SearchTagAdapter (
    private val adapterViewModel: SearchTagViewModel? = null
) : ListAdapter<SearchTag, ItemSearchTagViewHolder>(object : DiffUtil.ItemCallback<SearchTag>(){
    override fun areItemsTheSame(oldItem: SearchTag, newItem: SearchTag): Boolean {
        return oldItem.tagName == newItem.tagName
    }

    override fun areContentsTheSame(oldItem: SearchTag, newItem: SearchTag): Boolean {
        return oldItem.tagName == newItem.tagName && oldItem.isPicked == newItem.isPicked
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchTagViewHolder {
        val binding = ItemSearchTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemSearchTagViewHolder, position: Int) {
        holder.run {
            val item = getItem(position)
            binding.searchTag = item
            binding.viewModel = adapterViewModel
        }
    }
}