package com.enterprise.agino.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        if (viewModel.countryCode.isNotEmpty())
            binding.countryCodePicker.setCountryForPhoneCode(viewModel.countryCode.toInt())
    }

    private fun setupListeners() {
        binding.apply {
            continueBtn.setOnClickListener {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToVerificationCodeFragment(
                        "+" + viewModel!!.countryCode + viewModel!!.phoneNumber.value!!
                    )
                )
            }

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            countryCodePicker.setOnCountryChangeListener {
                viewModel!!.countryCode = countryCodePicker.selectedCountryCode
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}