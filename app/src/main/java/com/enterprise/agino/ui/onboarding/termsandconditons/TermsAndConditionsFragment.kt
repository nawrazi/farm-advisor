package com.enterprise.agino.ui.onboarding.termsandconditons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.enterprise.agino.databinding.FragmentTermsAndConditionsBinding
import com.enterprise.agino.utils.gone

class TermsAndConditionsFragment : Fragment() {
    private var _binding: FragmentTermsAndConditionsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<TermsAndConditionsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTermsAndConditionsBinding.inflate(inflater, container, false)

        if (args.showOnly) {
            binding.bottomLayout.gone()
        }

        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            agreeBtn.setOnClickListener {
                findNavController().navigate(
                    TermsAndConditionsFragmentDirections.actionOnBoarding2FragmentToVerificationCodeFragment(
                        args.phoneNumber
                    )
                )
            }

            closeButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}