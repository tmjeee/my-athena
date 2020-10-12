package com.tmjee.myathena.ui.profileAndSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tmjee.myathena.databinding.FragmentProfileAndSettingsBinding
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileAndSettingsFragment: MenuAwareFragment() {

    lateinit var bindings: FragmentProfileAndSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindings = FragmentProfileAndSettingsBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this

        setSupportActionBarAndSetupWithNavController(
            toolbar = bindings.appBarLayoutIncluded.toolbar,
            navController = null,
            appBarConfiguration = null,
            navigationView = null
        )



        bindings.appBarLayoutIncluded.toolbar.setNavigationOnClickListener {

            when(findNavController().currentDestination?.id) {

                    1 -> {

                    }
                    else -> {
                        requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()
                    }

            }

        }

        bindings.appBarLayoutIncluded.toolbar.setTitle("Profile and Settings")

        return bindings.root
    }


}