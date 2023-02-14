package com.enterprise.agino.ui.home.adapters

// adapter for home alert recyclerview
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.CardAlertBinding
import com.enterprise.agino.domain.model.Notification


class HomeAlertAdapter(private var alerts: List<Notification>) :
    RecyclerView.Adapter<HomeAlertAdapter.ViewHolder>() {

    class ViewHolder(binding: CardAlertBinding) : RecyclerView.ViewHolder(binding.root) {
        val date = binding.alertDate
        val description = binding.alertDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        CardAlertBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alert = alerts[position]
        holder.date.text = "Today"
        holder.description.text = alert.title
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAlerts(alerts: List<Notification>) {
        this.alerts = alerts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = alerts.size
}