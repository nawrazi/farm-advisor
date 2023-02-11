package com.enterprise.agino.ui.first_time.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentAddFirstSensorBinding

class AddFirstSensorFragment : Fragment() {
    private var _binding: FragmentAddFirstSensorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFirstSensorBinding.inflate(inflater, container, false)

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            addFirstSensorButton.setOnClickListener {
                findNavController().navigate(AddFirstSensorFragmentDirections.actionAddFirstSensorFragmentToAddNewSensorFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}