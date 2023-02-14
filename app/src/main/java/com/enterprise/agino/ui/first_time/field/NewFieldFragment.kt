package com.enterprise.agino.ui.first_time.field

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.common.Resource
import com.enterprise.agino.databinding.FragmentNewFieldBinding
import com.enterprise.agino.utils.showErrorSnackBar
import com.enterprise.agino.utils.showSuccessSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewFieldFragment : Fragment() {
    private var _binding: FragmentNewFieldBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewFieldViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewFieldBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.formSubmissionResult.collect() {
                    if (it is Resource.Success) {
                        showSuccessSnackBar("Successfully added field", binding.root)
                        findNavController().popBackStack()
                    } else if (it is Resource.Error) {
                        showErrorSnackBar("Error adding field: ${it.message}", binding.root)
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