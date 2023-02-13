package com.enterprise.agino.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.enterprise.agino.R
import com.enterprise.agino.databinding.ActivityMainBinding
import com.enterprise.agino.utils.gone
import com.enterprise.agino.utils.show
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var startDestination: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        if (savedInstanceState == null)
            startDestination = if (Firebase.auth.currentUser != null) {
                R.id.homeFragment
            } else {
                R.id.onBoarding1Fragment
            }
    }


    override fun onStart() {
        super.onStart()
        val navController = findNavController(R.id.nav_host)

        if (Firebase.auth.currentUser != null) {
            navController.clearBackStack(R.id.navigation_graph)
            navController.navigate(R.id.homeFragment)
        } else {
            navController.clearBackStack(R.id.navigation_graph)
            navController.navigate(R.id.onBoarding1Fragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val fragmentsWithoutAppbar = setOf(
            R.id.onBoarding2Fragment,
            R.id.phoneNumberSignUpFragment,
            R.id.verificationCodeFragment,
            R.id.addNewSensorFragment
        )
        navController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
            if (fragmentsWithoutAppbar.contains(destination.id)) binding.toolbar.gone()
            else binding.toolbar.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.nav_host).navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }
}