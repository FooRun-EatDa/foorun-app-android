package kr.foorun.uni_eat.feature.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.data.event.Event
import kr.foorun.presentation.databinding.ItemEventBinding

class EventViewHolder(val binding : ItemEventBinding) : RecyclerView.ViewHolder(binding.root)

class EventAdapter(
    private val eventViewModel: EventViewModel
) : RecyclerView.Adapter<EventViewHolder>() {

    lateinit var eventList : List<Event>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.run{
            binding.event = eventList.get(position) ?: null
            binding.viewModel = eventViewModel
        }
    }
}