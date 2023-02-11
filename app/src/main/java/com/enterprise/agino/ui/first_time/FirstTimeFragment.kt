package com.enterprise.agino.ui.first_time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentFirstTimeUserBinding

class FirstTimeFragment : Fragment() {
    private var _binding: FragmentFirstTimeUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstTimeUserBinding.inflate(inflater, container, false)

        setupListener()
        return binding.root
    }

    private fun setupListener() {
        binding.createFarmButton.setOnClickListener {
            findNavController().navigate(FirstTimeFragmentDirections.actionFirstTimeFragmentToNewFarmFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}