package com.enterprise.agino.ui.home.adapters

// adapter for home alert recyclerview
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.AlertCardBinding

// TODO: change this to your model class later
class HomeAlertAdapter(private val dataset: List<Int>) :
    RecyclerView.Adapter<HomeAlertAdapter.ViewHolder>() {

    class ViewHolder(private val binding: AlertCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.alertPicture
        val date = binding.alertDate
        val description = binding.alertDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            AlertCardBinding.inflate(
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