package kr.foorun.uni_eat.feature.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
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
        return eventList[position].viewType
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
        when (eventList[position].viewType) {
            1 -> {
                holder as EventViewHolder2
                holder.run {
                    binding.event = eventList.get(position) ?: null
                    binding.viewModel = eventViewModel
                }
            }
            else -> {
                holder as EventViewHolder
                holder.run {
                    binding.event = eventList.get(position) ?: null
                    binding.viewModel = eventViewModel
                }
            }
        }
    }
}