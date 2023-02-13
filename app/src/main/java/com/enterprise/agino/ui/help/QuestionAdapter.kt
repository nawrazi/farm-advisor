package com.enterprise.agino.ui.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enterprise.agino.databinding.QuestionItemBinding
import com.enterprise.agino.utils.gone
import com.enterprise.agino.utils.show

class QuestionAdapter(
    private val faqs: List<Question>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private val isExpanded = MutableList(faqs.size) { false }

    inner class QuestionViewHolder(val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding =
            QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = faqs[position]

        holder.apply {
            binding.question.text = question.title
            binding.answer.text = question.content

            binding.headerRow.setOnClickListener {
                isExpanded[position] = !isExpanded[position]
                notifyItemChanged(position)
            }
            binding.imageButton3.setOnClickListener {
                isExpanded[position] = !isExpanded[position]
                notifyItemChanged(position)
            }

            if (isExpanded[position]) {
                binding.answer.show()
            } else {
                binding.answer.gone()
            }
        }
    }

    override fun getItemCount(): Int {
        return faqs.size
    }
}

