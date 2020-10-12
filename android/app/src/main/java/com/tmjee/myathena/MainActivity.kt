package com.tmjee.myathena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tmjee.myathena.repository.PaymentsRepo
import com.tmjee.myathena.ui.setupNotificationChannel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject()
    lateinit var paymentsRepo: PaymentsRepo


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("****** main activity create ${paymentsRepo}")


        setupNotificationChannel(this)


        // val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        // val navController = navHostFragment!!.findNavController();
        // appBarConfiguration = AppBarConfiguration(navController.graph)
        // setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
    //     val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    //     val navController = navHostFragment!!.findNavController();
    //     return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

        println("********** onSupportNavigateUp ");
        return super.onSupportNavigateUp()
    }

    override fun onNavigateUp(): Boolean {
        println("********** ontNavigateUp ");
        return super.onNavigateUp()
    }
}