package kr.foorun.uni_eat.feature.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.data.event.Event
import kr.foorun.presentation.databinding.ItemEvent2Binding
import kr.foorun.presentation.databinding.ItemEventBinding

class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)
class EventViewHolder2(val binding: ItemEvent2Binding) : RecyclerView.ViewHolder(binding.root)

class EventAdapter(
    private val eventViewModel: EventViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var eventList: List<Event>

    override fun getItemViewType(position: Int): Int {
        return eventList[position].index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val binding =
                    ItemEvent2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EventViewHolder2(binding)
            }
            else -> {
                val binding =
                    ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return EventViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (eventList[position].index) {
            1 -> {
                holder as EventViewHolder2
                holder.run {
                    binding.apply {
                        event = eventList.get(position) ?: null
                        viewModel = eventViewModel
                        eventLine.bringToFront()
                    }
                }
            }
            else -> {
                holder as EventViewHolder
                holder.run {
                    binding.apply {
                        event = eventList.get(position) ?: null
                        viewModel = eventViewModel
                        eventLine.bringToFront()
                    }
                }
            }
        }
    }
}