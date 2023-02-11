package com.enterprise.agino.ui.onboarding.onboarding1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.enterprise.agino.R
import com.enterprise.agino.databinding.FragmentOnboarding1Binding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoarding1Fragment : Fragment() {
    private var _binding: FragmentOnboarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding1Binding.inflate(inflater, container, false)
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


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}