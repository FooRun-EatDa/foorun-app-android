package kr.foorun.uni_eat.feature.map.shop_detail.article

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.const.Constant.Companion.ITEM_HEIGHT
import kr.foorun.model.article.Article
import kr.foorun.presentation.databinding.ItemArticleThumbnailBinding
import kr.foorun.uni_eat.base.view.base.dp

class ItemArticleThumbnailViewHolder(val binding: ItemArticleThumbnailBinding) : RecyclerView.ViewHolder(binding.root)

class ShopDetailArticleAdapter (
    private val itemHeight: Int = ITEM_HEIGHT,
    private val isPager: Boolean = false
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
        if (!isPager) {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.height = itemHeight.dp //height item
            holder.itemView.layoutParams = params
        }

        holder.run {
            binding.article = getItem(position)
        }
    }
}