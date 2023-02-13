package com.enterprise.agino.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            addFieldButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewFieldFragment2())
            }

            firstTimeOverlay.createFarmButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewFarmFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}