package com.enterprise.agino.ui.onboarding.phonenumbersignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentPhoneNumberSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNumberSignUpFragment : Fragment() {
    private var _binding: FragmentPhoneNumberSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PhoneNumberSignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneNumberSignupBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(
                PhoneNumberSignUpFragmentDirections.actionPhoneNumberSignUpFragmentToOnBoarding2Fragment(
                    "+"+viewModel!!.countryCode + viewModel!!.phoneNumber.value!!
                )
            )
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        countryCodePicker.setOnCountryChangeListener {
            viewModel!!.countryCode = binding.countryCodePicker.selectedCountryCode
        } }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}