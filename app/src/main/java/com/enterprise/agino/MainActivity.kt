package com.enterprise.agino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.enterprise.agino.databinding.ActivityMainBinding
import com.enterprise.agino.utils.gone
import com.enterprise.agino.utils.show

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        val fragmentsWithoutAppbar = setOf(
            R.id.onBoarding2Fragment
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