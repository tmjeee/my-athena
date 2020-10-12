package com.tmjee.myathena.ui.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.tmjee.myathena.NavGraphXmlDirections
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentLogoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*

@AndroidEntryPoint
class LogoutFragment: Fragment() {

    lateinit var bindings: FragmentLogoutBinding

    val viewModel: LogoutFragmentViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        bindings = FragmentLogoutBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel


        return bindings.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings.buttonLogin.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(LogoutFragmentDirections.actionToLogin())
        }
    }
}