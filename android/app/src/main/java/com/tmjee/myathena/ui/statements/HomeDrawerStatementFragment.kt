package com.tmjee.myathena.ui.statements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentHomeDrawerStatementBinding
import com.tmjee.myathena.ui.MenuAwareFragment
import com.tmjee.myathena.ui.home.HomeFragment
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import com.tmjee.myathena.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDrawerStatementFragment: MenuAwareFragment() {

    val viewModel: HomeDrawerStatementFragmentViewModel by viewModels()

    lateinit var bindings: FragmentHomeDrawerStatementBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        bindings = FragmentHomeDrawerStatementBinding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this
        bindings.viewModel = viewModel

        val navController = findNavController()
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        val homeFragment =  parentFragment?.parentFragment as HomeFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home_drawer_loan_details_fragment,
                R.id.home_drawer_payee_and_linked_accounts_fragment,
                R.id.home_drawer_payment_and_transfers_fragment,
                R.id.home_drawer_statements_fragment,
                R.id.home_drawer_transactions_fragment
            ),
            homeFragment.binding.drawerLayout)

        setSupportActionBarAndSetupWithNavController(
            toolbar = bindings.appBarLayoutIncluded.toolbar,
            navController = navController,
            appBarConfiguration = appBarConfiguration,
            navigationView = homeFragment.binding.navigationView
        )

        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}