package com.enterprise.agino.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enterprise.agino.databinding.FragmentHomeBinding
import com.enterprise.agino.ui.home.adapters.HomeAlertAdapter
import com.enterprise.agino.ui.home.adapters.HomeFieldAdapter
import com.enterprise.agino.utils.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var fieldsAdapter: HomeFieldAdapter
    private lateinit var alertsAdapter: HomeAlertAdapter

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
            fieldsAdapter = HomeFieldAdapter(listOf()) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToGraphFragment(it.fieldID)
                )
            }
            alertsAdapter = HomeAlertAdapter(listOf())

            binding.alertRv.adapter = alertsAdapter
            binding.alertRv.layoutManager = LinearLayoutManager(context)

            binding.fieldRv.adapter = fieldsAdapter
            binding.fieldRv.layoutManager = LinearLayoutManager(context)

            addFieldButton.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNewFieldFragment2(
                        viewModel!!.farm.value!!.farmID
                    )
                )
            }

            overlayFirstAddField.addFieldButton.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNewFieldFragment2(
                        viewModel!!.farm.value!!.farmID
                    )
                )
            }

            firstTimeOverlay.createFarmButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewFarmFragment())
            }
        }

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.errorMessage.collect {
                    showErrorSnackBar(it, binding.root)
                }
            }
            launch {
                viewModel.farm.collect {
                    if (it != null) {
                        binding.chooseFarmName.setText(it.name)
                        alertsAdapter.setAlerts(it.notifications)
                        fieldsAdapter.setFields(it.fields)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}