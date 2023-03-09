package kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.article.Article
import kr.foorun.presentation.databinding.ItemShopImageBinding

class ItemShopViewHolder(val binding: ItemShopImageBinding) : RecyclerView.ViewHolder(binding.root)

class ShopBottomSheetAdapter(
    private val adapterViewModel: ArticleAdapterViewModel
) : ListAdapter<Article, ItemShopViewHolder>(object : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.shopImages == newItem.shopImages
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShopViewHolder {
        val binding = ItemShopImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemShopViewHolder, position: Int) {
        holder.run {
            binding.article = getItem(position)
            binding.viewModel = adapterViewModel
        }
    }
}