package kr.foorun.uni_eat.feature.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.model.event.EventCoupon
import kr.foorun.presentation.databinding.ItemEventRightBinding
import kr.foorun.presentation.databinding.ItemEventLeftBinding
import kr.foorun.uni_eat.feature.event.EventViewHolder.Companion.LEFT

sealed class EventViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root){

    abstract fun bind(item: EventCoupon)

    class EventLeftViewHolder(
        private val binding: ItemEventLeftBinding,
        private val adapterViewModel: EventAdapterViewModel
    ): EventViewHolder(binding){
        override fun bind(item: EventCoupon) = binding.run {
            viewModel = adapterViewModel
            eventCoupon = item
        }
    }

    class EventRightViewHolder(
        private val binding: ItemEventRightBinding,
        private val adapterViewModel: EventAdapterViewModel
    ): EventViewHolder(binding) {
        override fun bind(item: EventCoupon) = binding.run {
            viewModel = adapterViewModel
            eventCoupon = item
        }
    }

    companion object{
        const val LEFT = 1
        const val RIGHT = 0
    }
}

class EventAdapter(
    private val eventAdapterViewModel: EventAdapterViewModel
) : ListAdapter<EventCoupon, EventViewHolder>(object : DiffUtil.ItemCallback<EventCoupon>(){
    override fun areItemsTheSame(oldItem: EventCoupon, newItem: EventCoupon) = oldItem == newItem
    override fun areContentsTheSame(oldItem: EventCoupon, newItem: EventCoupon) = oldItem.image == newItem.image
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return when (viewType) {
            LEFT -> ItemEventRightBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
                    EventViewHolder.EventRightViewHolder(it,eventAdapterViewModel)
            }
            else -> ItemEventLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false).let {
                    EventViewHolder.EventLeftViewHolder(it,eventAdapterViewModel)
            }
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) = holder.bind(getItem(position))
    override fun getItemViewType(position: Int) = position
}