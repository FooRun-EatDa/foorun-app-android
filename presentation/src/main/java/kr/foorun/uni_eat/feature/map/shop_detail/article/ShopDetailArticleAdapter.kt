package kr.foorun.uni_eat.feature.map.shop_detail.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.article.Article
import kr.foorun.presentation.databinding.ItemArticleThumbnailBinding

class ItemArticleThumbnailViewHolder(val binding: ItemArticleThumbnailBinding) : RecyclerView.ViewHolder(binding.root)

class ShopDetailArticleAdapter (
) : ListAdapter<Article, ItemArticleThumbnailViewHolder>(object : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticleThumbnailViewHolder {
        val binding = ItemArticleThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemArticleThumbnailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemArticleThumbnailViewHolder, position: Int) {
        holder.run {
            binding.article = getItem(position)
        }
    }
}