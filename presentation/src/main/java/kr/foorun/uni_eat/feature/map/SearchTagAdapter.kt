package kr.foorun.uni_eat.feature.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.tag.SearchTag
import kr.foorun.presentation.databinding.ItemIntroduceTagBinding
import kr.foorun.presentation.databinding.ItemSearchTagBinding
import kr.foorun.uni_eat.feature.map.TagViewHolder.Companion.SEARCH

sealed class TagViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: SearchTag? = null, index: Int?)

    class ItemSearchTagViewHolder(
        val binding: ItemSearchTagBinding,
        val viewModel: SearchTagViewModel?
    ) : TagViewHolder(binding) {
        override fun bind(item: SearchTag?, index: Int?) {
            viewModel?.let { binding.viewModel = it }
            item?.let { binding.searchTag = item }
            index?.let { binding.index = it }
        }
    }

    class ItemIntroduceTagViewHolder(
        val binding: ItemIntroduceTagBinding,
        val viewModel: SearchTagViewModel?
    ) : TagViewHolder(binding) {
        override fun bind(item: SearchTag?,index: Int?) {
            viewModel?.let { binding.viewModel = it }
            item?.let { binding.searchTag = item }
            index?.let { binding.index = it }
        }
    }

    companion object{
        const val SEARCH = 0
        const val INTRODUCE = 1
    }
}

class SearchTagAdapter (
    private val adapterViewModel: SearchTagViewModel? = null,
    private val type: Int = SEARCH
) : ListAdapter<SearchTag, TagViewHolder>(object : DiffUtil.ItemCallback<SearchTag>(){
    override fun areItemsTheSame(oldItem: SearchTag, newItem: SearchTag) = oldItem.tagName == newItem.tagName && oldItem.isPicked == newItem.isPicked
    override fun areContentsTheSame(oldItem: SearchTag, newItem: SearchTag) = oldItem.tagName == newItem.tagName && oldItem.isPicked == newItem.isPicked
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return when(type){
            SEARCH -> {
                val binding = ItemSearchTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TagViewHolder.ItemSearchTagViewHolder(binding,adapterViewModel)
            }

            else -> {
                val binding = ItemIntroduceTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TagViewHolder.ItemIntroduceTagViewHolder(binding,adapterViewModel)
            }
        }
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.run { bind(getItem(position), position) }
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.run { bind(getItem(position), position) }
    }

    fun tagClicked(position: Int, atLeastOne: Boolean = false, single: Boolean = false){
        currentList.forEachIndexed { idx, searchTag ->
            if(idx != position && searchTag.isPicked && single){
                searchTag.isPicked = false
                notifyItemChanged(idx,PAYLOAD)
            }

            if(idx == position){
                if(atLeastOne) searchTag.isPicked = true
                else searchTag.isPicked = !searchTag.isPicked
                notifyItemChanged(position,PAYLOAD)
            }
        }
    }

    companion object{
        const val PAYLOAD = "payload"
    }

}