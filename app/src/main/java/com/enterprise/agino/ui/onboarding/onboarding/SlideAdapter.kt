package com.enterprise.agino.ui.onboarding.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.ViewOnboardingSlideBinding


class SlideAdapter(private val slides: List<Slide>) :
    RecyclerView.Adapter<SlideAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewOnboardingSlideBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ViewOnboardingSlideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            image.setImageResource(slides[position].imageId)
            titleText.setText(slides[position].title)
            descriptionText.setText(slides[position].description)
        }
    }

    override fun getItemCount(): Int = slides.size
}