package com.enterprise.agino.ui.onboarding.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.R
import com.enterprise.agino.databinding.FragmentOnboardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        setupViewPager()
        setupListeners()

        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            signUpBtn.setOnClickListener {
                findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToPhoneNumberSignUpFragment())
            }
            logInBtn.setOnClickListener {
                findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToSignInFragment())
            }
        }
    }

    private fun setupViewPager() {
        val viewPager2 = binding.pager
        val tabLayout = binding.tabLayout

        viewPager2.adapter = SlideAdapter(
            listOf(
                Slide(
                    R.drawable.ic_active_sensor,
                    R.string.fragment_onboarding_1_slide_1_title,
                    R.string.fragment_onboarding_1_slide_1_description
                ),
                Slide(
                    R.drawable.ic_active_sensor,
                    R.string.fragment_onboarding_1_slide_2_title,
                    R.string.fragment_onboarding_1_slide_2_description
                ),
                Slide(
                    R.drawable.ic_active_sensor,
                    R.string.fragment_onboarding_1_slide_3_title,
                    R.string.fragment_onboarding_1_slide_3_description
                )
            )
        )

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            viewPager2.currentItem = position
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}