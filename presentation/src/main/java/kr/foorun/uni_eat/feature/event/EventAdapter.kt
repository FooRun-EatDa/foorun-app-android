package kr.foorun.uni_eat.feature.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.const.Constant.Companion.EVENT_ITEM_MARGIN_TOP_INDEX
import kr.foorun.model.event.Event
import kr.foorun.presentation.databinding.ItemEventRightBinding
import kr.foorun.presentation.databinding.ItemEventLeftBinding

class EventLeftViewHolder(val binding: ItemEventLeftBinding) : RecyclerView.ViewHolder(binding.root)
class EventRightViewHolder(val binding: ItemEventRightBinding) : RecyclerView.ViewHolder(binding.root)

class EventAdapter(
    private val eventViewModel: EventViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var eventList: List<Event>

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            EVENT_ITEM_MARGIN_TOP_INDEX -> {
                val binding =
                    ItemEventRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EventRightViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemEventLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EventLeftViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            EVENT_ITEM_MARGIN_TOP_INDEX -> {
                holder as EventRightViewHolder
                holder.run {
                    binding.apply {
                        event = eventList.get(position)
                        viewModel = eventViewModel
                        index = position
                    }
                }
            }
            else -> {
                holder as EventLeftViewHolder
                holder.run {
                    binding.apply {
                        event = eventList.get(position)
                        viewModel = eventViewModel
                        index = position
                    }
                }
            }
        }
    }
}