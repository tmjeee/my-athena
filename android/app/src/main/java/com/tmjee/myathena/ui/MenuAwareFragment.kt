package com.tmjee.myathena.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.NavGraphXmlDirections
import com.tmjee.myathena.R
import com.tmjee.myathena.ui.pinValidation.PinValidationFragmentDirections

abstract class MenuAwareFragment : Fragment() {

    private val _activityViewModel: MainActivityViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        return when(item.itemId) {
            R.id.menu_item_hub -> {
                navController.navigate(PinValidationFragmentDirections.actionToHub(
                    _activityViewModel.user.value?.userId ?: 0

                ))
                /*
                navController.navigate(NavGraphXmlDirections.actionToHub(
                    _activityViewModel.user.value?.userId ?: 0
                ))
                 */
                true
            }
            R.id.menu_item_profile_and_settings -> {
                navController.navigate(NavGraphXmlDirections.actionToProfileAndSettings())
                true
            }
            R.id.menu_item_logout -> {
                navController.navigate(NavGraphXmlDirections.actionToLogout())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}