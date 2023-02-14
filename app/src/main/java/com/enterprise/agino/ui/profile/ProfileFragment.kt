package com.enterprise.agino.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.R
import com.enterprise.agino.databinding.FragmentNewFarmBinding
import com.enterprise.agino.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.deleteAccountButton.setOnClickListener {
            Firebase.auth.currentUser?.delete()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToOnBoarding1Fragment())
        }

        binding.termsAndCondtions.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToOnBoarding2Fragment(
                    "", true
                )
            )
        }

        binding.tvPhoneNo.text = Firebase.auth.currentUser?.phoneNumber

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
