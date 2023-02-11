package com.enterprise.agino.ui.home.adapters

// adapter for home field recyclerview
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.CardFieldBinding


class HomeFieldAdapter(private val dataset: List<Int>) :
    RecyclerView.Adapter<HomeFieldAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CardFieldBinding) :
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
        // TODO: change this to your model class later
    }

    override fun getItemCount(): Int = dataset.size
}