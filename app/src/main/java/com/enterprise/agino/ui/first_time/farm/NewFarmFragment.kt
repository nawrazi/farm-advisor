package com.enterprise.agino.ui.first_time.farm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentNewFarmBinding

class NewFarmFragment : Fragment() {
    private var _binding: FragmentNewFarmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewFarmBinding.inflate(inflater, container, false)

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        findNavController().navigate(NewFarmFragmentDirections.actionNewFarmFragmentToAddFieldFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}