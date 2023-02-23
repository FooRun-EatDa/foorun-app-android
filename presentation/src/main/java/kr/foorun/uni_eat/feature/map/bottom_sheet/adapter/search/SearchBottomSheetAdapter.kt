package kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.article.Article
import kr.foorun.presentation.databinding.ItemSearchArticlesBinding

class ItemSearchViewHolder(val binding: ItemSearchArticlesBinding) : RecyclerView.ViewHolder(binding.root)

class SearchBottomSheetAdapter (
    private val adapterViewModel: SearchBottomSheetItemViewModel
) : ListAdapter<Article, ItemSearchViewHolder>(object : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.shopImages == newItem.shopImages
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchViewHolder {
        val binding = ItemSearchArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemSearchViewHolder, position: Int) {
        holder.run {
        }
    }
}