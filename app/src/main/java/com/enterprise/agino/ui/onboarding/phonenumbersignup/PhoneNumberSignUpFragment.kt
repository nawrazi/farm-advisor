package com.enterprise.agino.ui.onboarding.phonenumbersignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.databinding.FragmentPhoneNumberSignupBinding

class PhoneNumberSignUpFragment : Fragment() {
    private var _binding: FragmentPhoneNumberSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhoneNumberSignupBinding.inflate(inflater, container, false)

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            continueBtn.setOnClickListener {
                findNavController().navigate(PhoneNumberSignUpFragmentDirections.actionPhoneNumberSignUpFragmentToOnBoarding2Fragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}