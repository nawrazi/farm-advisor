package com.enterprise.agino.ui.home.adapters

// adapter for home field recyclerview
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.CardFieldBinding
import com.enterprise.agino.domain.model.Field


class HomeFieldAdapter(private var fields: List<Field>, private val listener: (Field) -> Unit) :
    RecyclerView.Adapter<HomeFieldAdapter.ViewHolder>() {

    class ViewHolder(binding: CardFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.fieldPicture
        val name = binding.fieldName
        val gddField = binding.gddField
        val reapPeriod = binding.reapPeriod
        val detailButton = binding.viewDetail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            CardFieldBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val field = fields[position]
        holder.name.text = field.name
        holder.gddField.text = (100..400).random().toString()
        holder.reapPeriod.text = "Your Optimal Cutting Period is in 3 days"

        holder.detailButton.setOnClickListener {
            listener(field)
        }
    }

    override fun getItemCount(): Int = fields.size

    @SuppressLint("NotifyDataSetChanged")
    fun setFields(fields: List<Field>) {
        this.fields = fields
        notifyDataSetChanged()
    }
}