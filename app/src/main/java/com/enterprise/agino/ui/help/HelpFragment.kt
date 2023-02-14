package com.enterprise.agino.ui.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enterprise.agino.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        val adapter = QuestionAdapter(
            listOf(
                Question(
                    "What is Agino?",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc ut aliquam tincidunt, nisl nisl aliquet nunc, eget aliquam nisl nunc eu nunc. Sed euismod, nunc ut aliquam tincidunt, nisl nisl aliquet nunc, eget aliquam nisl nunc eu nunc."
                ),
                Question(
                    "How to use Agino?",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc ut aliquam tincidunt, nisl nisl aliquet nunc, eget aliquam nisl nunc eu nunc. Sed euismod, nunc ut aliquam tincidunt, nisl nisl aliquet nunc, eget aliquam nisl nunc eu nunc."
                ),
                Question(
                    "Do I need to pay for Agino?",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc ut aliquam tincidunt, nisl nisl aliquet nunc, eget aliquam nisl nunc eu nunc. Sed euismod, nunc ut aliquam tincidunt, nisl nisl aliquet nunc, eget aliquam nisl nunc eu nunc."
                ),
            )
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}