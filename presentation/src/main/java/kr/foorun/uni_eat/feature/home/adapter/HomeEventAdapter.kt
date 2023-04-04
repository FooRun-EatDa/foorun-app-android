package kr.foorun.uni_eat.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.event.EventCoupon
import kr.foorun.presentation.databinding.ItemHomeEventBinding

class ItemHomeEventViewHolder(val binding: ItemHomeEventBinding) : RecyclerView.ViewHolder(binding.root)

class HomeEventAdapter (
    private val adapterViewModel: HomeEventAdapterViewModel
) : ListAdapter<EventCoupon, ItemHomeEventViewHolder>(object : DiffUtil.ItemCallback<EventCoupon>(){
    override fun areItemsTheSame(oldItem: EventCoupon, newItem: EventCoupon) = oldItem == newItem
    override fun areContentsTheSame(oldItem: EventCoupon, newItem: EventCoupon) = oldItem.image == newItem.image
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHomeEventViewHolder {
        val binding =
            ItemHomeEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHomeEventViewHolder, position: Int) {
        holder.run {
            binding.viewModel = adapterViewModel
            binding.eventCoupon = getItem(position)
        }
    }
}