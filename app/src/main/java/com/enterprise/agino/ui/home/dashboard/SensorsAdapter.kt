package com.enterprise.agino.ui.home.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.CardSensorBinding
import com.enterprise.agino.domain.model.Sensor

class SensorsAdapter(private val onSensorOptionsClickListener: OnSensorOptionsClickListener) :
    RecyclerView.Adapter<SensorsAdapter.ViewHolder>() {

    private var items: List<Sensor> = listOf()

    class ViewHolder(binding: CardSensorBinding) : RecyclerView.ViewHolder(binding.root) {
        val idText = binding.textView
        val ggdText = binding.textView3
        val options = binding.imageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CardSensorBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idText.text = items[position].sensorID
        holder.ggdText.text = items[position].optimalGDD.toString()

        holder.options.setOnClickListener {
            onSensorOptionsClickListener.onSensorOptionsClick(holder)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Sensor>) {
        items = newItems
        notifyDataSetChanged()
    }

    interface OnSensorOptionsClickListener {
        fun onSensorOptionsClick(holder: ViewHolder)
    }

}