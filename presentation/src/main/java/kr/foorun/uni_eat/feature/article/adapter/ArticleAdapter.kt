package kr.foorun.uni_eat.feature.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.const.Constant.Companion.ITEM_HEIGHT
import kr.foorun.model.article.Article
import kr.foorun.presentation.databinding.ItemArticleThumbnailLargeBinding
import kr.foorun.presentation.databinding.ItemMoreArticleBinding
import kr.foorun.uni_eat.base.view.base.dp
import kr.foorun.uni_eat.feature.article.ArticleViewModel
import kr.foorun.uni_eat.feature.article.adapter.ArticleViewHolder.Companion.ARTICLE
import kr.foorun.uni_eat.feature.article.adapter.ArticleViewHolder.Companion.MORE

sealed class ArticleViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root){

    abstract fun bind(item: Article)

    class ItemArticleViewHolder(
        val binding: ItemArticleThumbnailLargeBinding,
        val viewModel: ArticleAdapterViewModel
    ): ArticleViewHolder(binding){
        override fun bind(item: Article) {
            binding.viewModel = viewModel
            binding.article = item
        }
    }

    class ItemMoreArticleHolder(
        val binding: ItemMoreArticleBinding,
        val viewModel: ArticleAdapterViewModel
    ): ArticleViewHolder(binding) {
        override fun bind(item: Article) { binding.viewModel = viewModel }
    }

    companion object{
        const val MORE = 0
        const val ARTICLE = 1
    }
}

class ArticleAdapter (
    private val itemHeight: Int = ITEM_HEIGHT,
    private val isPager: Boolean = false,
    val viewModel: ArticleAdapterViewModel
) : ListAdapter<Article, ArticleViewHolder>(object : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return when(viewType){
            MORE -> {
                val binding = ItemMoreArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArticleViewHolder.ItemMoreArticleHolder(binding,viewModel)
            }
            else -> {
                val binding = ItemArticleThumbnailLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArticleViewHolder.ItemArticleViewHolder(binding,viewModel)
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if (!isPager) {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.height = itemHeight.dp //height item
            holder.itemView.layoutParams = params
        }
        holder.bind(getItem(position))
    }

    /* CommonViewType에서 해당 data의 viewType의 ordinal(인덱스)를 반환  */
    override fun getItemViewType(position: Int): Int {
        return if(itemCount-1 == position) MORE
        else ARTICLE
    }
}