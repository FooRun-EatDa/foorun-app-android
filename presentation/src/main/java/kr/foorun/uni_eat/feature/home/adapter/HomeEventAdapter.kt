package kr.foorun.uni_eat.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.event.Event
import kr.foorun.presentation.databinding.ItemHomeEventBinding

class ItemHomeEventViewHolder(val binding: ItemHomeEventBinding) : RecyclerView.ViewHolder(binding.root)

class HomeEventAdapter (
    private val adapterViewModel: HomeEventAdapterViewModel
) : ListAdapter<Event, ItemHomeEventViewHolder>(object : DiffUtil.ItemCallback<Event>(){
    override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem.image == newItem.image
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeEventViewHolder {
        val binding =
            ItemHomeEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHomeEventViewHolder, position: Int) {
        holder.run {
            binding.viewModel = adapterViewModel
            binding.event = getItem(position)
        }
    }
}