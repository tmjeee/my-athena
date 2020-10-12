package com.tmjee.myathena.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

fun Fragment.setSupportActionBarAndSetupWithNavController(
                                    toolbar: Toolbar,
                                    navController: NavController?,
                                    appBarConfiguration: AppBarConfiguration?,
                                    navigationView: NavigationView?) {

    // toolbar / appbar & drawer setup
    (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    if (navController != null) {
        NavigationUI.setupActionBarWithNavController(requireActivity() as AppCompatActivity, navController)
        if (toolbar != null && appBarConfiguration != null) {
            NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        } else {
            NavigationUI.setupWithNavController(toolbar, navController)
        }

        if (navigationView != null) {
            NavigationUI.setupWithNavController(navigationView, navController)
        }
    }
}

fun Fragment.setupWithNavController(bottomNavigationView: BottomNavigationView, navController: NavController) {
    NavigationUI.setupWithNavController(bottomNavigationView, navController)
}

inline fun <reified VM: ViewModel>Fragment.applicationViewModels(): Lazy<VM> {
    return createViewModelLazy(
        VM::class,
        { (requireActivity().application as ViewModelStoreOwner).viewModelStore },
    )
}

