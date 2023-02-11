package com.enterprise.agino.ui.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.QuestionItemBinding

class QuestionAdapter (
    var faqs: List<Question>
        ) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    lateinit var binding: QuestionItemBinding
    inner class QuestionViewHolder(binding: QuestionItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bindQuestion(question: Question) {
                binding.tvTitle.text = question.title
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = faqs[position]
        holder.bindQuestion(question)
    }

    override fun getItemCount(): Int {
        return faqs.size
    }
}

